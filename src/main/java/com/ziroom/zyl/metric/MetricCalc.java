package com.ziroom.zyl.metric;

import java.util.function.BiConsumer;

public interface MetricCalc<R, T> {

    T calc(BaseContext context);

    BiConsumer<R, T> setResult();
}
