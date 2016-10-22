package pl.ug.edu.polisa.domain.core;

import java.math.BigDecimal;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

public class BigMoneyHelper {

	/**
	 * Metoda zwraca obiekt reprezentujący kwotę z walutą PLN
	 * 
	 * @param value
	 *            wartość typu double
	 * @return
	 */
	public static BigMoney of(double value) {
		return BigMoney.of(CurrencyUnit.getInstance("PLN"), BigDecimal.valueOf(value));
	}

	/**
	 * Metoda zwraca obiekt reprezentujący kwotę z walutą PLN
	 * 
	 * @param value
	 *            wartość typu double
	 * @return
	 */
	public static BigMoney of(String value) {
		return BigMoney.of(CurrencyUnit.getInstance("PLN"), new BigDecimal(value));
	}

	/**
	 * Metoda zwraca obiekt reprezentujący kwotę z walutą PLN
	 * 
	 * @param value
	 *            wartość typu {@link BigDecimal}
	 * @return
	 */
	public static BigMoney of(BigDecimal value) {
		return BigMoney.of(CurrencyUnit.getInstance("PLN"), value);
	}
}
