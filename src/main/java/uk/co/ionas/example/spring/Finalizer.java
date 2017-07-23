package uk.co.ionas.example.spring;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Finalizer implements DisposableBean {

	private static Logger LOGGER = LogManager.getLogger(Finalizer.class);

	@Autowired
	private HttpClient httpClient;

	@Override
	public void destroy() throws Exception {
		LOGGER.info("Closing httpClient... ");
		((CloseableHttpClient) httpClient).close();
		LOGGER.info("Cleaning up...");
		LogManager.shutdown();
	}

}