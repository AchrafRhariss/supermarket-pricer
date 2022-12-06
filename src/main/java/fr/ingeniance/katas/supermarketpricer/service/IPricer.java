package fr.ingeniance.katas.supermarketpricer.service;

import java.math.BigDecimal;
import java.util.Map;

import fr.ingeniance.katas.supermarketpricer.models.Product;

public interface IPricer {

	public BigDecimal payTheBill(Map<Product, Integer> cartItems);
}
