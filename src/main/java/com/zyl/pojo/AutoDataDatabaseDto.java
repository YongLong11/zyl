package com.zyl.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data(staticConstructor = "of")
@Accessors(chain = true)
public class AutoDataDatabaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 数据库 ip
     */
    private String dbIp;

    /**
     * 执行 SQL
     */
    private String sql;
    /**
     * 数据库 表名
     */
    private String dbName;
    /**
     * 数据库 端口
     */
    private String dbPort;
    /**
     * 数据库 连接用户名
     */
    private String userName;
    /**
     * 数据库 连接密码
     */
    private String userPassword;

}
