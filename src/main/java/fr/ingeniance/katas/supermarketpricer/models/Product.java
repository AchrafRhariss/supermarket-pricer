package fr.ingeniance.katas.supermarketpricer.models;

import java.math.BigDecimal;

/**
 * <p>
 * 	Product entity defined with 3 attributes : name, price and {@link Unit} .
 * </p>
 * @author arhariss
 * 
 */
public class Product {
	
	private String name;
	private BigDecimal price;
	private Unit unit;
	
	public Product(String name, BigDecimal price, Unit unit) {
		this.name = name;
		this.price = price;
		this.unit = unit;
	}
	
	public Product(String name, BigDecimal price) {
		this(name, price, Unit.PIECE);
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		return true;
	}
	
	
	
}
