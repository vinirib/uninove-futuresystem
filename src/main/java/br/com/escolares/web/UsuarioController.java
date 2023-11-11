package br.com.escolares.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.escolares.domain.RoleType;
import br.com.escolares.domain.Usuario;
import br.com.escolares.repository.UsuarioRepository;
import br.com.escolares.service.UsuarioServiceImpl;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Controller
@RequestMapping("usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;

	@Autowired
	private UsuarioRepository usuarioRepository;
		
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(       Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));   
	}
	
	
	@RequestMapping("listar")
	public String listar(Model model){
		List<Usuario> usuarios = usuarioRepository.findAll(new Sort("name"));
		model.addAttribute("usuarios", usuarios);
		return "usuarios/listar";
	}
	
	@GetMapping("cadastrar")
	public String cadastrar(Usuario usuario, Model model){
		model.addAttribute("roles", RoleType.values());
		model.addAttribute("today", Calendar.getInstance());
		return "usuarios/cadastrar";
	}
	
	@GetMapping("editar/{id}")
	public String editar(@PathVariable("id")Integer id, Model model){
		Usuario usuario = usuarioRepository.findOne(id);
		model.addAttribute("roles", RoleType.values());
		model.addAttribute("today", Calendar.getInstance());
		model.addAttribute("usuario", usuario);
		return "usuarios/cadastrar";
	}
	
	@GetMapping("editar")
	public String editarUsuarioLogado(Model model){
		Usuario loggedUser = usuarioServiceImpl.getLoggedUser();
		model.addAttribute("roles", RoleType.values());
		model.addAttribute("today", Calendar.getInstance());
		model.addAttribute("usuario", loggedUser);
		return "usuarios/cadastrar";
	}
	
    @PostMapping("gravar")
    public String gravar(@Valid Usuario user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
   	    if (bindingResult.hasErrors()) {
   	    	model.addAttribute("roles", RoleType.values());
   	    	model.addAttribute("today", Calendar.getInstance());
            return "usuarios/cadastrar";
        }
    	usuarioServiceImpl.save(user);
    	redirectAttributes.addFlashAttribute("sucesso", "Usuario: "+user.getName()+" cadastrado com sucesso!");
    	return "redirect:/";
    }
    
	@PostMapping("remover")
	public String remover(Integer id, Model model, RedirectAttributes redirectAttributes){
		Usuario usuario = usuarioRepository.findOne(id);
		if (usuario.getUsername() == "admin@futuresystem.com.br") {
			redirectAttributes.addFlashAttribute("Ops", "Usuario: "+usuario.getName()+" não pode ser apagado do sistema.");
			return "redirect:/";
		}
		usuarioRepository.delete(usuario);
		redirectAttributes.addFlashAttribute("sucesso", "Usuário: " + usuario.getName() + " removido com sucesso!");
		return "redirect:/";
		
	}
	
	@PostMapping("ativar/{id}")
	@ResponseBody
	public String ativar(@PathVariable("id")Integer id){
		Usuario usuario = usuarioRepository.findOne(id);
		usuario.setStatus(Boolean.TRUE);
		saveUsuarioState(usuario);
		return "Usuario "+usuario.getName()+ " ativado com sucesso";
	}


	private void saveUsuarioState(Usuario usuario) {
		usuario.setPasswordForm(usuario.getPassword());
		usuario.setPasswordConfirmForm(usuario.getPassword());
		usuarioRepository.save(usuario);
	}
	
	@PostMapping("desativar/{id}")
	@ResponseBody
	public String desativar(@PathVariable("id")Integer id){
		Usuario usuario = usuarioRepository.findOne(id);
		usuario.setStatus(Boolean.FALSE);
		saveUsuarioState(usuario);
		return "Usuario "+usuario.getName()+ " desativado com sucesso";
	}

}
