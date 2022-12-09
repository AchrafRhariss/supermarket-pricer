package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;

public class Product {
	
	private String name;
	private BigDecimal price;

	public Product(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
}
