package com.ziroom.zyl.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @ClassName：ExportResp
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/2 14:09
 **/
//@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportResp {

    public String getOKRI() {
        return OKRI;
    }

    public void setOKRI(String OKRI) {
        this.OKRI = OKRI;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ExcelProperty("o / kr / ki")
    @ColumnWidth(100)
    private String OKRI;

    @ExcelProperty("A指标")
    @ColumnWidth(10)
    private String A;

    @ExcelProperty("B指标")
    @ColumnWidth(10)
    private String B;

    @ExcelProperty("okri标识，1是o，2是kr，3是ki")
    @ColumnWidth(10)
    //标准前面字段类型，
    private String type;

    @ExcelProperty("名字")
    @ColumnWidth(10)
    private String userCode;

    @ExcelProperty("工号")
    @ColumnWidth(10)
    private String userName;
}
