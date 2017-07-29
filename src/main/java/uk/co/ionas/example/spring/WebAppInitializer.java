package uk.co.ionas.example.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import uk.co.ionas.example.spring.common.Constants;

@Configuration
@ComponentScan
public class WebAppInitializer implements WebApplicationInitializer {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Bean
	public PropertyPlaceholderConfigurer properties() {
		PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
		String dbProperties = "db/h2.properties";
		props.setLocations(
			new ClassPathResource(dbProperties)
		);
		return props;
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(this.getClass().getName());

		LOGGER.info("Registering DispatcherServlet");
		DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
		final ServletRegistration.Dynamic dispatcher = servletContext
				.addServlet("dispatcherServlet", dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(Constants.API_API, Constants.API_ROOT);

			
		servletContext.addListener(new ContextLoaderListener(context));
	}
}
