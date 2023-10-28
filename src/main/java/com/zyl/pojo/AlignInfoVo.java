package com.zyl.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName：AlignInfoVo
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/2 12:58
 **/
@Data(staticConstructor = "build")
@Accessors(chain = true)
public class AlignInfoVo {

    private Long id;

    private String createName;

}
