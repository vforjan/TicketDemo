package hu.otp.simple.ticket.security;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import javax.net.ssl.SSLContext;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.ssl.SSLContextBuilder;

public class SecurityUtils {

	@Value("${secure-rest.trust_store}")
	Resource trustStore;
	@Value("${secure-rest.trust_store_password}")
	String trustStorePassword;

	// public RestTemplate restTemplate() throws Exception {
	// return new RestTemplate();
	// }

	public RestTemplate restTemplate() throws Exception {
		System.out.println("<<<< " + trustStore.getURL());
		System.out.println("<<<< " + trustStorePassword);

		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray()).build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(factory);
	}

}
