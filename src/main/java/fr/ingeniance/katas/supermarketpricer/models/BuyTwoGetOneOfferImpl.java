package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * <p>
 * 	Implementation for Offer specification, using this offer you can buy 3 products with price of two.
 * </p>
 * @author arhariss
 * 
 */
public class BuyTwoGetOneOfferImpl implements IOffer {

	@Override
	public BigDecimal calculatePrice(Product product, Quantity quantity) {
		if(product.getUnit() != Unit.PIECE || quantity.getUnit() != Unit.PIECE) {
			throw new IllegalStateException("Offer isn't applicable for weightable products");
		} else {
			if(quantity.getValue() >= 3) {
				return product.getPrice()
						.multiply(BigDecimal.valueOf(2*quantity.getValue()/3L))
						.setScale(2, RoundingMode.HALF_UP);
			}
			return product.getPrice()
					.multiply(BigDecimal.valueOf(quantity.getValue()))
					.setScale(2, RoundingMode.HALF_UP);
		}
	}

}
