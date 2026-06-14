package com.cb.sat.app.integration;

import java.util.Map;

public interface ComponenteNotificacionService {
	/**
	 * notificacion al momento de crear la cuenta
	 * 
	 * @param map
	 */
	void sendEmailRegistroUsuario(Map<String, Object> map);

	/**
	 * link de crear contraseña para una cuenta administrador
	 * 
	 * @param map
	 */
	void sendEmailRegistroAdministrador(Map<String, Object> map);

	/**
	 * link de recuperación de clave
	 * 
	 * @param map
	 */
	void sendForgotPassword(Map<String, Object> map);

	/**
	 * notificacion de cambio de password
	 * 
	 * @param map
	 */
	void sendChangePassword(Map<String, Object> map);

}
