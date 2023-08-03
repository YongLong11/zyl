package com.ziroom.zyl.metric;

import java.util.List;

public abstract class AbstractCalcTask<T> {

    public T startTask(){
        T calcResult = initRestlt();
        Context context = (Context) getContext();
        List<MetricCalc> calcs = getMetric();
        for (MetricCalc calc : calcs) {
            MetricCalc<Object, Object> metricCalc = (MetricCalc<Object, Object>) calc;
            Object re = metricCalc.calc(context);
            metricCalc.setResult().accept(calc, re);
        }
        return calcResult;
    }

    protected abstract BaseContext getContext();
    protected abstract List<MetricCalc> getMetric();
    protected abstract T initRestlt();

}
