package br.com.escolares.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.escolares.domain.AjaxResponseBody;
import br.com.escolares.domain.Responsavel;
import br.com.escolares.domain.SearchCriteria;
import br.com.escolares.repository.ResponsavelRepository;
import br.com.escolares.response.ResponsavelResponse;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Controller
@RequestMapping("responsaveis")
public class ResponsavelController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ResponsavelRepository responsavelRepository;
	
	@RequestMapping("listar")
	public String listar(Model model){
		List<Responsavel> responsaveis = responsavelRepository.findAll(new Sort("nome"));
		model.addAttribute("responsaveis", responsaveis);
		return "/responsaveis/listar";
	}
	
	@GetMapping("cadastrar")
	public String cadastrar(Responsavel responsavel) {
		return "responsaveis/cadastrar";
	}
	
	@PostMapping("gravar")
	public String gravar(@Valid Responsavel responsavel, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "responsaveis/cadastrar";
		}
		responsavelRepository.save(responsavel);
		redirectAttributes.addFlashAttribute("sucesso", "Responsável: " + responsavel.getNome() + " cadastrado com sucesso!");
		return "redirect:/";
	}
	
	@GetMapping("editar/{id}")
	public String editar(@PathVariable("id")Integer id, Model model){
		Responsavel responsavel = responsavelRepository.findOne(id);
		model.addAttribute("responsavel", responsavel);
		return "responsaveis/cadastrar";
	}
	
	@PostMapping("remover")
	public String remover(Integer id, Model model, RedirectAttributes redirectAttributes){
		Responsavel responsavel = responsavelRepository.findOne(id);
		responsavelRepository.delete(responsavel);
		redirectAttributes.addFlashAttribute("sucesso", "Responsável: " + responsavel.getNome() + " removido com sucesso!");
		return "redirect:/";
		
	}

	@RequestMapping(value="buscarPorCpf", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public  ResponseEntity<?> buscarResponsavelPorCpf(@RequestBody SearchCriteria search, Errors errors){
		String msg = "";
		AjaxResponseBody<ResponsavelResponse> result = new AjaxResponseBody<ResponsavelResponse>();

        Responsavel responsavel = responsavelRepository.findByCpf(search.getCpf());
        if (responsavel == null) {
            msg = "Responsável não encontrado!";
            result.setResult(new ResponsavelResponse(null, null, null, null, msg));
            return ResponseEntity.badRequest().body(result);
        } else {
            msg = "Encontrado";
        }
        result.setResult(new ResponsavelResponse(responsavel.getId(),responsavel.getNome(), responsavel.getCpf(), responsavel.getTelefone(), msg));
        return ResponseEntity.ok(result);
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
