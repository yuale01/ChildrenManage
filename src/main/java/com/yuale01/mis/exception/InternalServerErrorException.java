package com.yuale01.mis.exception;

import javax.ws.rs.core.Response;

public class InternalServerErrorException extends CommonException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InternalServerErrorException(int error, String msg)
	{
		super(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), error, msg);
	}
}