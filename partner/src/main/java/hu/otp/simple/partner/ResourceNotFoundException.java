package hu.otp.simple.partner;

import hu.otp.simple.common.AbstractErrorMessageException;

public class ResourceNotFoundException extends AbstractErrorMessageException {

	/** Serial */
	private static final long serialVersionUID = -6011643737729501390L;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public ResourceNotFoundException(String fileName) {
		super();
		this.fileName = fileName;
	}

}
