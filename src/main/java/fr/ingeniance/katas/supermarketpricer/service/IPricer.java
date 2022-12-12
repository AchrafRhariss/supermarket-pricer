package fr.ingeniance.katas.supermarketpricer.service;

import java.math.BigDecimal;
import java.util.Map;

import fr.ingeniance.katas.supermarketpricer.models.Product;
import fr.ingeniance.katas.supermarketpricer.models.Quantity;

/**
 * <p>
 * 	Specification for pricer contract, it contains one method that prices goods depending on offers.
 * </p>
 * @author arhariss
 * 
 */
public interface IPricer {

	public BigDecimal payTheBill(Map<Product, Quantity> cartItems);
}
