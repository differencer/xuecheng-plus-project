package com.xuecheng.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 针对自定义异常处理
    @ResponseBody
    @ExceptionHandler(XuechengPlusException.class) // 截取并处理异常信息(截取什么养的异常)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)// 返回的状态码
    public RestErrorResponse customException(XuechengPlusException e){


        log.error("系统异常：{}",e.getErrMessage(),e);
        // 解析出
        String errorMessage = e.getErrMessage();
        RestErrorResponse errorResponse = new RestErrorResponse(errorMessage);
        return errorResponse;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class) // 截取并处理异常信息(截取什么养的异常)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)// 返回的状态码
    public RestErrorResponse validateFailException(MethodArgumentNotValidException e){

        BindingResult bindingResult = e.getBindingResult();
        List<String> errors = new ArrayList<>();
        bindingResult.getFieldErrors().stream().forEach(item->
                errors.add(item.getDefaultMessage()));

        String errorMessage = StringUtils.join(errors,","); // 以逗号拼接
        log.error("校验异常：{}",errorMessage,e);
        // 解析出

        RestErrorResponse errorResponse = new RestErrorResponse(errorMessage);
        return errorResponse;
    }

    // 针对 其他异常处理
    @ResponseBody
    @ExceptionHandler(Exception.class) // 截取并处理异常信息(截取什么养的异常)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)// 返回的状态码
    public RestErrorResponse otherException(Exception e){
        // 记录
        log.error("系统异常：{}",e.getMessage(),e);
        // 解析出
        RestErrorResponse errorResponse = new RestErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
        return errorResponse;
    }


}
