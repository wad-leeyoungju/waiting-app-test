package co.wadcorp.waiting.api.controller;

import co.wadcorp.waiting.data.api.ApiResponse;
import co.wadcorp.waiting.data.exception.AppException;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import java.net.BindException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleUnhandledException(Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Void> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Void> handleMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage(), e);
        Sentry.captureException(e);

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse<Object>> bindException(org.springframework.validation.BindException e) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String code = String.valueOf(status.value());

        apiResponse.setResultCode(code);
        apiResponse.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        return ResponseEntity.status(status).body(apiResponse);
    }

    @ExceptionHandler({AppException.class})
    public ResponseEntity<ApiResponse<Object>> handleAppException(AppException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        String code = e.getCode();
        if(code != null) {
            status = HttpStatus.resolve(Integer.parseInt(code));
        }
        apiResponse.setResultCode(code);
        apiResponse.setMessage(e.getMessage());
        apiResponse.setDisplayMessage(e.getDisplayMessage());
        apiResponse.setData(e.getData());

        assert status != null;
        return ResponseEntity.status(status)
                .body(apiResponse);
    }
}
