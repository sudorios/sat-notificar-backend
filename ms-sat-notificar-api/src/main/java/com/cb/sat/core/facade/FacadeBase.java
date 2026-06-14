package com.cb.sat.core.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Service;

import com.cb.sat.core.audit.UserSesion;
import com.cb.sat.core.exception.InternalException;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service("facadeBase")
@Slf4j
public class FacadeBase {

	@Autowired
	protected UserSesion userSesion;

	@Value("${message.global.update.password}")
	protected String messageUpdatePassword;

	@Value("${message.global.reset.password}")
	protected String messageResetPassword;

	@Value("${message.global.save}")
	protected String messageSave;

	@Value("${message.global.update}")
	protected String messageUpdate;

	@Value("${message.global.delete}")
	protected String messageDelete;

	@Value("${path.base.front}")
	protected String pathBaseFront;

	protected void launchException(Exception e) {
		log.error(e.getMessage());
		if (e instanceof EntityNotFoundException) {
			throw new InternalException(e.getMessage());
		} else if (e instanceof MappingException) {
			throw new InternalException(e.getMessage());
		} else {
			throw new InternalException(e.getMessage());
		}
	}
}
