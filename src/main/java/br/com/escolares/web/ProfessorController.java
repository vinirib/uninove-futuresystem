package br.com.escolares.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.escolares.domain.Materia;
import br.com.escolares.domain.Professor;
import br.com.escolares.repository.ProfessorRepository;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Controller
@RequestMapping("professores")
public class ProfessorController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProfessorRepository professorRepository;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
	}

	@RequestMapping("listar")
	public String listar(Model model) {
		List<Professor> professores = professorRepository.findAll(new Sort("nome"));
		model.addAttribute("professores", professores);
		return "/professores/listar";
	}

	@GetMapping("cadastrar")
	public String cadastrar(Professor professor, Model model) {
		model.addAttribute("materias", Materia.values());
		return "professores/cadastrar";
	}

	@PostMapping("gravar")
	@Transactional(value=TxType.REQUIRES_NEW)
	public String gravar(@Valid Professor professor, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("materias", Materia.values());
			return "professores/cadastrar";
		}
		professorRepository.save(professor);
		redirectAttributes.addFlashAttribute("sucesso", "Usuario: " + professor.getNome() + " cadastrado com sucesso!");
		return "redirect:/";
	}
	
	@GetMapping("editar/{id}")
	public String editar(@PathVariable("id")Integer id, Model model){
		Professor professor = professorRepository.findOne(id);
		model.addAttribute("professor", professor);
		model.addAttribute("materias", Materia.values());
		return "professores/cadastrar";
	}
	
	@PostMapping("remover")
	public String remover(Integer id, Model model, RedirectAttributes redirectAttributes){
		Professor professor = professorRepository.findOne(id);
		professorRepository.delete(professor);
		redirectAttributes.addFlashAttribute("sucesso", "Professor: " + professor.getNome() + " removido com sucesso!");
		return "redirect:/";
		
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception ex) {
		logger.error("Request: " + req.getRequestURL() + " raised " + ex);
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName("professores/cadastrar");
		return mav;
	}

}
