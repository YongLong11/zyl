package com.ziroom.zyl.metric;

import com.google.common.collect.Lists;

import java.util.List;

public class FirstCalcTask extends AbstractCalcTask<CalcResult>{
    @Override
    public BaseContext getContext(){
        return new Context();
    }
    @Override
    public List<MetricCalc> getMetric(){
        return Lists.newArrayList(new FirstCalc());
    }

    @Override
    protected CalcResult initRestlt() {
        return new CalcResult();
    }
}
