package com.cb.sat.config;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.chandre.velocity2.spring.Velocity2AutoConfiguration;
import de.chandre.velocity2.spring.Velocity2PropertiesOverrideHook;

@Configuration
@AutoConfigureBefore(Velocity2AutoConfiguration.class)
public class VelocityConfig {

	@Bean
	public Velocity2PropertiesOverrideHook velocity2PropertiesOverrideHook() {
		return new Velocity2PropertiesOverrideHook() {

			@Override
			public Properties override(Properties velocityProperties) {
				velocityProperties.put("resource.loader", "class");
				velocityProperties.put("class.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
				return velocityProperties;
			}
		};
	}

	@Bean
	public VelocityEngine velocityEngine() {
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties properties = new Properties();
		properties.put("resource.loader", "class");
		properties.put("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngine.init(properties);
		return velocityEngine;
	}

}