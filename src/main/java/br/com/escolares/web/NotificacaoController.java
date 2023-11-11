package br.com.escolares.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.escolares.domain.Materia;
import br.com.escolares.domain.Notificacao;
import br.com.escolares.domain.Responsavel;
import br.com.escolares.domain.Usuario;
import br.com.escolares.repository.AlunoRepository;
import br.com.escolares.repository.NotificacaoRepository;
import br.com.escolares.repository.ResponsavelRepository;
import br.com.escolares.service.UsuarioServiceImpl;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Controller
@RequestMapping("notificacoes")
public class NotificacaoController {
	
	@Autowired
	private NotificacaoRepository notificacaoRepository;

	@Autowired
	private ResponsavelRepository responsavelRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@GetMapping("listar")
	public String listar(Model model){
		Usuario loggedUser = usuarioServiceImpl.getLoggedUser();
		Responsavel responsavel  = responsavelRepository.findByUsuario(loggedUser.getId());
		List<Notificacao> notificacoes = notificacaoRepository.findByResponsavel(responsavel.getId());
		model.addAttribute("notificacoes", notificacoes);
		return "notificacoes/listar";
	}
	
	@GetMapping("nova")
	public String novaNotificação(Notificacao notificacao, Integer id, Model model){
		Usuario loggedUser = usuarioServiceImpl.getLoggedUser();
		Responsavel responsavel  = responsavelRepository.findByUsuario(loggedUser.getId());
		model.addAttribute("responsavel", responsavel);
		model.addAttribute("materias", Materia.values());
		return "notificacoes/nova";
	}
	
	@PostMapping("gravar")
	public String gravar(@Valid Notificacao notificacao, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("responsavel", notificacao.getResponsavel());
			model.addAttribute("materias", Materia.values());
			return "notificacoes/nova";
		}
		notificacao.setAluno(alunoRepository.findOne(notificacao.getAluno().getId()));
		notificacao.setResponsavel(responsavelRepository.findOne(notificacao.getResponsavel().getId()));
		try {
			notificacaoRepository.save(notificacao);
		} catch (DataIntegrityViolationException e) {
			redirectAttributes.addFlashAttribute("alerta", "Notificação do filho: " + notificacao.getAluno().getNome() 
					+ " para a matéria de "+ notificacao.getMateria()+" já está cadastrada no sistema");
			return "redirect:/";
		}
		redirectAttributes.addFlashAttribute("sucesso", "Notificação do filho: " + notificacao.getAluno().getNome() + " cadastrada com sucesso!");
		return "redirect:/";
	}
	
	@PostMapping("remover")
	public String remover(Integer id, Model model, RedirectAttributes redirectAttributes){
		Notificacao notificacao = notificacaoRepository.findOne(id);
		notificacaoRepository.delete(notificacao);
		redirectAttributes.addFlashAttribute("sucesso", "Notificação do filho: " + notificacao.getAluno().getNome() + " removida com sucesso!");
		return "redirect:/";
		
	}

}
