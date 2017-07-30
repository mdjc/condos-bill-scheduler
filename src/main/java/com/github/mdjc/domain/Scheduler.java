package com.github.mdjc.domain;

import java.time.LocalDate;

public interface Scheduler {
	static final int DAYS_BILL_DUE = 30;
	static final String SCHEDULE_BILL_DESCRIPTION = "CUOTA MENSUAL AUTOGENERADA"; 
	
	void schedule(LocalDate date);
}
