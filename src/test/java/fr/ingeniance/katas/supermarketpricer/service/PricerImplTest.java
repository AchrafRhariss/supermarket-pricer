package fr.ingeniance.katas.supermarketpricer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import fr.ingeniance.katas.supermarketpricer.models.Product;

@SpringBootTest
public class PricerImplTest {

	
	private IPricer pricer;

	@BeforeEach
	public void setUp() {
		pricer = new PricerImpl();
	}
	
	@Test
	public void given_NoProductInCart_When_PayTheBill_Then_TotalPriceIsZero() {
		Map<String, List<Product>> cartItems = new HashMap<String, List<Product>>();
		assertEquals(BigDecimal.ZERO, pricer.payTheBill(cartItems));
	}
	
	@Test
	public void given_OneProductInCart_When_PayTheBill_Then_TotalPriceIsTheProductPrice() {
		HashMap<String, List<Product>> cartItems = new HashMap<>();
		cartItems.put("A", buildProducts("A",BigDecimal.valueOf(20), 1));
		
		assertEquals(BigDecimal.valueOf(20), pricer.payTheBill(cartItems));
	}
	
	private List<Product> buildProducts(String name, BigDecimal price, int numberOfProducts) {
		List<Product> productsList = new ArrayList<Product>();
		for (int i = 0; i < numberOfProducts; i++) {	
			productsList.add(new Product(name, price));
		}
		return productsList;
	}
	
}
