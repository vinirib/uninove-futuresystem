package br.com.escolares.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.escolares.domain.AjaxResponseBody;
import br.com.escolares.domain.Aluno;
import br.com.escolares.domain.Responsavel;
import br.com.escolares.domain.Usuario;
import br.com.escolares.repository.AlunoRepository;
import br.com.escolares.repository.ResponsavelRepository;
import br.com.escolares.repository.TurmaRepository;
import br.com.escolares.response.AlunoResponse;
import br.com.escolares.service.UsuarioServiceImpl;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Controller
@RequestMapping("alunos")
public class AlunoController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private ResponsavelRepository responsavelRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(       Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));   
	}

	@RequestMapping("listar")
	public String listar(Model model) {
		// lista ordenada por nome do aluno
		List<Aluno> alunos = alunoRepository.findAll(new Sort("nome"));
		model.addAttribute("alunos", alunos);
		return "/alunos/listar";
	}

	@RequestMapping("listarPorResponsavel")
	public String listarPorFilho(Model model) {
		Usuario loggedUser = usuarioServiceImpl.getLoggedUser();
		Responsavel responsavel  = responsavelRepository.findByUsuario(loggedUser.getId());
		List<Aluno> alunos = alunoRepository.findByResponsavel(responsavel.getId());
		model.addAttribute("alunos", alunos);
		return "/alunos/listar";
	}

	@GetMapping("cadastrar")
	public String cadastrar(Aluno aluno) {
		return "alunos/cadastrar";
	}

	@PostMapping("gravar")
	public String gravar(@Valid Aluno aluno, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "alunos/cadastrar";
		}
		aluno.setResponsavel(responsavelRepository.findOne(aluno.getResponsavel().getId()));
		if (aluno.getTurma() != null) {
			aluno.setTurma(turmaRepository.findOne(aluno.getTurma().getId()));
		}
		alunoRepository.save(aluno);
		redirectAttributes.addFlashAttribute("sucesso", "Aluno: " + aluno.getNome() + " cadastrado com sucesso!");
		return "redirect:/";
	}
	
	@GetMapping("editar/{id}")
	public String editar(@PathVariable("id")Integer id, Model model){
		Aluno aluno = alunoRepository.findOne(id);
		model.addAttribute("aluno", aluno);
		return "alunos/cadastrar";
	}
	
	@PostMapping("remover")
	public String remover(Integer id, Model model, RedirectAttributes redirectAttributes){
		Aluno aluno = alunoRepository.findOne(id);
		alunoRepository.delete(aluno);
		redirectAttributes.addFlashAttribute("sucesso", "Aluno: " + aluno.getNome() + " removido com sucesso!");
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
	
	@RequestMapping(value="buscarPorRg", produces = { "application/json"})
	@ResponseBody
	public ResponseEntity<?> buscarPorRg(String rg){
		AjaxResponseBody<AlunoResponse> result = new AjaxResponseBody<AlunoResponse>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Aluno aluno = alunoRepository.findByRg(rg);
		if (aluno != null) {
			AlunoResponse alunoResponse = new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getRg(), dateFormat.format(aluno.getDataNascimento()), 
					aluno.getResponsavel().getNome(), aluno.getTelefone(), "Aluno Encontrado");
				result.setResult(alunoResponse);
			 return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(new AlunoResponse("Aluno não encontrado"));
	}

}
