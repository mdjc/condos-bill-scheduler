package com.github.mdjc.domain;

import java.time.LocalDate;

public class Bill {
	private final long apartment;
	private final String description;
	private final LocalDate dueDate;
	private final double dueAmount;
	private final String paymentStatus;
	private final LocalDate lastUpdateOn;

	public Bill(long apartment, String description, LocalDate dueDate, double dueAmount, String paymentStatus,
			LocalDate lastUpdateOn) {
		this.apartment = apartment;
		this.description = description;
		this.dueDate = dueDate;
		this.dueAmount = dueAmount;
		this.paymentStatus = paymentStatus;
		this.lastUpdateOn = lastUpdateOn;
	}

	public long getApartment() {
		return apartment;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public double getDueAmount() {
		return dueAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public LocalDate getLastUpdateOn() {
		return lastUpdateOn;
	}
}
