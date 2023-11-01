package com.zyl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName：AlignInfoVo
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/2 12:58
 **/
@Data(staticConstructor = "of")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AlignInfoVo {

    private Long id;

    private String createName;

}
