package com.cb.sat.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Service;

import com.cb.sat.core.audit.UserSesion;
import com.cb.sat.core.exception.InternalException;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("serviceBase")
public class ServiceBase {

	@Autowired
	protected UserSesion userSesion;

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
