package fr.ingeniance.katas.supermarketpricer.service;

import java.math.BigDecimal;
import java.util.Map;

import fr.ingeniance.katas.supermarketpricer.models.Product;
import fr.ingeniance.katas.supermarketpricer.models.Quantity;

public interface IPricer {

	public BigDecimal payTheBill(Map<Product, Quantity> cartItems);
}
