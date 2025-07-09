package es.udc.tfg.app.rest.common;

import es.udc.tfg.app.util.exceptions.DuplicateInstanceException;
import es.udc.tfg.app.util.exceptions.IncorrectPasswordException;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ControllerAdvice
public class CommonControllerAdvice {

    private final static String INSTANCE_NOT_FOUND_EXCEPTION_CODE = "project.exceptions.InstanceNotFoundException";
    private final static String DUPLICATE_INSTANCE_EXCEPTION_CODE = "project.exceptions.DuplicateInstanceException";
    private final static String INCORRECT_PASSWORD_EXCEPTION_CODE = "project.exceptions.IncorrectPasswordException";
    private final static String INPUT_VALIDATION_EXCEPTION_CODE = "project.exceptions.InputValidationException";

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(InstanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInstanceNotFoundException(InstanceNotFoundException exception, Locale locale) {

        String nameMessage = messageSource.getMessage(exception.getClassName(), null, exception.getClassName(), locale);
        String errorMessage = messageSource.getMessage(INSTANCE_NOT_FOUND_EXCEPTION_CODE,
                new Object[] {nameMessage, exception.getInstanceId().toString()}, INSTANCE_NOT_FOUND_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }
    @ExceptionHandler(DuplicateInstanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleDuplicateInstanceException(DuplicateInstanceException exception, Locale locale) {

        String nameMessage = messageSource.getMessage(exception.getClassName(), null, exception.getClassName(), locale);
        String errorMessage = messageSource.getMessage(DUPLICATE_INSTANCE_EXCEPTION_CODE,
                new Object[] {nameMessage, exception.getInstanceId().toString()}, DUPLICATE_INSTANCE_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handleIncorrectPasswordException (IncorrectPasswordException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(INCORRECT_PASSWORD_EXCEPTION_CODE,null,INCORRECT_PASSWORD_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(InputValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleInputValidationException(InputValidationException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(INPUT_VALIDATION_EXCEPTION_CODE,new Object[] {exception.getMessage()},INPUT_VALIDATION_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }
    }
