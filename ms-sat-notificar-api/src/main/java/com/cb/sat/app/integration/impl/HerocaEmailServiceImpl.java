package com.cb.sat.app.integration.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.cb.sat.app.integration.HerocaEmailService;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.dto.util.GenericUtil;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HerocaEmailServiceImpl extends ServiceBase implements HerocaEmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${email.notificacion.heroca}")
	private String fromEmailAddress;

	@Override
	public void sendHTML(String to, String subject, String htmlContent) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(fromEmailAddress);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);
			mailSender.send(message);
			log.info("Correo enviado exitosamente a: {}", to);
		} catch (Exception e) {
			log.error("Error enviando correo SMTP a {}: {}", to, e.getMessage());
		}
	}

	@Override
	public void sendHTMLCopia(Set<String> fillCopia, String to, String subject, String htmlContent) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(fromEmailAddress);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);

			if (GenericUtil.isNotNull(fillCopia) && !fillCopia.isEmpty()) {
				helper.setCc(fillCopia.toArray(new String[0]));
			}

			mailSender.send(message);
			log.info("Correo con copia enviado a: {}", to);
		} catch (Exception e) {
			log.error("Error en sendHTMLCopia: {}", e.getMessage());
		}
	}

	@Override
	public void sendHTMLMultiple(Set<String> fillCopia, Set<String> to, String subject, String htmlContent) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(fromEmailAddress);
			helper.setTo(to.toArray(new String[0]));
			helper.setSubject(subject);
			helper.setText(htmlContent, true);
			if (GenericUtil.isNotNull(fillCopia) && !fillCopia.isEmpty()) {
				helper.setCc(fillCopia.toArray(new String[0]));
			}
			mailSender.send(message);
			log.info("Correo múltiple enviado a {} destinatarios", to.size());
		} catch (Exception e) {
			log.error("Error en sendHTMLMultiple: {}", e.getMessage());
		}
	}
}