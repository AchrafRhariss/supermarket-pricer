package fr.ingeniance.katas.supermarketpricer.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import fr.ingeniance.katas.supermarketpricer.models.Product;

@Service
public class PricerImpl implements IPricer {

	@Override
	public BigDecimal payTheBill(Map<String, List<Product>> cartItems) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (List<Product> products : cartItems.values()) {
			totalPrice = totalPrice.add(products.get(0).getPrice().multiply(BigDecimal.valueOf(products.size())));
		}
		return totalPrice;
	}

}
