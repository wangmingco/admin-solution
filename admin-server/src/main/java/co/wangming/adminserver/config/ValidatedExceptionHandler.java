package co.wangming.adminserver.config;

import co.wangming.adminserver.enums.ResponseCode;
import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.vo.Response;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * Created By WangMing On 2020-03-10
 **/
@Component
@ControllerAdvice
public class ValidatedExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getUserLogger(ValidatedExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleBindException(Exception exception) {

        int code = ResponseCode.UNKNOW_ERROR.getCode();
        String msg = ResponseCode.UNKNOW_ERROR.getMsg();

        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) exception;
            BindingResult bindingResult = e.getBindingResult();
            String message = bindingResult.getFieldErrors().stream()
                    .map(it -> it.getDefaultMessage() + "[" + it.getField() + "]")
                    .collect(Collectors.joining(", "));
            code = ResponseCode.PARAMS_ERROR.getCode();
            msg = message;
        } else {
            LOGGER.error("未知异常", exception);
        }

        Response response = new Response();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }
}
