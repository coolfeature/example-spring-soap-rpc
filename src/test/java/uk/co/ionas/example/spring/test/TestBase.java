package uk.co.ionas.example.spring.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uk.co.ionas.example.spring.common.Constants;
import uk.co.ionas.example.spring.WebAppInitializer;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Constants.TEST)
@ContextConfiguration(classes = { WebAppInitializer.class })
@Configuration
public class TestBase {

	@Bean
	@Profile(Constants.TEST)
	public PropertyPlaceholderConfigurer properties() {
		PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
		String dbProperties = "db/hsql.properties";
		props.setLocations(
			new ClassPathResource(dbProperties)
		);
		return props;
	}
	
}
