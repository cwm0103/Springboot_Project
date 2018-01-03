package com.bom.utils.exception;

import com.bom.utils.LoggerUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jindb on 2017/7/20.
 * 全局异常捕捉类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
   /* public static final String DEFAULT_ERROR_VIEW = "error";
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        LoggerUtils.Error("系统错误",e);
        return mav;
    }
    *//**
     * 400 - Bad Request
     *//*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public AjaxResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        LoggerUtils.Error("缺少请求参数", e);
        return new AjaxResult().failure("required_parameter_is_not_present");
    }

    *//**
     * 400 - Bad Request
     *//*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public AjaxResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LoggerUtils.Error("参数解析失败", e);
        return new AjaxResult().failure("could_not_read_json");
    }

    *//**
     * 400 - Bad Request
     *//*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LoggerUtils.Error("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new AjaxResult().failure(message);
    }

    *//**
     * 400 - Bad Request
     *//*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e) {
        LoggerUtils.Error("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new AjaxResult().failure(message);
    }

    *//**
     * 400 - Bad Request
     *//*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult handleServiceException(ConstraintViolationException e) {
        LoggerUtils.Error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return new AjaxResult().failure("parameter:" + message);
    }

    *//**
     * 400 - Bad Request
     *//*
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ValidationException.class)
//    public AjaxResult handleValidationException(ValidationException e) {
//        LoggerUtils.Error("参数验证失败", e);
//        return new AjaxResult().failure("validation_exception");
//    }

    *//**
     * 405 - Method Not Allowed
     *//*
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        LoggerUtils.Error("不支持当前请求方法", e);
        return new AjaxResult().failure("request_method_not_supported");
    }

    *//**
     * 415 - Unsupported Media Type
     *//*
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public AjaxResult handleHttpMediaTypeNotSupportedException(Exception e) {
        LoggerUtils.Error("不支持当前媒体类型", e);
        return new AjaxResult().failure("content_type_not_supported");
    }

    *//**
     * 500 - Internal Server Error
     *//*
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(ServiceException.class)
//    public AjaxResult handleServiceException(ServiceException e) {
//        logger.error("业务逻辑异常", e);
//        return new AjaxResult().failure("业务逻辑异常：" + e.getMessage());
//    }

    *//**
     * 500 - Internal Server Error
     *//*
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public AjaxResult handleException(Exception e) {
//        LoggerUtils.Error("通用异常", e);
//        return new AjaxResult().failure("通用异常：" + e.getMessage());
//    }

    *//**
     * 操作数据库出现异常:名称重复，外键关联
     *//*
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public AjaxResult handleException(DataIntegrityViolationException e) {
        LoggerUtils.Error("操作数据库出现异常:", e);
        return new AjaxResult().failure("操作数据库出现异常：字段重复、有外键关联等");
    }
*/
    @ExceptionHandler(AuthException.class)
    public ModelAndView authHandleException(AuthException e, HttpServletRequest  request, HttpServletResponse response) throws IOException, ServletException {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("message", e.getMessage());
        mav.setViewName("/login");
        LoggerUtils.Error("系统错误",e);
        return mav;

    }
}
