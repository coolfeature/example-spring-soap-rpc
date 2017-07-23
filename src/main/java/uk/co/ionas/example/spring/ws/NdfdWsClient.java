package uk.co.ionas.example.spring.ws;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import uk.co.ionas.example.spring.model.NdfdMarshaller;
import uk.co.ionas.example.spring.model.generated.WeatherParametersType;

@Component
public class NdfdWsClient extends WebServiceGatewaySupport {

	private final NdfdMarshaller ndfdMarshaller;

	protected final Logger LOGGER = LogManager.getLogger(this.getClass());

	private static final String URL = "https://graphical.weather.gov/xml/SOAP_server/ndfdXMLserver.php";
	
	@Autowired
	public NdfdWsClient(NdfdMarshaller ndfdMarshaller, HttpClient httpClient) {
		this.ndfdMarshaller = ndfdMarshaller;
		SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
		messageFactory.setSoapVersion(SoapVersion.SOAP_11);
		messageFactory.afterPropertiesSet();
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate(ndfdMarshaller);
		webServiceTemplate.setMessageFactory(messageFactory);
		WebServiceMessageSender sender = new HttpComponentsMessageSender(httpClient);
		webServiceTemplate.setMessageSender(sender);
		this.setWebServiceTemplate(webServiceTemplate);
	}
	
	
	public String call(WeatherParametersType params) {
		WebServiceMessageCallback cb = new NdfdSOAPActionMessageCallback();
		JAXBElement<WeatherParametersType> el = new JAXBElement<WeatherParametersType>(new QName("weatherParametersType"), WeatherParametersType.class, params);
		
		//String resp = (String) this.getWebServiceTemplate()
		//		.marshalSendAndReceive(URL, el, cb);
		
		/**
		 * Figure out how to generate XML below from WSDL!
		 */
		String MESSAGE = 
		"<m:NDFDgen xmlns:m=\"https://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl\">"
			+ "<m:latitude>40.71</m:latitude>"
			+ "<m:longitude>-74.07</m:longitude>"
			+ "<m:product>time-series</m:product>"
			+ "<m:startTime>2017-01-01</m:startTime>"
			+ "<m:endTime>2017-01-02</m:endTime>"
			+ "<m:Unit>m</m:Unit>"
			+ "<m:weatherParameters/>" 
		 + "</m:NDFDgen>";
		StreamSource source = new StreamSource(new StringReader(MESSAGE));
		StringWriter sw = new StringWriter();
		this.getWebServiceTemplate().sendSourceAndReceiveToResult(URL, source, new StreamResult(sw));
		String resp = sw.toString();

		return resp;
	}
	
	
	protected static class NdfdSOAPActionMessageCallback implements WebServiceMessageCallback {

		String soapAction = "https://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl#NDFDgen";
		
		@Override
		public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
			SoapMessage soapMessage = ((SoapMessage) message);
			soapMessage.setSoapAction(soapAction);

            SoapHeader soapHeader = soapMessage.getSoapHeader();
            QName wsaToQName = new QName("http://www.w3.org/2005/08/addressing", "To", "wsa");
            SoapHeaderElement wsaTo =  soapHeader.addHeaderElement(wsaToQName);
            wsaTo.setText(soapAction);
             
            QName wsaActionQName = new QName("http://www.w3.org/2005/08/addressing", "Action", "wsa");
            SoapHeaderElement wsaAction =  soapHeader.addHeaderElement(wsaActionQName);
            wsaAction.setText(soapAction);
		}
		
	}
}
