package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;

/**
 * <p>
 * 	Specification for Offer contract, it contains one method that calculates the offer price.
 * </p>
 * @author arhariss
 * 
 */
public interface IOffer {
	public BigDecimal calculatePrice(Product product, Quantity quantity);
}
