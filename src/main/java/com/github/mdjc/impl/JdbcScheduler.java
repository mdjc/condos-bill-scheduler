package com.github.mdjc.impl;

import static com.github.mdjc.commons.db.DBUtils.parametersMap;

import java.time.LocalDate;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import com.github.mdjc.domain.Scheduler;

public class JdbcScheduler implements Scheduler {
	private static final String PENDING_PAYMENT_STATUS = "PENDING";
	private static final int MAX_DAYS_MONTH = 31;

	private final NamedParameterJdbcTemplate template;

	public JdbcScheduler(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	@Transactional
	public void schedule(LocalDate date) {
		int dayFrom = date.getDayOfMonth();
		int dayTo = date.getDayOfMonth() == date.getMonth().length(date.isLeapYear()) ? MAX_DAYS_MONTH : dayFrom;

		SqlParameterSource parameters = parametersMap("bill_description", SCHEDULE_BILL_DESCRIPTION, "due_date",
				date.plusDays(DAYS_BILL_DUE), "payment_status", PENDING_PAYMENT_STATUS, "last_update_on", date,
				"day_from", dayFrom, "day_to", dayTo);

		template.update(
				"insert into bills (apartment, description, due_amount, due_date, payment_status, last_update_on)"
						+ " select a.id apartment, :bill_description, a.monthly_share, :due_date, :payment_status, :last_update_on"
						+ " from condos c join apartments a on a.condo = c.id"
						+ " where billing_day_of_month >= :day_from and billing_day_of_month <= :day_to"
						+ " and a.resident is not null and not exists "
						+ " (select * from bills where apartment = a.id and description = :bill_description and last_update_on = :last_update_on)",
				parameters);
	}
}
