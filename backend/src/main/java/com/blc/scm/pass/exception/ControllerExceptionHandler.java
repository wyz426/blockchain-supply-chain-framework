package com.blc.scm.pass.exception;

import com.blc.scm.pass.utils.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Elaine Huang
 * @date 2024/2/25 5:13 PM
 * @signature Do it while you can!
 */
@ControllerAdvice
@Controller
public class ControllerExceptionHandler implements ErrorController {

    public String getErrorPath() {
        return "/error";
    }

    private Logger logger = LoggerFactory.getLogger("ERROR");

    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    @RequestMapping("/error")
    @ResponseBody
    public HttpResponse<Void> handleException(Exception uncaughtException){
        if (uncaughtException == null){
            return HttpResponse.failure("No found exception!",CustomError.INTERNAL_ERROR.getCode());
        }
        uncaughtException.printStackTrace();
        if (uncaughtException instanceof LocalRuntimeException){
            logger.error(((LocalRuntimeException) uncaughtException).getError().getErrMsg());
            LocalRuntimeException localRuntimeException = (LocalRuntimeException) uncaughtException;
            return HttpResponse.failure(localRuntimeException.getError().getErrMsg(),localRuntimeException.getError().getCode());
        }else{
            logger.error("error message",uncaughtException);
            return HttpResponse.failure(uncaughtException.getMessage(),CustomError.UNKNOWN_ERROR.getCode());
        }
    }
}
