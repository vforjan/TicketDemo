package hu.otp.simple.common;

public enum ErrorMessages {

	CARD_NOT_FOUND(10001, "Nincs ilyen kártya a rendszerben!"), USER_ID_NOT_FOUND(10002, "Helytelen userId!"), USER_NOT_FOUND(10003,
			"Felhasználó nem található!"), TOKEN_EXPIRED(10004, "Lejárt token"), DEVICE_UNKNOWN(10005,
					"Ismeretlen eszköz."), EMAIL_MISMATCH(10006,
							"A felhasználó emailcíme eltér a regisztrált emailcímtől. Belső hiba"), USER_INVALIDATED(10007,
									"Érvénytelen felhasználó!"), NOT_FOUND_TOKEN(10050,
											"A beérkezett kérésben a felhasználó token nem szerepel"), CARD_AND_USER_NOT_MATCH(10100,
													"Ez a bankkártya nem ehhez a felhasználóhoz tartozik"), NOT_ENOUGH_COVERAGE(10101,
															"A felhasználónak nincs elegendő pénze hogy megvásárolja a jegyet!"), EVENT_NOT_EXIST(
																	90001, "Nem létezik ilyen esemény!"), SEAT_NOT_EXIST(90002,
																			"Nem létezik ilyen szék!"), RESERVED_SEAT(90010,
																					"Már lefoglalt székre nem lehet jegyet eladni!");

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

	public String getSimpleMessage() {
		return "Hibakód: " + this.getCode() + ", Üzenet: " + this.getMessage();

	}
}
