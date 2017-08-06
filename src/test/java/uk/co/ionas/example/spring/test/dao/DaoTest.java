package uk.co.ionas.example.spring.test.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import uk.co.ionas.example.spring.dao.MainDao;
import uk.co.ionas.example.spring.test.TestBase;

public class DaoTest extends TestBase {

	@Autowired
	private MainDao daoMain;
	
	@Test
	public void test() {
		System.out.println("testing");
	}
	
	@Test
	public void select() {
		System.out.println(daoMain.select());
	}
}
