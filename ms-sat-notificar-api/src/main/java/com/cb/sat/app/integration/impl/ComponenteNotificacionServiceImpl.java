package com.cb.sat.app.integration.impl;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cb.sat.app.integration.ComponenteNotificacionService;
import com.cb.sat.app.integration.HerocaEmailService;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.dto.model.Constantes;

@Component
public class ComponenteNotificacionServiceImpl extends ServiceBase implements ComponenteNotificacionService {

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private HerocaEmailService herocaEmailService;

	@Value("${email.template.registro.usuario}")
	private String templateRegistroUsuario;

	@Value("${email.template.forgot.password}")
	private String templateForgotPassword;

	@Value("${email.template.registro.administrador}")
	private String templateRegistrarAdministrador;

	@Value("${email.template.change.password}")
	private String templateChangePassword;

	private String mergeTemplate(String templa, Map<String, Object> parameters) {
		try {
			Template template = velocityEngine.getTemplate(templa);
			VelocityContext context = new VelocityContext();
			for (Map.Entry<String, Object> tt : parameters.entrySet()) {
				context.put(tt.getKey(), tt.getValue());
			}
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (Exception e) {
			launchException(e);
		}
		return null;
	}

	@Override
	public void sendEmailRegistroUsuario(Map<String, Object> map) {
		try {
			String subject = (String) map.get(Constantes.Email.SUBJECT_EMAIL);
			String to = (String) map.get(Constantes.Email.TO_EMAIL);
			herocaEmailService.sendHTML(to, subject, mergeTemplate(templateRegistroUsuario, map));
		} catch (Exception e) {
			launchException(e);
		}
	}

	@Override
	public void sendForgotPassword(Map<String, Object> map) {
		try {
			String subject = (String) map.get(Constantes.Email.SUBJECT_EMAIL);
			String to = (String) map.get(Constantes.Email.TO_EMAIL);
			herocaEmailService.sendHTML(to, subject, mergeTemplate(templateForgotPassword, map));
		} catch (Exception e) {
			launchException(e);
		}
	}

	@Override
	public void sendChangePassword(Map<String, Object> map) {
		try {
			String subject = (String) map.get(Constantes.Email.SUBJECT_EMAIL);
			String to = (String) map.get(Constantes.Email.TO_EMAIL);
			herocaEmailService.sendHTML(to, subject, mergeTemplate(templateChangePassword, map));
		} catch (Exception e) {
			launchException(e);
		}
	}

	@Override
	public void sendEmailRegistroAdministrador(Map<String, Object> map) {
		try {
			String subject = (String) map.get(Constantes.Email.SUBJECT_EMAIL);
			String to = (String) map.get(Constantes.Email.TO_EMAIL);
			herocaEmailService.sendHTML(to, subject, mergeTemplate(templateRegistrarAdministrador, map));
		} catch (Exception e) {
			launchException(e);
		}

	}

}
