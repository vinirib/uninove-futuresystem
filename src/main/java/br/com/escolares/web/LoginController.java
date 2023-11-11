package br.com.escolares.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Controller
public class LoginController  {
	
	@GetMapping("/login")
    public String login(Model model){
        return "login";
    }
}
