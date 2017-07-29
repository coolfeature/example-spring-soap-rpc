package uk.co.ionas.example.spring.test;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import uk.co.ionas.example.spring.model.NdfdMarshaller;
import uk.co.ionas.example.spring.model.generated.WeatherParametersType;


public class MarshallerTest extends TestBase {
	
	@Autowired
	private NdfdMarshaller ndfdMarshaller;
	
	@Test
	public void test() {
		WeatherParametersType params = new WeatherParametersType();
		JAXBElement<WeatherParametersType> el = new JAXBElement<WeatherParametersType>(new QName("weatherParametersType"), WeatherParametersType.class, params);
		String xml = ndfdMarshaller.marshall(el);
		System.out.println(xml);
	}
	

}
