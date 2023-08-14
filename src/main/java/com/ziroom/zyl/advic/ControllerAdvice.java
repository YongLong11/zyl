package com.ziroom.zyl.advic;
import com.ziroom.zyl.common.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(2)
    public Resp handleValidate(Exception exception){
        log.error("统一异常捕获 ", exception);
        return Resp.error(exception.getMessage());
    }

    @ExceptionHandler(value = {SQLException.class, DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(1)
    public Resp handleSqlValidate(Exception exception) {
        log.error(exception.getMessage(), exception);
        return Resp.error("db error");
    }
}
