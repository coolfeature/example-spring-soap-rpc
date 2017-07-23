package uk.co.ionas.example.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.ionas.example.spring.model.generated.WeatherParametersType;
import uk.co.ionas.example.spring.ws.NdfdWsClient;

@Service
public class NdfdService {

	@Autowired
	private NdfdWsClient ndfdWsClient;
	
	public String call() {
		WeatherParametersType params = new WeatherParametersType();
		params.setSnow(true);
		return ndfdWsClient.call(params);
	}
	
	
}
