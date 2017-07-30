package uk.co.ionas.example.spring.dao;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDataSource;
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

	@Value("${jdbc.hsql.url}")
    private String dbUrl;
	
	@Value("${jdbc.hsql.username}")
    private String dbUsername;
	
	@Value("${jdbc.hsql.password}")
    private String dbPassword;
	
	public static final String[] TBL_SCRIPTS = new String[] {
		"db/sql/tables/APP_CONSTANTS.sql"
	};
		
	public static final String[] TR_SCRIPTS = new String[] {
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
		ds = db();
		DatabasePopulatorUtils.execute(DaoConfig.createPopulator(TBL_SCRIPTS, ";"), ds);
		return ds;
	}
	
	
	private DataSource db() { 
		JDBCDataSource ds = new JDBCDataSource();
		ds.setDatabase(dbUrl);
		ds.setUser(dbUsername);
		ds.setPassword(dbPassword);
		return ds;
	}
}
