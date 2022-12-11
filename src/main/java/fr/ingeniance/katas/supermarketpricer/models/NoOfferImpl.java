package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NoOfferImpl implements IOffer {

	@Override
	public BigDecimal calculatePrice(Product product, double quantity) {
		return product.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
	}

}
