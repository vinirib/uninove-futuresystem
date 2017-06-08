package br.com.escolares;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import br.com.escolares.domain.Agenda;
import br.com.escolares.domain.Aluno;
import br.com.escolares.domain.Endereco;
import br.com.escolares.domain.Falta;
import br.com.escolares.domain.Materia;
import br.com.escolares.domain.Notificacao;
import br.com.escolares.domain.Professor;
import br.com.escolares.domain.Responsavel;
import br.com.escolares.domain.Role;
import br.com.escolares.domain.RoleType;
import br.com.escolares.domain.Sala;
import br.com.escolares.domain.Turma;
import br.com.escolares.domain.Turno;
import br.com.escolares.domain.Usuario;
import br.com.escolares.repository.AgendaRepository;
import br.com.escolares.repository.AlunoRepository;
import br.com.escolares.repository.FaltaRepository;
import br.com.escolares.repository.NotificacaoRepository;
import br.com.escolares.repository.ProfessorRepository;
import br.com.escolares.repository.ResponsavelRepository;
import br.com.escolares.repository.RoleRepository;
import br.com.escolares.repository.SalaRepository;
import br.com.escolares.repository.TurmaRepository;
import br.com.escolares.service.UsuarioServiceImpl;
import br.com.escolares.util.GeradorCpfCnpj;
import br.com.escolares.util.GeradorDeDataDeNascimento;
import br.com.escolares.util.GeradorDeEndereco;
import br.com.escolares.util.GeradorDeNomes;
import br.com.escolares.util.GeradorRg;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Component
public class ConstroiDadosDoSistema {

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private ResponsavelRepository responsavelRepository;

	@Autowired
	private AgendaRepository agendaRepository;

	@Autowired
	private SalaRepository salaRepository;

	@Autowired
	private NotificacaoRepository notificacaoRepository;

	@Autowired
	@Qualifier("transactionManager")
	protected PlatformTransactionManager txManager;
	
    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

	@Autowired
	private FaltaRepository faltaRepository;

	private GeradorCpfCnpj geraCpf = new GeradorCpfCnpj();

	private GeradorRg geraRg = new GeradorRg();
	
	@PostConstruct
	public void init() {
		TransactionTemplate tmpl = new TransactionTemplate(txManager);
		tmpl.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				constroiRoles();
				constroiUsuarioAdministrador();
				

				List<String> nomesDosProfessores = GeradorDeNomes.constroiListaDeNomesComSobrenomes(Materia.values().length);
				Materia[] materia = Materia.values();
				for (int i = 0; i < Materia.values().length; i++) {
					constroiProfessor(materia[i], nomesDosProfessores.get(i));
				}
				
				constroiSala("1º série A", Turno.MANHA, "22");
				constroiSala("2º série B", Turno.TARDE, "46F");
				constroiSala("3º série C", Turno.TARDE, "47");
				constroiSala("4º série A", Turno.TARDE, "48");
				constroiSala("5º série A", Turno.TARDE, "50");
				constroiTurmas();
				constroiResponsaveisComAlunos(25);
				constroiAgendaPassadaEFutura();
				constroiNotificacao();
				constroiFalta();
			}

		});
	}
	
	private void constroiRoles() {
		for (RoleType role : RoleType.values()) {
			Role roleToPersist = new Role();
			roleToPersist.setName(role);
			roleRepository.save(roleToPersist);
		}
	}

	private void constroiUsuarioAdministrador() {
		Usuario adminUser = new Usuario();
		adminUser.setName("Administrador");
		adminUser.setUsername("admin@futuresystem.com.br");
		adminUser.setPasswordForm("123");
		adminUser.setPasswordConfirmForm("123");
		adminUser.setAvatarPhoto("user.jpg");
		adminUser.setStatus(Boolean.TRUE);
		adminUser.setDataCadastro(LocalDateTime.now());
		adminUser.setRole(roleRepository.findByName(RoleType.ROLE_ADMIN));
		usuarioService.save(adminUser);
	}

	private void constroiFalta() {
		List<Agenda> agendasAnteriores = agendaRepository.findWhereDataBetween(LocalDate.now().minusDays(5), LocalDate.now());
		for (int i = 0; i < 10; i++) {
			List<Aluno> alunos = agendasAnteriores.get(i).getTurma().getAlunos();
			Collections.shuffle(alunos);
			Falta falta = new Falta();
			falta.setAgenda(agendasAnteriores.get(i));
			falta.setAluno(alunos.get(0));
			falta.setData(agendasAnteriores.get(i).getData());
			falta.setHoraInicio(agendasAnteriores.get(i).getHoraInicio());
			falta.setHoraFim(agendasAnteriores.get(i).getHoraFim());
			faltaRepository.save(falta);
		}
	}

	private void constroiNotificacao() {
		List<Responsavel> responsaveis = responsavelRepository.findAll();
			responsaveis.forEach(responsavel -> {
				responsavel.getFilhos().forEach(filho -> {
					Arrays.asList(Materia.values()).forEach(materia -> {
						Notificacao notificacao = new Notificacao();
						notificacao.setResponsavel(responsavel);
						notificacao.setAluno(filho);
						notificacao.setMateria(materia);
						notificacaoRepository.save(notificacao);
					});
				});
			});
	}

	private void constroiSala(String nome, Turno turno, String numero) {
		Sala sala = new Sala();
		sala.setNome(nome);
		sala.setNumero(numero);
		sala.setTurno(turno);
		salaRepository.save(sala);
	}

	private void constroiAgendaPassadaEFutura() {
		List<Professor> professores = professorRepository.findAll();
		List<Turma> turmas = turmaRepository.findAll();
		for (Turma turma : turmas) {
			constroiAgendaPassada(turma, professores);
			constroiAgendaFutura(turma, professores);
		}
	}

	/**
	 * @param turma
	 * @param professores
	 */
	private void constroiAgendaPassada(Turma turma, List<Professor> professores) {
		LocalDateTime hoje = LocalDate.now().atTime(7, 0).minusDays(5);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Collections.shuffle(professores);
				Agenda agenda = new Agenda();
				agenda.setProfessor(professores.get(0));
				agenda.setTurma(turma);
				agenda.setData(hoje.toLocalDate());
				agenda.setHoraInicio(hoje.plusHours(j).toLocalTime());
				agenda.setHoraFim(hoje.plusHours(j+1).toLocalTime());
				agendaRepository.save(agenda);
			}
			hoje = hoje.plusDays(1);
		}
		
	}
	/**
	 * @param turma
	 * @param professores
	 */
	private void constroiAgendaFutura(Turma turma, List<Professor> professores) {
		LocalDateTime hoje = LocalDate.now().atTime(7, 0);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Collections.shuffle(professores);
				Agenda agenda = new Agenda();
				agenda.setProfessor(professores.get(0));
				agenda.setTurma(turma);
				agenda.setData(hoje.toLocalDate());
				agenda.setHoraInicio(hoje.plusHours(j).toLocalTime());
				agenda.setHoraFim(hoje.plusHours(j+1).toLocalTime());
				agendaRepository.save(agenda);
			}
			hoje = hoje.plusDays(1);
		}
		
	}
	
	private void constroiResponsaveisComAlunos(int quantidadeDeResponsaveis){
		for (int i = 0; i < quantidadeDeResponsaveis; i++) {
			Responsavel responsavel = new Responsavel();
			responsavel.setEmailContato("responsavel@futuresystem.com.br");
			responsavel.setEndereco(constroiEndereco());
			responsavel.setTelefone(geradorDeTelefone());
			responsavel.setCpf(geraCpf.cpf());
			responsavel.setNome(GeradorDeNomes.getNomeComSobrenomeAleatorio());
			responsavel.setFilhos(constroiFilhosParaResponsavel(responsavel.getNome().replaceFirst("^[^\\s]*\\s", "")));
			responsavel.setUsuario(constroiUsuarioParaResponsavel(responsavel.getNome()));
			responsavelRepository.save(responsavel);
			List<Aluno> filhos = responsavel.getFilhos();
			filhos.forEach(filho -> filho.setResponsavel(responsavel));
			filhos.forEach(alunoRepository::save);
		}
	}

	
	/**
	 * @param nome
	 * @return
	 */
	private List<Aluno> constroiFilhosParaResponsavel(String sobrenome) {
		List<Aluno> filhos = new ArrayList<>(2);
		List<Turma> turmas = turmaRepository.findAll();
		for (int i = 0; i < 2; i++) {
			Collections.shuffle(turmas);
			Aluno aluno = new Aluno();
			aluno.setNome(GeradorDeNomes.geraNomeComGrauDeParentesco(sobrenome));
			aluno.setTelefone(geradorDeTelefone());
			aluno.setRg(geraRg.gerarRg());
			aluno.setEndereco(constroiEndereco());
			Turma turma = turmas.get(0);
			aluno.setTurma(turma);
			aluno.setDataNascimento(GeradorDeDataDeNascimento.geraDataEntre(LocalDate.of(2000, 3, 1), LocalDate.of(2010, 3, 1)));
			filhos.add(aluno);
			alunoRepository.save(aluno);
			turma.getAlunos().add(aluno);
			turmaRepository.save(turma);
		}
		return filhos;
	}

	private Usuario constroiUsuarioParaResponsavel(String nome) {
		Usuario responsavelUser = new Usuario();
		responsavelUser.setName("Resp. "+ nome);
		responsavelUser.setUsername("responsavel"+nome.toLowerCase()
				.replaceAll("\\W", "")
				.replaceAll("\\s+", "")+"@futuresystem.com.br");
		responsavelUser.setPasswordForm("123");
		responsavelUser.setPasswordConfirmForm("123");
		responsavelUser.setPassword(bcryptPasswordEncoder.encode("123"));
		responsavelUser.setAvatarPhoto("avatar4.png");
		responsavelUser.setStatus(Boolean.TRUE);
		responsavelUser.setDataCadastro(LocalDateTime.now());
		responsavelUser.setRole(roleRepository.findByName(RoleType.ROLE_RESPONSAVEL));
		return responsavelUser;
	}

	private void constroiTurmas() {
		salaRepository.findAll().forEach(sala -> {
			Turma turma = new Turma();
			turma.setSala(sala);
			turmaRepository.save(turma);
		});
	}

	private String geradorDeTelefone() {
		Random rand = new Random();
		int num2 = rand.nextInt(99999 - 66666 + 1) + 66666;
		int num3 = rand.nextInt(9999 - 1111 + 1) + 1111;

		DecimalFormat df5 = new DecimalFormat("00000"); // 3 zeros
		DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

		String phoneNumber = "(11) "+ df5.format(num2) + "-" + df4.format(num3);

		return phoneNumber;
	}

	private void constroiProfessor(Materia materia, String nomeProfessor) {
		Professor professor = new Professor();
		Endereco endereco = constroiEndereco();
		professor.setMateria(materia);
		professor.setTelefone(geradorDeTelefone());
		professor.setNome(nomeProfessor);
		professor.setEndereco(endereco);
		professor.setUsuario(constroiUsuarioParaProfessor(nomeProfessor));
		professorRepository.save(professor);
	}

	private Usuario constroiUsuarioParaProfessor(String nome) {
		Usuario professorUser = new Usuario();
		professorUser.setName(nome);
		professorUser.setUsername("professor"+nome.toLowerCase()
			.replaceAll("\\W", "")
			.replaceAll("\\s+", "")+"@futuresystem.com.br");
		professorUser.setPasswordForm("123");
		professorUser.setPasswordConfirmForm("123");
		professorUser.setPassword(bcryptPasswordEncoder.encode(professorUser.getPasswordForm()));
		professorUser.setAvatarPhoto("avatar5.png");
		professorUser.setStatus(Boolean.TRUE);
		professorUser.setDataCadastro(LocalDateTime.now());
		professorUser.setRole(roleRepository.findByName(RoleType.ROLE_PROFESSOR));
		return professorUser;
	}

	private Endereco constroiEndereco() {
		Endereco endereco = new Endereco();
		endereco.setRua(GeradorDeEndereco.geraEnderecoSemNumero());
		endereco.setNumero(String.valueOf(GeradorDeEndereco.geraNumeroCasa()));
		endereco.setBairro("Jd Angela");
		endereco.setMunicipio("São Paulo");
		endereco.setEstado("São Paulo");
		return endereco;
	}
}
