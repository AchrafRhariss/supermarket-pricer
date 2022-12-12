/**
 * 
 */
package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author asus
 *
 */
public class TwoKilosForThreeDollarsOfferImpl implements IOffer {

	@Override
	public BigDecimal calculatePrice(Product product, Quantity quantity) {
		if(quantity.getUnit() == Unit.PIECE || product.getUnit() == Unit.PIECE) {
			throw new IllegalStateException("Offer isn't applicable for unit products");
		}
		
		BigDecimal convertedQuantity = BigDecimal.valueOf(quantity.getValue());
		
		if(quantity.getUnit() != product.getUnit()) {
			convertedQuantity = quantity.getUnit().convertTo(product.getUnit(), BigDecimal.valueOf(quantity.getValue()));
		}
		
		if(product.getUnit() == Unit.KILO) {
			BigDecimal[] divRem = convertedQuantity.divideAndRemainder(BigDecimal.valueOf(2));
			return divRem[0].multiply(BigDecimal.valueOf(3))
					.add(product.getPrice().multiply(divRem[1]))
					.setScale(2, RoundingMode.HALF_UP);
		}
		return product.getPrice().multiply(convertedQuantity)
					.setScale(2, RoundingMode.HALF_UP);
	}

}
