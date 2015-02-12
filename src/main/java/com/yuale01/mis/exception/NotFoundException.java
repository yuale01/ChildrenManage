package com.yuale01.mis.exception;

import javax.ws.rs.core.Response;

public class NotFoundException extends CommonException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public NotFoundException(int error, String msg) {
        super(Response.Status.NOT_FOUND.getStatusCode(), error, msg);
    }
}