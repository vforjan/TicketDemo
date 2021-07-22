package hu.otp.simple.ticket.security;

import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@EnableConfigurationProperties(SecureRestTemplateProperties.class)
public class SecureRestTemplateCustomizer implements RestTemplateCustomizer {
	private static final Logger log = LoggerFactory.getLogger(SecureRestTemplateCustomizer.class);

	private final SecureRestTemplateProperties properties;

	@Autowired
	public SecureRestTemplateCustomizer(SecureRestTemplateProperties properties) {
		this.properties = properties;
	}

	@Override
	public void customize(RestTemplate restTemplate) {

		final SSLContext sslContext;
		try {
			sslContext = SSLContextBuilder.create()
					.loadTrustMaterial(new URL(properties.getTrustStore()), properties.getTrustStorePassword()).build();
		} catch (Exception e) {
			log.error("Failed to setup client SSL context.", e);
			throw new IllegalStateException("Failed to setup client SSL context", e);
		}

		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
		final HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

		final ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		log.info("Registered SSL truststore {} for client requests", properties.getTrustStore());
		restTemplate.setRequestFactory(requestFactory);
	}
}