package fr.ingeniance.katas.supermarketpricer.models;

/**
 * <p>
 * 	This class represents the quantity for weightable product for example : 3Kg, 5Pounds, 10g...
 * </p>
 * @author arhariss
 * 
 */
public class Quantity {

	private Unit unit;
	private Double value;
	
	
	private Quantity() {
		super();
	}
	
	private Quantity(Unit unit, double value) {
		super();
		this.unit = unit;
		this.value = value;
	}

	public static Quantity of(Unit unit, Double value) {
		if(unit == Unit.PIECE && value.intValue() != value) {
			throw new IllegalArgumentException("Quantity should be integer for piece unit");
		}
		return new Quantity(unit, value);
	}

	public Unit getUnit() {
		return unit;
	}

	public Double getValue() {
		return value;
	}
	
}
