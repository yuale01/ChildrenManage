package com.yuale01.mis.exception;

import javax.ws.rs.core.Response;

public class ConflictException extends CommonException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public ConflictException(int error, String msg) {
        super(Response.Status.CONFLICT.getStatusCode(), error, msg);
    }
}
