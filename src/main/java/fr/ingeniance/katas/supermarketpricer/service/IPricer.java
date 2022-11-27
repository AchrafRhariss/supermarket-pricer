package fr.ingeniance.katas.supermarketpricer.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import fr.ingeniance.katas.supermarketpricer.models.Product;

public interface IPricer {

	public BigDecimal payTheBill(Map<String, List<Product>> cartItems);
}
