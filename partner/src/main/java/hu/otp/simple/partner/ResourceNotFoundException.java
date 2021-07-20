package hu.otp.simple.partner;

public class ResourceNotFoundException extends RuntimeException {

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
