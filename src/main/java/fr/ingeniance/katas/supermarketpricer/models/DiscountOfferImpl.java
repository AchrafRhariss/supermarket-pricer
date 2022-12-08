package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;

public class DiscountOfferImpl implements IOffer {

	private BigDecimal discountRate;
	
	public DiscountOfferImpl() {
		this(BigDecimal.valueOf(0.2));
	}

	public DiscountOfferImpl(BigDecimal discountRate) {
		super();
		this.discountRate = discountRate;
	}

	@Override
	public BigDecimal calculatePrice(Product product, int quantity) {
		
		return product.getPrice().multiply(BigDecimal.valueOf(quantity)).multiply(BigDecimal.ONE.subtract(discountRate));
	}

}
