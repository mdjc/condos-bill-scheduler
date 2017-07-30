package com.github.mdjc;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.github.mdjc.domain.Scheduler;

@SpringBootApplication
public class CondosBillSchedulerApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(CondosBillSchedulerApplication.class);

	public static void main(String[] args) {
		LocalDate date = null;
		
		try {			
			date = args.length < 1 ? LocalDate.now() : LocalDate.parse(args[0]);
			LOGGER.info("date: {}", date);
			ConfigurableApplicationContext context = SpringApplication.run(CondosBillSchedulerApplication.class, args);
			Scheduler scheduler = context.getBean(Scheduler.class);
			scheduler.schedule(date);
		} catch(DateTimeParseException e) {
			LOGGER.error("Incorrect format for date {}, should be: YYYY-MM-DD", args[0]);
			throw e;
		} catch(Exception e) {
			LOGGER.error("Exception schedulingBills: {}", e.toString(), e);
			throw e;
		}
	}
}
