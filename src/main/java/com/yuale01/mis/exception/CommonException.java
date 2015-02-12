package com.yuale01.mis.exception;

import javax.ws.rs.core.Response;

public class CommonException extends Exception {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1641182270942847057L;
    private int               statusCode;
    private int               errorCode;

    CommonException(int status, int error, String msg) {
        super(msg);
        statusCode = status;
        errorCode = error;
    }

    CommonException(int error, String msg) {
        super(msg);
        statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        errorCode = error;
    }

    CommonException(ErrorMessage errorMsg) {
        super(errorMsg.getMessage());
        statusCode = errorMsg.getStatusCode();
        errorCode = errorMsg.getErrorCode();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
