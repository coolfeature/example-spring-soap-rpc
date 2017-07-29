package uk.co.ionas.example.spring.dao;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import uk.co.ionas.example.spring.Constants;

@Profile("!" + Constants.TEST)
@Configuration
public class DaoConfig {

	@Value("${jdbc.h2.url}")
    private String h2Url;
	
	public static final String[] TBL_SCRIPTS = new String[] {
		"db/sql/tables/APP_CONSTANTS.sql"
	};
		
	public static final String[] TR_SCRIPTS = new String[] {
	};
	
	public static final String[] TR_SCRIPTS_H2 = new String[] {
	};
	
	public static DatabasePopulator createPopulator(String[] paths, String separator) {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.setContinueOnError(false);
		pop.setSeparator(separator);
		Resource[] rsc = new ClassPathResource[paths.length];
		for (int i = 0; i < paths.length; i++) {
			rsc[i] = new ClassPathResource(paths[i]);
		}
		pop.addScripts(rsc);
		return pop;
	}
	
	
	@Bean
	public DataSource h2ds() {
		DataSource ds = null;
		ds = h2();
		DatabasePopulatorUtils.execute(DaoConfig.createPopulator(TBL_SCRIPTS, ";"), ds);
		DatabasePopulatorUtils.execute(DaoConfig.createPopulator(TR_SCRIPTS_H2, ";"), ds);
		return ds;
	}
	
	
	private DataSource h2() { 
		JdbcDataSource ds = new JdbcDataSource();
		ds.setUser("sa");
		ds.setPassword("sa");
		ds.setUrl(h2Url);
		return ds;
	}
}
