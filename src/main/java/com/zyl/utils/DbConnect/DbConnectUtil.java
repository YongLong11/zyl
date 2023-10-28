package com.zyl.utils.DbConnect;

import com.zyl.common.Resp;
import com.zyl.pojo.AutoDataDatabaseDto;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.truncate.Truncate;

import java.util.List;
import java.util.Map;

@Slf4j
public class DbConnectUtil {

    @Data(staticConstructor = "of")
    @Accessors(chain = true)
    public static class ExecSqlResult{
        Boolean isSelect;
        List<Map<String, Object>> result;
        long changedRows;
    }

    public static ExecSqlResult execSql(AutoDataDatabaseDto dto) {
        String sql = dto.getSql();
        String formattedSql = getFormattedSql(sql, dto.getDbType());
        Statement statement = null;
        ExecSqlResult execSqlResult = new ExecSqlResult();
        try {
            statement = CCJSqlParserUtil.parse(formattedSql);
        } catch (JSQLParserException e) {
            log.error("sql -> {} 解析失败", formattedSql);
        }
        JDBCUtil jdbcUtil = getJdbcUtils(dto);
        if(statement instanceof Select || statement instanceof SubSelect){
            List<Map<String, Object>> result = execSelectSql(jdbcUtil, formattedSql);
            log.info("jdbcUtil.findResult:[{}]", result);
            execSqlResult.setResult(result);
            execSqlResult.setIsSelect(true);
        }else if(statement instanceof CreateTable || statement instanceof Alter ||
                statement instanceof Drop || statement instanceof Truncate){
            int changedRows = execSelectUpdateSql(jdbcUtil, formattedSql);
            log.info("jdbcUtil.findResult:[{}]", changedRows);
            execSqlResult.setChangedRows(changedRows);
            execSqlResult.setIsSelect(false);
        }
        return execSqlResult;
    }

    public static List<Map<String, Object>> execSelectSql(JDBCUtil jdbcUtil, String sql) {
        List<Map<String, Object>> result;
        try {
            result = jdbcUtil.findResult(sql);
            log.info("jdbcUtil.findResult:[{}]", result);
        } catch (Exception e) {
            log.error("执行远程sql异常", e);
            throw new RuntimeException(e);
        }finally {
            jdbcUtil.releaseConn();
        }
        return result;
    }

    public static int execSelectUpdateSql(JDBCUtil jdbcUtil, String sql) {
        int result;
        try {
            result = jdbcUtil.executeUpdate(sql);
            log.info("jdbcUtil.findResult:[{}]", result);
        } catch (Exception e) {
            log.error("执行远程sql异常", e);
            throw new RuntimeException(e);
        } finally {
            jdbcUtil.releaseConn();
        }
        return result;
    }

    public static List<String> getTables(AutoDataDatabaseDto dto) {
        JDBCUtil jdbcUtil = getJdbcUtils(dto);
        List<String> result;
        try {
            result = jdbcUtil.getTables(dto.getDbName());
            log.info("jdbcUtil.getTables:[{}]", result);
        } catch (Exception e) {
            log.error("执行远程sql异常", e);
            throw new RuntimeException(e);
        } finally {
            jdbcUtil.releaseConn();
        }
        return result;

    }

    private static String getFormattedSql(String sql, String dbType) {
        String formattedSql = sql.replace("\n", " ").replace("\t", " ");
        if (DbTypeEnum.ORACLE.name().equalsIgnoreCase(dbType)) {
            formattedSql = formattedSql.replace(";", "");
        }
        return formattedSql;
    }

    public static Resp<String> validateConnection(AutoDataDatabaseDto dto) {
        JDBCUtil jdbcUtil = getJdbcUtils(dto);
        try {
            jdbcUtil.validateConnection();
            return Resp.success("连接成功");
        } catch (Exception e) {
            log.error("执行远程sql异常", e);
            return Resp.error("连接失败，tips:" + e.getCause());
        } finally {
            jdbcUtil.releaseConn();
        }
    }

    private static JDBCUtil getJdbcUtils(AutoDataDatabaseDto dto) {
        String dbUrl = "";
        if (DbTypeEnum.ORACLE.name().equalsIgnoreCase(dto.getDbType())) {
            dbUrl = "jdbc:oracle:thin:@" + dto.getDbIp() + ":" + dto.getDbPort() + ":";
        }else if(DbTypeEnum.MYSQL.name().equalsIgnoreCase(dto.getDbType())){
            dbUrl = "jdbc:mysql://" + dto.getDbIp() + ":" + dto.getDbPort() + "/";
        }
        return getJdbcUtils(dto.getDbType(), dbUrl, dto.getDbName(), dto.getUserName(), dto.getUserPassword());
    }
    private static JDBCUtil getJdbcUtils(String dbType, String dbUrl, String dbName, String dbUserName, String dbUserPwd) {
        JDBCUtil jdbcUtil = null;
        if (DbTypeEnum.ORACLE.name().equalsIgnoreCase(dbType)) {
            jdbcUtil = new JDBCUtil(dbType,dbUrl + dbName, dbUserName, dbUserPwd);
        }else if(DbTypeEnum.MYSQL.name().equalsIgnoreCase(dbType)){
            jdbcUtil = new JDBCUtil(dbType,dbUrl + dbName + "?useUnicode=true&characterEncoding=UTF-8&useSSL=false", dbUserName, dbUserPwd);
        }
        return jdbcUtil;
    }
}
