package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Unit {
	PIECE(BigDecimal.ZERO),
	GRAM(BigDecimal.ONE),
	KILO(BigDecimal.ONE.divide(BigDecimal.valueOf(1000),4,RoundingMode.HALF_UP)),
	POUND(BigDecimal.ONE.divide(BigDecimal.valueOf(453.6),4,RoundingMode.HALF_UP)),
	OUNCE(BigDecimal.ONE.divide(BigDecimal.valueOf(28.35),4,RoundingMode.HALF_UP));
	
	
	private BigDecimal factor;

	Unit(BigDecimal factor) {
		this.factor = factor;
	}
	
	public BigDecimal convertTo(Unit to, BigDecimal value) {
		if(this == Unit.PIECE) {
			throw new IllegalArgumentException("Not supported conversion");
		}
		return BigDecimal.ONE.divide(this.factor).multiply(to.factor);
	}
}
