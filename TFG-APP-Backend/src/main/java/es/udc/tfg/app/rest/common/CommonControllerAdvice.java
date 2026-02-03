package es.udc.tfg.app.rest.common;

import es.udc.tfg.app.util.exceptions.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Locale;

@ControllerAdvice
public class CommonControllerAdvice {

    private final static String INSTANCE_NOT_FOUND_EXCEPTION_CODE = "project.exceptions.InstanceNotFoundException";
    private final static String DUPLICATE_INSTANCE_EXCEPTION_CODE = "project.exceptions.DuplicateInstanceException";
    private final static String INCORRECT_PASSWORD_EXCEPTION_CODE = "project.exceptions.IncorrectPasswordException";
    private final static String INPUT_VALIDATION_EXCEPTION_CODE = "project.exceptions.InputValidationException";
    private final static String DISABLED_USER_EXCEPTION_CODE = "project.exceptions.DisabledUserException";
    private final static String INTERNAL_SERVER_ERROR_CODE = "project.exceptions.InternalServerError";
    private final static String INVOICE_ATTACHED_EXCEPTION_CODE = "project.exceptions.InvoiceAttachedException";
    private final static String INVALID_PRODUCT_STOCK_EXCEPTION_CODE = "project.exceptions.InvalidProductStockException";
    private final static String INCORRECT_LOGIN_EXCEPTION_CODE = "project.exceptions.IncorrectLoginException";
    private final static String MESSAGING_EXCEPTION_CODE = "project.exceptions.MessagingException";

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

    @ExceptionHandler(DisabledUserException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handleDisabledUserException(DisabledUserException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(DISABLED_USER_EXCEPTION_CODE, new Object[]{exception.getUserId()}, DISABLED_USER_EXCEPTION_CODE,locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorsDto handleIOException(IOException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(INTERNAL_SERVER_ERROR_CODE, null, INTERNAL_SERVER_ERROR_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(CreateInvoiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleCreateInvoiceException(CreateInvoiceException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(
                exception.getMessage(), null, exception.getMessage(), locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(InvoiceAttachedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handleInvoiceAttachedException(InvoiceAttachedException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(INVOICE_ATTACHED_EXCEPTION_CODE, null, INVOICE_ATTACHED_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(InvalidProductStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleInvalidProductStockException(InvalidProductStockException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(INVALID_PRODUCT_STOCK_EXCEPTION_CODE, null, INVALID_PRODUCT_STOCK_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(IncorrectLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorsDto handleIncorrectLoginException(IncorrectLoginException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(INCORRECT_LOGIN_EXCEPTION_CODE, null, INCORRECT_LOGIN_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorsDto handleMessagingException(MessagingException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(MESSAGING_EXCEPTION_CODE, null, MESSAGING_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }
}