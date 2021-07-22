package hu.otp.simple.ticket.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("secure-rest")
class SecureRestTemplateProperties {

	/**
	 * URL location, typically with file:// scheme, of a CA trust store file in JKS format.
	 */
	String trustStore;

	/**
	 * The store password of the given trust store.
	 */
	char[] trustStorePassword;

	/**
	 * One of the SSLContext algorithms listed at
	 * https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SSLContext .
	 */
	String protocol = "TLSv1.2";

	public String getTrustStore() {
		return trustStore;
	}

	public void setTrustStore(String trustStore) {
		this.trustStore = trustStore;
	}

	public char[] getTrustStorePassword() {
		return trustStorePassword;
	}

	public void setTrustStorePassword(char[] trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}