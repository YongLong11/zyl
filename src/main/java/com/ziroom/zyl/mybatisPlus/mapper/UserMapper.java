package com.ziroom.zyl.mybatisPlus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.zyl.mybatisPlus.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author mybatisPlusAutoGenerate
 * @since 2023-08-04
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {

}
