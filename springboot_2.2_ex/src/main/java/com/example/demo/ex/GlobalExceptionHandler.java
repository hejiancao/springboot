package com.example.demo.ex;

import com.example.demo.utils.RespResult;
import com.example.demo.utils.RespResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 统一异常处理类
 * @author created by shaos on 2019/6/12
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final static String SYS_ERR = "-1";
    private final static String ARGUMENT_ERR = "1001";

    /**
     *
     * 处理未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RespResult handleExcepton(Exception e) {
        loghelper(log, e.getMessage());
        return RespResultUtils.returnJson(SYS_ERR, e.getMessage(), null);
    }

    /**
     * 处理自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public RespResult handleCustomerException(CustomerException e) {
        loghelper(log, e.getMessage());
        return RespResultUtils.returnJson(e.getCode(), e.getMessage(), null);
    }

    /**
     * 处理参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RespResult handleCustomerException(MethodArgumentNotValidException e) {
        List<String> errList = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        this.loghelper(log, errList.toString());
        return RespResultUtils.returnJson(ARGUMENT_ERR, String.join(",", errList), null);
    }

    private void loghelper(Logger log, String message) {
        log.error(String.format(">>> 发生异常：%s", message));
    }

}
