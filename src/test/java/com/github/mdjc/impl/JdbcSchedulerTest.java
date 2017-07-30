package com.github.mdjc.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.mdjc.commons.db.DBUtils;
import com.github.mdjc.config.BeansConfig;
import com.github.mdjc.domain.Bill;
import com.github.mdjc.domain.Scheduler;
import com.github.mdjc.impl.JdbcScheduler;

@RunWith(SpringRunner.class)
@JdbcTest
@Import(BeansConfig.class)
public class JdbcSchedulerTest {
	private static final int DAYS_BILL_DUE = 30;
	private static final String SCHEDULE_BILL_DESCRIPTION = "CUOTA MENSUAL AUTOGENERADA";
	private static final String PENDING_PAYMENT_STATUS = "PENDING";

	private Scheduler scheduler;
	
	@Autowired
	private NamedParameterJdbcTemplate template;


	@Before
	public void beforeTest() {
		scheduler = new JdbcScheduler(template);
	}	

	@Test
	public void testScheduleGivenDate_shouldSchedule() {
		LocalDate date = LocalDate.of(2017, 6, 15);
		scheduler.schedule(date);

		List<Bill> expected = Arrays.asList(
				new Bill(1, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 10, PENDING_PAYMENT_STATUS, date),
				new Bill(4, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 115, PENDING_PAYMENT_STATUS, date));
		List<Bill> actual = scheduledBills(date);
		assertBillListEquals(expected, actual);
	}
	
	@Test
	public void testScheduleGivenDate_shouldBeIndempotent() {
		LocalDate date = LocalDate.of(2017, 6, 15);
		scheduler.schedule(date);
		scheduler.schedule(date);
		scheduler.schedule(date);
		List<Bill> expected = Arrays.asList(
				new Bill(1, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 10, PENDING_PAYMENT_STATUS, date),
				new Bill(4, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 115, PENDING_PAYMENT_STATUS, date));
		List<Bill> actual = scheduledBills(date);
		assertBillListEquals(expected, actual);
	}
	
	@Test
	public void testScheduleGivenDateFebruary28_shouldScheduleForBillingDayGreaterOrEqualThan28() {
		LocalDate date = LocalDate.of(2017, 2, 28);
		scheduler.schedule(date);
		List<Bill> expected = Arrays.asList(
				new Bill(8, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 150, PENDING_PAYMENT_STATUS, date),
				new Bill(9, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 300, PENDING_PAYMENT_STATUS, date),
				new Bill(10, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 800, PENDING_PAYMENT_STATUS, date),
				new Bill(11, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 90, PENDING_PAYMENT_STATUS, date));
		List<Bill> actual = scheduledBills(date);
		assertBillListEquals(expected, actual);
	}
	
	@Test
	public void testScheduleGivenDateFebruary29_shouldScheduleForBillingDayGreaterOrEqualThan29() {
		LocalDate date = LocalDate.of(2016, 2, 29);
		scheduler.schedule(date);
		List<Bill> expected = Arrays.asList(
				new Bill(9, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 300, PENDING_PAYMENT_STATUS, date),
				new Bill(10, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 800, PENDING_PAYMENT_STATUS, date),
				new Bill(11, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 90, PENDING_PAYMENT_STATUS, date));
		List<Bill> actual = scheduledBills(date);
		assertBillListEquals(expected, actual);
	}
	
	@Test
	public void testScheduleGivenDateSept30_shouldScheduleForBillingDayGreaterOrEqualThan30() {
		LocalDate date = LocalDate.of(2017, 9, 30);
		scheduler.schedule(date);
		List<Bill> expected = Arrays.asList(
				new Bill(10, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 800, PENDING_PAYMENT_STATUS, date),
				new Bill(11, SCHEDULE_BILL_DESCRIPTION, date.plusDays(DAYS_BILL_DUE), 90, PENDING_PAYMENT_STATUS, date));
		List<Bill> actual = scheduledBills(date);
		assertBillListEquals(expected, actual);
	}

	private List<Bill> scheduledBills(LocalDate date) {
		SqlParameterSource parameters = DBUtils.parametersMap("date", date);
		return template.query("select * from bills where last_update_on = :date", parameters, this::mapper);
	}

	private void assertBillListEquals(List<Bill> expected, List<Bill> actual) {
		for (int i = 0; i < expected.size(); i++) {
			assertBillEquals(expected.get(i), actual.get(i));
		}
	}

	private void assertBillEquals(Bill expected, Bill actual) {
		assertEquals(expected.getApartment(), actual.getApartment());
		assertEquals(expected.getDescription(), expected.getDescription());
		assertEquals(expected.getDueDate(), expected.getDueDate());
		assertTrue(Math.abs(expected.getDueAmount() - expected.getDueAmount()) == 0);
		assertEquals(expected.getPaymentStatus(), expected.getPaymentStatus());
		assertEquals(expected.getLastUpdateOn(), expected.getLastUpdateOn());
	}

	private Bill mapper(ResultSet rs, int rownum) throws SQLException {
		return new Bill(rs.getLong("apartment"), rs.getString("description"), rs.getDate("due_date").toLocalDate(), rs.getDouble("due_amount"),
				rs.getString("payment_status"), rs.getDate("last_update_on").toLocalDate());
	}
}
