package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;

public class BuyTwoGetOneOfferImpl implements IOffer {

	@Override
	public BigDecimal calculatePrice(Product product, int quantity) {
		if(quantity >= 3) {
			return product.getPrice().multiply(BigDecimal.valueOf(quantity-(quantity/3)));
		}
		return product.getPrice().multiply(BigDecimal.valueOf(quantity));
	}

}
