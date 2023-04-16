package org.client.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // используется для глобальной обработки ошибок в приложении Spring. То есть любой Exception, выпадающий в нашем приложении, будет замечен нашим ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler  // Эта аннотация позволяет нам указать, что мы хотим перехватывать и обрабатывать исключения определённого типа, если они возникают, и зашивать их в ResponseEntity, чтобы вернуть ответ нашему фронту.
    public ResponseEntity<AppError> catchNotFoundException(NotFoundException e) {
        //log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchConnectException(ConnectException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_GATEWAY.value(), e.getMessage()), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchIncorrectRequestToDbException(IncorrectRequestToDbException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchEmptyFieldException(EmptyFieldException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NO_CONTENT.value(), e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchBadAuthException(BadAuthException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchRepeatDataException(RepeatDataException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchIncorrectRequestException(IncorrectRequestException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchNotEnoughMoneyException(NotEnoughMoneyException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
