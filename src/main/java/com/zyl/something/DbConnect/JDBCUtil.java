package com.zyl.something.DbConnect;

import com.zyl.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.sql.*;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
public class JDBCUtil {
    private String url;
    private String userName;
    private String passWord;
    private String dbType;
    private Connection connection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;

    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public JDBCUtil(String dbType, String url, String userName, String passWord){
        this.url = url;
        this.passWord = passWord;
        this.userName = userName;
        this.dbType = dbType;
        Optional.ofNullable(dbType).orElseThrow(() -> new BusinessException("缺少数据库类型信息"));
        loadDriver(dbType);
        this.connection = getConnection(url, userName, passWord);
    }

    private void loadDriver(String dbType){
        try {
            if(dbType.equalsIgnoreCase(DbTypeEnum.MYSQL.name())){
                Class.forName(MYSQL_DRIVER);
            }else if(dbType.equalsIgnoreCase(DbTypeEnum.ORACLE.name())){
                Class.forName(ORACLE_DRIVER);
            }else {
                throw new BusinessException("未知的数据库类型");
            }
        }catch (ClassNotFoundException e){
            log.error("failed to load driver");
            throw new BusinessException("加载数据库驱动失败");
        }
    }

    private Connection getConnection(String url, String userName, String passWord){
        Assert.hasLength(url, "URL 不可为空");
        Assert.hasLength(userName, "userName 不可为空");
        Assert.hasLength(passWord, "passWord 不可为空");
        try {
            DriverManager.setLoginTimeout(5);
            return DriverManager.getConnection(url, userName, passWord);
        }catch (SQLException e){
            log.error(MessageFormat.format("创建数据库连接失败，url -> 【{0}】 \n  {1}", url, e.getMessage()));
            throw new BusinessException(MessageFormat.format("创建数据库连接失败，url -> 【{0}】 \n  {1}", url, e.getMessage()));
        }
    }

    public List<String> getTables(String dbName) throws SQLException {
        DatabaseMetaData metaData = this.connection.getMetaData();
        /**
         * 如果数据库为MySQL:那么第一个参数catalog,可以是数据库的名称,当该项为null时候,为Url串中指定的数据库名称,第二个参数schema,填入null;
         *
         * 如果数据库为Oralce: 那么第一个参数catalog,为null,第二个参数schema,填入大写的用户名称例如”SCOTT”,如果该项目为null,那么查询范围为所有的模式用户。
         * 参考地址：https://www.cnblogs.com/azhqiang/p/4303071.html
         */
        String schema =null;
        if (!DbTypeEnum.MYSQL.name().equalsIgnoreCase(this.dbType)){
            dbName = null;
            schema = this.userName.toUpperCase(Locale.ROOT);
        }
        ResultSet tables = metaData.getTables(dbName, schema, "%", new String[] { "TABLE" });
        ArrayList<String> tablesList = new ArrayList<String>();
        while (tables.next()) {
            tablesList.add(tables.getString("TABLE_NAME"));
        }
        return tablesList;
    }
    public List<Map<String, Object>> findResult(String sql) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        this.pstmt = this.connection.prepareStatement(sql);
        this.resultSet = this.pstmt.executeQuery();
        ResultSetMetaData metaData = this.resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();

        while(this.resultSet.next()) {
            Map<String, Object> map = new HashMap<>();

            for(int i = 0; i < cols_len; ++i) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = this.resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }

    public int executeUpdate(String sql) throws SQLException {
        this.pstmt = this.connection.prepareStatement(sql);
        this.connection.setAutoCommit(false);
        return this.pstmt.executeUpdate();
    }

    public void releaseConn() {
        this.release(this.resultSet);
        this.release(this.pstmt);
        this.release(this.connection);
    }

    private void release(AutoCloseable auto) {
        if (auto != null) {
            try {
                auto.close();
            } catch (Exception var3) {
                log.error("连接关闭失败", var3);
            }
        }
    }

    public void validateConnection() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.userName, this.passWord);
            log.info("jdbcUtil validateConnection success");
        } catch (Exception var5) {
            throw new RuntimeException("get connection error!", var5);
        } finally {
            this.release(this.connection);
        }

    }
}
