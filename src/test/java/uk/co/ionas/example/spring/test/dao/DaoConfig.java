package uk.co.ionas.example.spring.test.dao;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import uk.co.ionas.example.spring.Constants;

@Profile(Constants.TEST)
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
		, "db/sql/queries/upsert.sql"
		, "db/sql/queries/select.sql"
	};
		
	public static final String[] TR_SCRIPTS = new String[] {
	};
	
	
	@Bean
	public DataSource h2ds() {
		DataSource ds = null;
		ds = db();
		DatabasePopulatorUtils.execute(uk.co.ionas.example.spring.dao.DaoConfig.createPopulator(TBL_SCRIPTS, ";"), ds);
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
