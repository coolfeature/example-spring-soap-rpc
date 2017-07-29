package uk.co.ionas.example.spring.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uk.co.ionas.example.spring.Constants;
import uk.co.ionas.example.spring.WebAppInitializer;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Constants.TEST)
@ContextConfiguration(classes = { WebAppInitializer.class })
public class TestBase {

}
