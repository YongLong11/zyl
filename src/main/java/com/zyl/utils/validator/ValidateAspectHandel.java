package com.zyl.utils.validator;


import com.zyl.common.Resp;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@Aspect
@Order(2)
public class ValidateAspectHandel {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(com.zyl.utils.validator.ValidateGroup)")
    public Object validateAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        Method method = getMethodByClassAndName(target.getClass(), methodName);
        // 1. 此为注解上的条件限制
        ValidateGroup an = method.getAnnotation(ValidateGroup.class);
        // 2. 此为外部传递进来的参数
        Object[] args = joinPoint.getArgs();
        // 3. 拿条件与参数匹配是否一致
        Map<String, Object> map = validateFiled(an.fields(), args);
        String status = (String) map.get("status");
        if ("0".equals(status)) {
            log.info("通过验证");
        } else {
            throw new IllegalArgumentException(String.valueOf(map.get("message")));
        }
        return joinPoint.proceed();
    }

    // 根据类名和方法名得到方法
    private Method getMethodByClassAndName(Class c, String methodName) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    private Map<String, Object> validateFiled(ValidateField[] validateFields, Object[] args) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "0");

        for (ValidateField validateField : validateFields) {
            StringBuffer message = new StringBuffer();
            String showName = validateField.showName();
            String fileName = validateField.filedName();
            message.append("".equals(showName) ? fileName : showName);

            Object arg;
            // 根据filename是否存在来判断方法是对应的一个对象属性还是参数
            if (!StringUtils.hasLength(fileName)) {
                // 1. 若不存在fileName 则说明直接判断参数值
                arg = args[validateField.index()];
            } else {
                // 2. 若存在fileName 则获取对象中同名的属性值
                arg = getFieldByObjectAndFileName(args[validateField.index()], fileName);
            }

            if (validateField.notNull()) {
                if (arg == null || StringUtils.isEmpty(String.valueOf(arg))) {
                    map.put("status", "1");
                    map.put("message", message.append(" 不能为空"));
                }
            } else {//如果该参数能够为空，并且当参数为空时，就不用判断后面的了 ，直接返回true
                if (arg == null || StringUtils.isEmpty(String.valueOf(arg))) {
                    continue;
                }
            }

            // 判断对应参数是否符合正则
            if (StringUtils.hasLength(validateField.regStr())) {//判断正则
                if (!(String.valueOf(arg)).matches(validateField.regStr())) {
                    map.put("status", "1");
                    map.put("message", message.append(" 格式不正确"));
                    return map;
                }
            }

            if (validateField.maxLen() > 0) {//判断字符串最大长度
                if (((String) arg).length() > validateField.maxLen()) {
                    map.put("status", "1");
                    map.put("message", message.append(" 不能超过").append(validateField.maxLen()).append("个字符!"));
                    return map;
                }
            }

            if (validateField.minLen() > 0) {//判断字符串最小长度
                if (((String) arg).length() < validateField.minLen()) {
                    map.put("status", "1");
                    map.put("message", message.append(" 不能少于").append(validateField.minLen()).append("个字符!"));
                    return map;
                }
            }

            if (validateField.maxVal() != -1) {//判断数值最大值
                if ((long) arg > validateField.maxVal()) {
                    map.put("status", "1");
                    map.put("message", message.append(" 数值不能大于").append(validateField.maxVal()));
                    return map;
                }
            }

            if (validateField.minVal() != -1) {//判断数值最小值
                if ((long) arg < validateField.minVal()) {
                    map.put("status", "1");
                    map.put("message", message.append(" 数值不能小于").append(validateField.minVal()));
                    return map;
                }
            }

            if (StringUtils.hasLength(validateField.values())) {//判断属性是否有固定值
                boolean flag = false;
                //获取分隔符
                String separator = validateField.separator();
                //属性的值
                String argValue = String.valueOf(arg);
                String values = validateField.values();
                if (StringUtils.hasLength(separator)) {//可以为多个值
                    String[] valueArray = values.split(separator);
                    if (valueArray != null && valueArray.length > 0) {
                        for (String val : valueArray) {
                            if (val.equals(argValue)) {
                                flag = true;
                                break;
                            }
                        }
                    }
                } else {
                    if (!argValue.equals(values)) {//只允许为一个值
                        map.put("status", "1");
                        map.put("message", message.append(" 的值只能是").append(values));
                        return map;
                    } else {
                        flag = true;
                    }
                }
                if (!flag) {
                    map.put("status", "1");
                    map.put("message", message.append(" 的值不在").append(values).append("内"));
                    return map;
                }
            }
        }
        return map;
    }

    /**
     * 根据对象以及属性名获取对应的值
     *
     * @param targetObj 对象
     * @param fileName  属性名
     * @return 获取的值 若不存在则为null
     * @throws Exception
     */
    private Object getFieldByObjectAndFileName(Object targetObj, String fileName) throws Exception {
        Object arg = targetObj;
        // 针对使用 @Accessors 注解修饰的类，无法通过次方法获取获取入参的值。尝试使用第二种方式获取
        // arg = getField(targetObj, fileName);
        arg = invokeGet(targetObj, fileName);
        return arg;
    }

    private Object getField(Object object, String fileName) throws Exception {
        Object obj = null;
        Class<?> clazz = object.getClass();
        try {
            // 1. 根据类名和对应的属性名获取对应的get方法
            PropertyDescriptor pd = new PropertyDescriptor(fileName, clazz);
            // 和 getGetMethod 方法大同小异
            // 获取get方法
            Method getMethod = pd.getReadMethod();
            // 2. 调用指定对象的get方法 获取对应属性的值
            obj = getMethod.invoke(object);

            log.info(clazz + ":" + fileName + ":" + obj);
        } catch (IntrospectionException e) {
            log.error(clazz + "没有属性:" + fileName);
        }
        return obj;
    }

    /**
     * 执行get方法
     *
     * @param obj 执行对象
     * @param fieldName 属性
     * @return Object
     * @throws RuntimeException
     */
    private static Object invokeGet(Object obj, String fieldName) {
        Method method = getGetMethod(obj.getClass(), fieldName);
        try {
            return method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Reflect error! execute " + method.getClass().getName() + "." + method.getName() + " error");
        }
    }
    private static Method getGetMethod(Class<?> objectClass, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase(Locale.ROOT));
        sb.append(fieldName.substring(1));

        try {
            return objectClass.getMethod(sb.toString());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Reflect error! " + objectClass.getName() + " dont have " + sb.toString() + " method") ;
        }
    }

    public static void main(String[] args) throws IOException {
        String path = "/app/logs";
        File file = new File(path + "/okr.log");
        System.out.println(file.getAbsoluteFile().getPath());
        System.out.println(file.getCanonicalPath());
    }

}
