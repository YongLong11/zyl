package com.zyl.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class PerformanceStaff {

    @ExcelProperty(value = "员工工号")
    private String userCode;

    @ExcelProperty(value = "姓名")
    private String userName;

    @ExcelProperty(value = "城市")
    private  String city;

    @ExcelProperty(value = "虚拟层")
    private String virtual;
    @ExcelProperty(value = "一级部门")
    private String firstDept;
    @ExcelProperty(value = "二级部门")
    private String secondDept;
    @ExcelProperty(value = "三级部门")
    private String thirdDept;
    @ExcelProperty(value = "四级部门")
    private String fourDept;
    @ExcelProperty(value = "五级部门")
    private String fiveDept;
    @ExcelProperty(value = "六级部门")
    private String sixDept;

    @ExcelProperty(value = "考核类别")
    private String category;

    @ExcelProperty(value = "考核周期")
    private String cycle;

    @ExcelProperty(value = "城市/部门终审")
    private String cityOrDeptLastTrialName;
    @ExcelProperty(value = "城市终审ID")
    private String cityOrDeptLastTrialCode;

    @ExcelProperty(value = "中心终审")
    private String centerLastTrialName;
    @ExcelProperty(value = "中心终审ID")
    private String centerLastTrialCode;
}
