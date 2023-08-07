package com.shbone.reggiedemo.common;


import com.shbone.reggiedemo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String > exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.info("失败"+exception.getMessage());

        if(exception.getMessage().contains("Duplicate entry")){
            String[] s = exception.getMessage().split(" ");
            String msg = s[2]+"已经存在";
            return R.error(msg);
        }
        return R.error("失败");
    }

    @ExceptionHandler(CustomException.class)
    public R<String > customExceptionHandler(CustomException  exception){
        log.info("失败"+exception.getMessage());

        return R.error(exception.getMessage());
    }
}
