package com.github.mdjc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.github.mdjc.domain.Scheduler;
import com.github.mdjc.impl.JdbcScheduler;

@Configuration
public class BeansConfig {	
	@Bean
	public Scheduler scheduler(NamedParameterJdbcTemplate template) {
		return new JdbcScheduler(template);
	}
}
