package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <p>
 * 	Implementation for Offer specification, this is the default one, offer price is the product price.
 * </p>
 * @author arhariss
 * 
 */
public class NoOfferImpl implements IOffer {

	@Override
	public BigDecimal calculatePrice(Product product, Quantity quantity) {
		
		if(quantity.getUnit() == Unit.PIECE && product.getUnit() != Unit.PIECE) {
			throw new IllegalStateException("Quantity unit mismatch product unit");
		}
		
		BigDecimal convertedQuantity = BigDecimal.valueOf(quantity.getValue());
		if(quantity.getUnit() != product.getUnit() && quantity.getUnit() != Unit.PIECE && product.getUnit() != Unit.PIECE) {
			convertedQuantity = quantity.getUnit().convertTo(product.getUnit(), BigDecimal.valueOf(quantity.getValue()));
		}
		return product.getPrice().multiply(convertedQuantity).setScale(2, RoundingMode.HALF_UP);
	}

}
