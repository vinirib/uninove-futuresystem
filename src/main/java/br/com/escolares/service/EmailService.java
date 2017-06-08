package br.com.escolares.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import br.com.escolares.domain.Falta;
import br.com.escolares.domain.Notificacao;
import br.com.escolares.domain.Professor;
import br.com.escolares.email.EmailHtmlSender;
import br.com.escolares.email.EmailStatus;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Service
public class EmailService {

	private static final String PAGINA_AVISO_DE_FALTA = "email/avisoDeFalta";

	private static final String mailsBlackList = ".*@futuresystem\\.com\\.br";
	
	@Autowired
	private EmailHtmlSender emailHtmlSender;
	
	public EmailStatus sendEmail(Notificacao notificacao, Falta falta){
		if (notificacao.getResponsavel().getEmailContato().matches(mailsBlackList)) {
			return new EmailStatus(null, null, null).error("Mail is on Black list");
		}
		Context context = new Context();
		context.setVariable("title", "Aviso de falta do aluno "+notificacao.getAluno().getNome());
		context.setVariable("nomeResponsavel", notificacao.getResponsavel().getNome());
		context.setVariable("nomeAluno", notificacao.getAluno().getNome());
		context.setVariable("nomeProfessor", falta.getAgenda().getProfessor().getNome());
		context.setVariable("materia", notificacao.getMateria());
		context.setVariable("data", falta.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		context.setVariable("horaInicio", falta.getHoraInicio());
		context.setVariable("horaFim", falta.getHoraFim());
		String tituloFalta = "Notificação de ausência do aluno: "+notificacao.getAluno().getNome(); 
		return emailHtmlSender.send(notificacao.getResponsavel().getEmailContato(), tituloFalta, PAGINA_AVISO_DE_FALTA, context);
	}

	/**
	 * @param notificacao
	 * @param professor 
	 * @return
	 */
	public EmailStatus sendEmailTest(Notificacao notificacao, Professor professor) {
		if (notificacao.getResponsavel().getEmailContato().matches(mailsBlackList)) {
			return new EmailStatus(null, null, null).error("Mail is on Black list");
		}
		Context context = new Context();
		context.setVariable("title", "Aviso de falta do aluno "+notificacao.getAluno().getNome());
		context.setVariable("nomeResponsavel", notificacao.getResponsavel().getNome());
		context.setVariable("nomeAluno", notificacao.getAluno().getNome());
		context.setVariable("nomeProfessor", professor.getNome());
		context.setVariable("materia", notificacao.getMateria());
		context.setVariable("data", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		context.setVariable("horaInicio", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
		context.setVariable("horaFim", LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm")));
		String tituloFalta = "Notificação de ausência do aluno: "+notificacao.getAluno().getNome(); 
		return emailHtmlSender.send(notificacao.getResponsavel().getEmailContato(), tituloFalta, PAGINA_AVISO_DE_FALTA, context);
	}

}
