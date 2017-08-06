package uk.co.ionas.example.spring.test.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import uk.co.ionas.example.spring.common.Constants;
import uk.co.ionas.example.spring.dao.DaoUtils;


@Profile(Constants.TEST)
@Configuration
public class DataSources {

	private static final String[] QUERY_SCRIPTS = new String[] {
		"db/sql/queries/upsert.sql"
		, "db/sql/queries/select.sql"
	};

	@Value("${jdbc.hsql.url}")
    private String dbUrl;
	
	@Value("${jdbc.hsql.username}")
    private String dbUsername;
	
	@Value("${jdbc.hsql.password}")
    private String dbPassword;
	
	@Bean
	public DataSource ds() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
		ds.setUrl(dbUrl);
		ds.setUsername(dbUsername);
		ds.setPassword(dbPassword);
		DaoUtils.ensureTablesExist(ds, uk.co.ionas.example.spring.dao.DataSources.TBL_SCRIPTS);
		return ds;
	}
}
