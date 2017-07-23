package uk.co.ionas.example.spring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import uk.co.ionas.example.spring.common.Constants;

/**
 * @author Szymon
 *
 */
@RestController
public class MainController {

	@RequestMapping(Constants.API_ROOT)
	public ModelAndView index() {
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView model = new ModelAndView("index");
		model.addObject("data", map);
		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = Constants.API_API, produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello() {
		return "hello";
	}
}
