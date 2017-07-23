package uk.co.ionas.example.spring.ws;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Configuration
public class WsConfig {

	/**
	 * Returns HttpClient instance
	 *
	 * @return HttpClient Bean
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	@Bean
	public HttpClient httpClient() throws NoSuchAlgorithmException, KeyManagementException {

		
		// set up a TrustManager that trusts everything
		
		X509TrustManager trustManager = new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                    System.out.println("\n\n\n\n\n\n\ngetAcceptedIssuers =============");
                    return null;
            }

            public void checkClientTrusted(X509Certificate[] certs,
                            String authType) {
                    System.out.println("\n\n\n\n\n\n\ncheckClientTrusted =============");
            }

            public void checkServerTrusted(X509Certificate[] certs,
                            String authType) {
                    System.out.println("checkServerTrusted =============");
            }
		};
		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, new TrustManager[] { trustManager }, new SecureRandom());
		
		Registry<ConnectionSocketFactory> csf = RegistryBuilder.<ConnectionSocketFactory>create()
			.register("http", PlainConnectionSocketFactory.INSTANCE)
			.register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
			.build();
         
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(csf);
		int max = 5000;
		int socTimeout = max;
		cm.setMaxTotal(max);
		cm.setDefaultMaxPerRoute(max);
		SocketConfig socketConfig = SocketConfig.custom()
				/* Determines the maximum queue length for incoming connection indications 
				 * (a request to connect) also known as server socket backlog.*/
				//.setBacklogSize(100) 
				/* Determines the default value of the SocketOptions.SO_RCVBUF 
				 * parameter for newly created sockets */
				//.setRcvBufSize(SocketOptions.SO_RCVBUF)
				/* Determines the default value of the SocketOptions.SO_SNDBUF 
				 * parameter for newly created sockets. */
				//.setSndBufSize(SocketOptions.SO_SNDBUF)
				/* Determines the default value of the SocketOptions.SO_LINGER 
				 * parameter for newly created sockets. */
				//.setSoLinger(SocketOptions.SO_LINGER)
				/* Determines the default value of the SocketOptions.SO_REUSEADDR
				 * parameter for newly created sockets. */
				//.setSoReuseAddress(false)
				/* Determines the default socket timeout SocketOptions.SO_TIMEOUT 
				 * value for non-blocking I/O operations. */
				.setSoTimeout(socTimeout)
				/* Determines the default value of the SocketOptions.TCP_NODELAY 
				 * parameter for newly created sockets. */
				//.setTcpNoDelay(false)
				.build();

		int connTimeoutMilis = max;
		RequestConfig reqConfig = RequestConfig.custom()
				/* The timeout in milliseconds used when requesting a connection 
				 * from the connection manager. */
				.setConnectionRequestTimeout(connTimeoutMilis)
				/* Determines the timeout in milliseconds until a connection is established. */
				.setConnectTimeout(connTimeoutMilis)
				.build();

		/**
		 * Http client sets default Content-Length etc headers on immutable object. Setting headers 
		 * later in the WebServiceTemplate again result in exception. HttpRequestInterceptor 
		 * interceptor is added to prevent apache client from setting any default headers.
		 * @see https://jira.spring.io/browse/SWS-835
		 */
		HttpRequestInterceptor noHeader = new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor();
		
		CloseableHttpClient client = HttpClients.custom()
				.setConnectionManager(cm)
				.setDefaultSocketConfig(socketConfig)
				.addInterceptorFirst(noHeader)
				.setDefaultRequestConfig(reqConfig)
				.build();
		return client;
	}
}
