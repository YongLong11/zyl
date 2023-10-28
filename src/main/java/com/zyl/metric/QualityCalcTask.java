package com.zyl.metric;

import com.google.common.collect.Lists;

import java.util.List;

public class QualityCalcTask extends AbstractCalcTask<CalcResult>{
    @Override
    public BaseContext getContext(){
        return new QualityCalcContext();
    }
    @Override
    public List<MetricCalc> getMetric(){
        return Lists.newArrayList();
    }

    @Override
    protected CalcResult initRestlt() {
        return new CalcResult();
    }
}
