package br.com.escolares.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.escolares.domain.Professor;
import br.com.escolares.domain.Responsavel;
import br.com.escolares.domain.Turma;
import br.com.escolares.domain.Usuario;
import br.com.escolares.repository.ProfessorRepository;
import br.com.escolares.repository.ResponsavelRepository;
import br.com.escolares.repository.TurmaRepository;
import br.com.escolares.service.UsuarioServiceImpl;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Controller
@RequestMapping("agendas")
public class AgendaController {

	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private ResponsavelRepository responsavelRepository;

	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@GetMapping("porTurma")
	public String agendaPorTurma(Model model){
		List<Turma> turmasComAgenda = turmaRepository.findAll();
		model.addAttribute("turmasComAgenda", turmasComAgenda);
		return "agendas/porTurma";
	}
	
	@GetMapping("agendaDosFilhos")
	public String agendaPorFilho(Model model){
		Usuario loggedUser = usuarioServiceImpl.getLoggedUser();
		Responsavel responsavel = responsavelRepository.findByUsuario(loggedUser.getId());
		model.addAttribute("filhos", responsavel.getFilhos());
		return "agendas/agendaDosFilhos";
	}
	
	@GetMapping("porProfessor")
	public String porProfessor(Model model){
		Usuario loggedUser = usuarioServiceImpl.getLoggedUser();
		Professor professor = professorRepository.findByUsuario(loggedUser.getId());
		model.addAttribute("professor", professor);
		return "agendas/porProfessor";
	}

	@GetMapping("agendaDosProfessores")
	public String agendaDosProfessores(Model model){
		List<Professor> professores = professorRepository.findAll();
		model.addAttribute("professores", professores);
		return "agendas/agendaDosProfessores";
	}

}
