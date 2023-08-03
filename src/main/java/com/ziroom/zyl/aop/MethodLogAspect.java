package com.ziroom.zyl.aop;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.ziroom.zyl.aop.MethodLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName：ControllerLogAspect
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/28 15:29
 **/
@Slf4j
@Aspect
@Component
public class MethodLogAspect {

    @Pointcut("@annotation(com.ziroom.zyl.aop.MethodLog)")
    public void methodLogPointCut() {}

    @Around("methodLogPointCut()")
    public Object advice(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();

        MethodSignature ms = (MethodSignature) point.getSignature();
        MethodLog methodLog = ms.getMethod().getAnnotation(MethodLog.class);
        log.info("REQUEST_METHOD:" + point.getSignature().getName());
        requestParam(point);
        Object result = point.proceed();
        if (methodLog.needReturn()){
//            if (result instanceof Resp) {
//                Resp resp = (Resp) result;
                if (ObjectUtils.isNotEmpty(result)){
                    if ( result.toString().length() > 10000 ){
                        log.info("RESPONE_DATA too long");
                    }else {
                        log.info("RESPONE_DATA:" + result.toString());
                    }
                }
//            }
        }
        if(methodLog.cost()){
            long end = System.currentTimeMillis();
            log.info("method cost {} s", (start - end) / 1000);
        }

        return result;
    }


//    @Before("controllerLogPointCut()")
//    public void doBefore(JoinPoint point) {
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (requestAttributes != null) {
//            MethodSignature ms = (MethodSignature) point.getSignature();
//            log.info("REQUEST_METHOD:"+ point.getSignature().getName());
//            requestParam(point);
//        }
//    }
//
    public void requestParam(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        // 参数名数组
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
        // 构造参数组集合
        Map<Object, Object> map = new HashMap<>();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (!(args[i] instanceof HttpServletRequest)) {
                map.put(JSON.toJSON(parameterNames[i]), JSON.toJSON(args[i]));
            }
        }
        log.info("REQUEST_PARAM: {}", JSON.toJSON(map));
    }
}
