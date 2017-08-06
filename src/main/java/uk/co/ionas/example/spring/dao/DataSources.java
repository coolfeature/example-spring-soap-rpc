package uk.co.ionas.example.spring.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import uk.co.ionas.example.spring.common.Constants;

@Profile("!" + Constants.TEST)
@Configuration
public class DataSources {

	public static final String[][] TBL_SCRIPTS = new String[][] {
		{"APP_CONSTANTS", "db/sql/tables/APP_CONSTANTS.sql"}
	};
	
	@Value("${jdbc.mssql.url}")
    private String dbUrl;
	
	@Value("${jdbc.mssql.username}")
    private String dbUsername;
	
	@Value("${jdbc.mssql.password}")
    private String dbPassword;
	
	@Bean
	public DataSource ds() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl(dbUrl);
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUsername(dbUsername);
		ds.setPassword(dbPassword);
		DaoUtils.ensureTablesExist(ds, TBL_SCRIPTS);
		return ds;
	}
	
}
