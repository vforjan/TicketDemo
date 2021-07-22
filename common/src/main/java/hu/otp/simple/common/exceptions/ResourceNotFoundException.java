package hu.otp.simple.common.exceptions;

import hu.otp.simple.common.AbstractErrorMessageException;
import hu.otp.simple.common.ErrorMessages;

public class ResourceNotFoundException extends AbstractErrorMessageException {

	/** Serial */
	private static final long serialVersionUID = -6011643737729501390L;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public ResourceNotFoundException(String fileName, ErrorMessages errorMessage) {
		super(errorMessage);
		this.fileName = fileName;
	}

}
