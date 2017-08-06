package uk.co.ionas.example.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MainDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
		
	public String select() {
		Assert.notNull(jdbcTemplate);
		return jdbcTemplate.queryForObject("SELECT NAME FROM APP_CONSTANTS", new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int ix) throws SQLException {
				String name = rs.getString(ix);
				return name;
			}
			
		});
	}
	
}
