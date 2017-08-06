package uk.co.ionas.example.spring.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class DaoUtils {

	
	/**
	 * 
	 * @param paths
	 * @param separator
	 * @return
	 */
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
	
	/**
	 * 
	 * @param path
	 * @param separator
	 * @return
	 */
	public static DatabasePopulator createPopulator(String path, String separator) {
		return createPopulator(new String[] { path }, separator);
	}
	
	
	/**
	 * 
	 * @param ds
	 * @param scripts
	 */
	public static void ensureTablesExist(DataSource ds, String[][] scripts) {
		try {
			DatabaseMetaData dbMeta = null;
			Connection conn = ds.getConnection();
			try {
				conn = ds.getConnection();
				dbMeta = conn.getMetaData();
				String catalog = null;
				String schemaPattern = null;
				for (int i=0; i< scripts.length; i++) {
					String[] tblScript = scripts[i];
					String tableNamePattern = tblScript[0];
					ResultSet rs = dbMeta.getTables(catalog, schemaPattern, tableNamePattern, new String[] {"TABLE"});
					int rsRow = rs.getRow();
					if (rsRow == 0) {
						System.out.println("Creating table: " + tableNamePattern);
						DatabasePopulatorUtils.execute(createPopulator(tblScript[1], ";"), ds);	
					}
					rs.close();
				}
			} finally {
				if (conn != null) conn.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
