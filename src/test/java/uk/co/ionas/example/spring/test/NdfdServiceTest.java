package uk.co.ionas.example.spring.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import uk.co.ionas.example.spring.service.NdfdService;

public class NdfdServiceTest extends TestBase {

	@Autowired
	private NdfdService ndfdService;
	
	@Test
	public void test() {
		String resp = ndfdService.call();
		System.out.println("\n\n\n\n" + resp);

	}
}
