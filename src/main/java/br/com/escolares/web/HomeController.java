package br.com.escolares.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String home(){
		return "index";
	}
}
