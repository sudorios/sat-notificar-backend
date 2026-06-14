package com.cb.sat.app.integration;

import java.util.Set;

public interface HerocaEmailService {
	
	void sendHTMLCopia(Set<String> fillCopia, String to, String subject, String mergeTemplate);

	void sendHTMLMultiple(Set<String> fillCopia, Set<String> to, String subject, String mergeTemplate);

	void sendHTML(String to, String subject, String mergeTemplate);
	
}