package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;

public interface IOffer {
	public BigDecimal calculatePrice(Product product, Quantity quantity);
}
