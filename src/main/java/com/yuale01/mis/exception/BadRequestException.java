package com.yuale01.mis.exception;

import javax.ws.rs.core.Response;

public class BadRequestException extends CommonException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public BadRequestException(int error, String msg) {
        super(Response.Status.BAD_REQUEST.getStatusCode(), error, msg);
    }
}