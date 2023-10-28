package com.zyl.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/*
用于绩效人员导出名单文件处理
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {


    private List<T> lists = new ArrayList<>();

    public ExcelListener(List<T> list){
        lists.clear();
        lists = list;
    }

    // 每条数据都会调用
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        lists.add(t);
    }

    // 所有数据解析完会调用
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
//        System.out.println(performanceStaffs.size());
    }



}
