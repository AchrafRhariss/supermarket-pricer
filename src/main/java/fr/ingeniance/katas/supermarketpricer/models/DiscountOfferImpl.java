package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountOfferImpl implements IOffer {

	private BigDecimal discountRate;
	
	public DiscountOfferImpl() {
		this(0.2);
	}

	public DiscountOfferImpl(double discountRate) {
		super();
		this.discountRate = BigDecimal.valueOf(discountRate);
	}

	@Override
	public BigDecimal calculatePrice(Product product, double quantity) {
		
		return product.getPrice().multiply(BigDecimal.valueOf(quantity))
					.multiply(BigDecimal.ONE.subtract(discountRate))
					.setScale(2, RoundingMode.HALF_UP);
	}

}
