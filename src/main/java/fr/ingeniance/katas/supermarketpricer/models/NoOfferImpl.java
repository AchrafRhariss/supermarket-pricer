package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;

public class NoOfferImpl implements IOffer {

	@Override
	public BigDecimal calculatePrice(Product product, int quantity) {
		return product.getPrice().multiply(BigDecimal.valueOf(quantity));
	}

}
