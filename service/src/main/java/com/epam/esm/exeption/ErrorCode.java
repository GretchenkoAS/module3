package com.epam.esm.exeption;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    TAG_NOT_FOUND("T1", HttpStatus.NOT_FOUND),
    TAG_NO_CONTENT("T1", HttpStatus.NO_CONTENT),
    TAG_NAME_INVALID("T40", HttpStatus.BAD_REQUEST),
    TAG_NAME_IS_NULL("T41", HttpStatus.BAD_REQUEST),
    TAG_ALREADY_EXIST("T3", HttpStatus.BAD_REQUEST),
    QUERY_CONTAINS_INVALID("Q1", HttpStatus.BAD_REQUEST),
    QUERY_SORT_BY_NAME_INVALID("Q2", HttpStatus.BAD_REQUEST),
    QUERY_SORT_BY_DATE_INVALID("Q3", HttpStatus.BAD_REQUEST),
    GIFT_CERTIFICATE_NAME_INVALID("GC40", HttpStatus.BAD_REQUEST),
    GIFT_CERTIFICATE_DESCRIPTION_INVALID("GC41", HttpStatus.BAD_REQUEST),
    GIFT_CERTIFICATE_PRICE_INVALID("GC42", HttpStatus.BAD_REQUEST),
    GIFT_CERTIFICATE_DURATION_INVALID("GC43", HttpStatus.BAD_REQUEST),
    GIFT_CERTIFICATE_NAME_IS_NULL("GC44", HttpStatus.BAD_REQUEST),
    GIFT_CERTIFICATE_DESCRIPTION_IS_NULL("GC45", HttpStatus.BAD_REQUEST),
    GIFT_CERTIFICATE_PRICE_IS_NULL("GC46", HttpStatus.BAD_REQUEST),
    GIFT_CERTIFICATE_DURATION_IS_NULL("GC47", HttpStatus.BAD_REQUEST),
    GIFT_CERTIFICATE_ALREADY_EXIST("GC3", HttpStatus.BAD_REQUEST),
    GIFT_CERTIFICATE_NOT_FOUND("GC1", HttpStatus.NOT_FOUND),
    GIFT_CERTIFICATE_NO_CONTENT("GC1", HttpStatus.NO_CONTENT),
    MESSAGE_NOT_READABLE("G1", HttpStatus.BAD_REQUEST),
    BAD_REQUEST("G2", HttpStatus.BAD_REQUEST),
    METHOD_ARGUMENT_TYPE_MISMATCH("G3", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("G4", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA_TYPE_NOT_SUPPORTED("G6", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("U1", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND("O1", HttpStatus.NOT_FOUND),
    ORDER_NO_CONTENT("O1", HttpStatus.NO_CONTENT),
    URL_INVALID("G5", HttpStatus.NOT_FOUND);

    private String code;
    private HttpStatus httpStatus;

    ErrorCode(String code, HttpStatus httpStatus){
        this.code = code;
        this.httpStatus = httpStatus;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
