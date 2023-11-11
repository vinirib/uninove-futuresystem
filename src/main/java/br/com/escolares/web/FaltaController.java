package br.com.escolares.web;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.escolares.domain.Agenda;
import br.com.escolares.domain.Falta;
import br.com.escolares.domain.Notificacao;
import br.com.escolares.domain.Professor;
import br.com.escolares.domain.Responsavel;
import br.com.escolares.domain.Usuario;
import br.com.escolares.email.EmailStatus;
import br.com.escolares.repository.AgendaRepository;
import br.com.escolares.repository.AlunoRepository;
import br.com.escolares.repository.FaltaRepository;
import br.com.escolares.repository.NotificacaoRepository;
import br.com.escolares.repository.ProfessorRepository;
import br.com.escolares.repository.ResponsavelRepository;
import br.com.escolares.service.EmailService;
import br.com.escolares.service.UsuarioServiceImpl;
import br.com.escolares.wrappers.FaltaWrapper;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Controller
@RequestMapping("faltas")
public class FaltaController {
	
	private Integer contadorTesteEmail = 0;

	@Autowired
	private FaltaRepository faltaRepository;
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private ResponsavelRepository responsavelRepository;
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	@Autowired
	private EmailService emailService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("listar")
	public String listar(Model model){
		List<Falta> faltas = faltaRepository.findAll();
		model.addAttribute("faltas", faltas);
		return "faltas/listar";
	}

	@GetMapping("inserirFaltaAluno")
	public String inserirFaltaAluno(Model model, FaltaWrapper faltaWrapper){
		Usuario loggedUser = usuarioServiceImpl.getLoggedUser();
		Professor professor = professorRepository.findByUsuario(loggedUser.getId());
		List<Agenda> agendaDoProfessor = agendaRepository.findByProfessorId(professor.getId());
		model.addAttribute("agendaDoProfessor", agendaDoProfessor);
		return "faltas/inserirFalta";
	}
	
	@PostMapping("salvarFaltas")
	public String salvarFaltas(@ModelAttribute("faltaWrapper") FaltaWrapper faltaWrapper){
		faltaWrapper.getFaltas().removeIf(falta -> falta.getAluno().getId() == 00000000000);
		if (faltaWrapper.getFaltas().isEmpty()) {
			return "redirect:/";
		}
		faltaWrapper.getFaltas().forEach(falta -> {
			Falta faltaPersist = new Falta();
			faltaPersist.setAgenda(agendaRepository.findOne(falta.getAgenda().getId()));
			faltaPersist.setAluno(alunoRepository.findOne(falta.getAluno().getId()));
			faltaPersist.setData(falta.getData());
			faltaPersist.setHoraInicio(falta.getHoraInicio());
			faltaPersist.setHoraFim(falta.getHoraFim());
			Notificacao notificacao = notificacaoRepository.findByResponsavelAlunoMateria(faltaPersist.getAluno().getResponsavel().getId(),
					faltaPersist.getAluno().getId(), faltaPersist.getAgenda().getProfessor().getMateria());
			if (notificacao != null) {
				EmailStatus email = emailService.sendEmail(notificacao, faltaPersist);
				if (email.getStatus().equals(EmailStatus.ERROR)) {
					faltaPersist.setNotificado(false);
					logger.error(email.getErrorMessage());
				} else {
					faltaPersist.setNotificado(true);
				}
			}
			faltaRepository.save(faltaPersist);
		});
		return "redirect:/faltas/listar";
	}
	
	@PostMapping("remover")
	public String remover(Integer id, RedirectAttributes redirectAttributes){
		Falta falta = faltaRepository.findOne(id);
		faltaRepository.delete(falta);
		redirectAttributes.addFlashAttribute("sucesso", "Falta do aluno: " + falta.getAluno().getNome() + " removida com sucesso!");
		return "redirect:/";
	}
	
	@GetMapping("faltasDosFilhos")
	public String faltasDosFilhos(Model model){
		Usuario loggedUser = usuarioServiceImpl.getLoggedUser();
		Responsavel responsavel = responsavelRepository.findByUsuario(loggedUser.getId());
		model.addAttribute("filhos", responsavel.getFilhos());
		return "faltas/faltasDosFilhos";
	}
	
	@GetMapping("emailTest")
	public ResponseEntity<?> testeDeEmail(String email){
		if (contadorTesteEmail < 2) {
			List<Notificacao> notificacoes = notificacaoRepository.findAll();
			List<Professor> professores = professorRepository.findAll();
			Collections.shuffle(notificacoes);
			Collections.shuffle(professores);
			Notificacao notificacao = notificacoes.get(0);
			notificacao.getResponsavel().setEmailContato(email);
			emailService.sendEmailTest(notificacao, professores.get(0));
			contadorTesteEmail++;
			return ResponseEntity.ok("email de teste enviado para: "+email+", número de testes: "+contadorTesteEmail);
		}
		return ResponseEntity.ok("você já enviou testes demais, utilize o sistema se quiser testar mais envio de emails. ");
		
	}
}
