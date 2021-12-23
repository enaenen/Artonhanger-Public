package com.artonhanger.manage.exception;



import com.artonhanger.manage.enums.ErrorEnum;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private NumberFormat numberFormatter = new DecimalFormat("#0.00");

    @ExceptionHandler({AOHException.class})
    public ResponseEntity<ErrorEnum.ErrorResponse> handleAOHException(HttpServletRequest request, HttpServletResponse response, AOHException ex) throws IOException {
        ErrorEnum errorEnum = ex.getErrorEnum();
        logger.error("AOH 오류 감지. - Enum : {}", errorEnum, ex);
        ErrorEnum.ErrorResponse errorResponse = errorEnum.getErrorResponse();
        return new ResponseEntity<>(errorResponse, errorEnum.getHttpStatus());
    }

    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<ErrorEnum.ErrorResponse> handleHeaderException(HttpServletRequest request, HttpServletResponse response, MissingRequestHeaderException ex) throws IOException {
        ErrorEnum unauthorized = ErrorEnum.UNAUTHORIZED;
        return handleAOHException(request, response, new AOHException(unauthorized));
    }

    @ExceptionHandler({InvalidDataAccessResourceUsageException.class})
    public ModelAndView handleArtworkListResultNotfoundException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("errors/404");
        ex.printStackTrace();
        logger.error("작품을 찾을 수 없음.", ex);
        return mav;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorEnum.ErrorResponse> handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        ex.printStackTrace();
        logger.error("알 수 없는 오류 감지.", ex);
        return handleAOHException(request, response, new AOHException(ErrorEnum.ETC));
    }

    /* 회원가입 예외 처리 정의 끝 */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorEnum.ErrorResponse> handleException(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException ex) throws IOException {
        return handleAOHException(request, response, new AOHException(ErrorEnum.MEMBER_DUPLICATED_ID));
    }

    /* 회원가입 예외 처리 정의 끝 */

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorEnum.ErrorResponse> handleException(HttpServletRequest request, HttpServletResponse response, DataIntegrityViolationException ex) throws IOException {
        return handleAOHException(request, response, new AOHException(ErrorEnum.MEMBER_DUPLICATED_ID));
    }

    @ExceptionHandler({UnsupportedEncodingException.class})
    public ResponseEntity<ErrorEnum.ErrorResponse> handleException(HttpServletRequest request, HttpServletResponse response, UnsupportedEncodingException ex) throws IOException {
        return handleAOHException(request, response, new AOHException(ErrorEnum.SEARCH_UNSUPPORTED_CHARSET));
    }

    @ExceptionHandler({MultipartException.class})
    public ResponseEntity<ErrorEnum.ErrorResponse> handleFileException(HttpServletRequest request, HttpServletResponse response, MultipartException e) throws IOException {
        String message = null;
        if (e.getCause() instanceof IllegalStateException) {
            if (((MultipartException) e).getRootCause() instanceof SizeException) {
                SizeException sizeException = (SizeException) ((MultipartException) e).getRootCause();
                if (sizeException instanceof FileSizeLimitExceededException) {
                    FileSizeLimitExceededException cause = (FileSizeLimitExceededException) sizeException;
                    message = "File Size should be less than: " + numberFormatter.format(cause.getPermittedSize() / Math.pow(2F, 20)) + "MB";
                    return handleAOHException(request, response, new AOHException(ErrorEnum.FILE_UPLOAD_MAX_FILE_SIZE_OVER, message));
                } else if (sizeException instanceof SizeLimitExceededException) {
                    SizeLimitExceededException cause = (SizeLimitExceededException) sizeException;
                    message = "Request Size should be less than: " + numberFormatter.format(cause.getPermittedSize() / Math.pow(2F, 20)) + "MB";
                    return handleAOHException(request, response, new AOHException(ErrorEnum.FILE_UPLOAD_REQUEST_MAX_FILE_SIZE_OVER, message));
                }
            }
        }
        return handleAOHException(request, response, new AOHException(ErrorEnum.ETC));
    }
}
