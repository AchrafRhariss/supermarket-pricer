package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BuyTwoGetOneOfferImpl implements IOffer {

	@Override
	public BigDecimal calculatePrice(Product product, double quantity) {
		if(product.getUnit() != Unit.PIECE) {
			throw new IllegalStateException("Offer isn't applicable for weightable products");
		} else {
			if(quantity >= 3) {
				return product.getPrice().multiply(BigDecimal.valueOf(2*quantity/3L)).setScale(2, RoundingMode.HALF_UP);
			}
			return product.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
		}
	}

}
