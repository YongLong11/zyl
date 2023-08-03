package com.ziroom.zyl.metric;

import java.util.function.BiConsumer;

public class FirstCalc implements MetricCalc<CalcResult, Long>{

   public Long calc(Context context){
        return 1L;
    }
   @Override
   public Long calc(BaseContext context) {
        return null;
   }

    @Override
   public BiConsumer<CalcResult, Long> setResult(){
       return (cr, lo) -> cr.setFirstTaskCalcResult(lo);
    }
}
