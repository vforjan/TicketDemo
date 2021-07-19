package hu.otp.simple.domain;

public enum ErrorMessages {

	CARD_NOT_FOUND(10001, "Nincs ilyen kártya a rendszerben!"), NOT_FOUND_TOKEN(10050,
			"A beérkezett kérésben a felhasználó token nem szerepel"), CARD_AND_USER_NOT_MATCH(10100,
					"Ez a bankkártya nem ehhez a felhasználóhoz tartozik"), NOT_ENOUGH_COVERAGE(10101,
							"A felhasználónak nincs elegendő pénze hogy megvásárolja a jegyet!");

	private Integer code;
	private String message;

	private ErrorMessages(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return this.getCode() + " - " + this.getMessage();
	}
}
