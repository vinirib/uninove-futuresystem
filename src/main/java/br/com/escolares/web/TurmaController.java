package br.com.escolares.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.escolares.domain.AjaxResponseBody;
import br.com.escolares.domain.Aluno;
import br.com.escolares.domain.Turma;
import br.com.escolares.domain.Turno;
import br.com.escolares.repository.AlunoRepository;
import br.com.escolares.repository.TurmaRepository;
import br.com.escolares.response.AlunoResponse;
import br.com.escolares.response.TurmaResponse;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Controller
@RequestMapping("turmas")
public class TurmaController {

	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@RequestMapping("listar")
	public String listar(Model model){
		List<Turma> turmas = turmaRepository.findAll();
		model.addAttribute("turmas", turmas);
		return "turmas/listar";
		
	}
	
	@GetMapping("cadastrar")
	public String cadastrar(Turma turma, Model model){
		model.addAttribute("turnos", Turno.values());
		return "turmas/cadastrar";
	}
	
    @PostMapping("gravar")
    public String gravar(@Valid Turma turma, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
   	    if (bindingResult.hasErrors()) {
   	    	model.addAttribute("turnos", Turno.values());
            return "turmas/cadastrar";
        }
   	 turmaRepository.save(turma);
    	redirectAttributes.addFlashAttribute("sucesso", "Turma: "+turma.getSala().getNome()+" cadastrado com sucesso!");
    	return "redirect:/";
    }
    
	@GetMapping("editar/{id}")
	public String editar(@PathVariable("id")Integer id, Model model){
		Turma turma = turmaRepository.findOne(id);
		model.addAttribute("turnos", Turno.values());
		model.addAttribute("turma", turma);
		return "turmas/cadastrar";
	}
	
	@PostMapping("remover")
	public String remover(Integer id, Model model, RedirectAttributes redirectAttributes){
		Turma turma = turmaRepository.findOne(id);
		turmaRepository.delete(turma);
		redirectAttributes.addFlashAttribute("sucesso", "Responsável: " + turma.getSala().getNome() + " removido com sucesso!");
		return "redirect:/";
		
	}
	
	@GetMapping("associarAluno")
	public String associarAluno(Turma turma){
		return "turmas/associarAluno";
	}
	
	@PostMapping("associarAlunoNaTurma")
	public String associarAlunoNaTurma(Integer turmaId, Integer alunoId, RedirectAttributes redirectAttributes){
		Aluno aluno = alunoRepository.findOne(alunoId);
		Turma turma = turmaRepository.findOne(turmaId);
		aluno.setTurma(turma);
		alunoRepository.save(aluno);
		redirectAttributes.addFlashAttribute("sucesso", "Aluno: " + aluno.getNome() + 
				" associado na turma: " +turma.getSala().getNome());
		return "redirect:/";
	}
	
	@RequestMapping(value="buscarPorSala", produces = { "application/json"})
	@ResponseBody
	public ResponseEntity<?> buscarPorSala(String numeroSala){
		AjaxResponseBody<TurmaResponse> result = new AjaxResponseBody<TurmaResponse>();
		Turma turma = turmaRepository.findBySalaNumero(numeroSala);
		if (turma != null) {
			TurmaResponse turmaResponse = new TurmaResponse(turma.getId(), turma.getSala().getNome(), Integer.toString(turma.getAlunos().size()),
					turma.getSala().getTurno().toString(), turma.getSala().getNumero(), "Turma encontrada");
			result.setResult(turmaResponse);
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(new AlunoResponse("Aluno não encontrado"));
	}
}
