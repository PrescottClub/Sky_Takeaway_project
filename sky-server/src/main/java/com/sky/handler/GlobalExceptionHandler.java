package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理SQL异常
     * @param ex SQLIntegrityConstraintViolationException异常
     * @return 处理结果
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // 获取异常消息
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            // 如果异常消息包含 "Duplicate entry"，则表示有重复的条目
            String[] split = message.split(" ");
            String username = split[2]; // 获取重复的用户名
            String msg = username + MessageConstant.ALREADY_EXISTS; // 构造错误信息
            return Result.error(msg); // 返回错误结果
        } else {
            // 如果不是重复条目错误，返回一个通用的错误信息
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
