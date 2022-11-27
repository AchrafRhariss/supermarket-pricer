package fr.ingeniance.katas.supermarketpricer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockitoSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import fr.ingeniance.katas.supermarketpricer.dao.OfferDaoImpl;
import fr.ingeniance.katas.supermarketpricer.models.BuyTwoGetOneOfferImpl;
import fr.ingeniance.katas.supermarketpricer.models.Product;

@SpringBootTest
public class PricerImplTest {

	@Spy
	@InjectMocks
	private PricerImpl pricer;
	
	@Mock
	private OfferDaoImpl offerDao;

	@BeforeEach
	public void setUp() {
		//pricer = new PricerImpl();
	}
	
	@Test
	public void given_NoProductInCart_When_PayTheBill_Then_TotalPriceIsZero() {
		Map<String, List<Product>> cartItems = new HashMap<String, List<Product>>();
		
		Mockito.when(offerDao.findByProductName(Mockito.anyString())).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.ZERO, pricer.payTheBill(cartItems));
	}
	
	@Test
	public void given_OneProductInCart_When_PayTheBill_Then_TotalPriceIsTheProductPrice() {
		HashMap<String, List<Product>> cartItems = new HashMap<>();
		cartItems.put("A", buildProducts("A",BigDecimal.valueOf(20), 1));
		
		Mockito.when(offerDao.findByProductName(Mockito.anyString())).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.valueOf(20), pricer.payTheBill(cartItems));
	}
	
	@Test
	public void given_MoreProductsInCart_When_PayTheBill_Then_TotalPriceIsTheProductsSumPrice() {
		HashMap<String, List<Product>> cartItems = new HashMap<>();
		cartItems.put("A", buildProducts("A",BigDecimal.valueOf(20), 1));
		cartItems.put("B", buildProducts("B",BigDecimal.valueOf(50), 1));
		cartItems.put("C", buildProducts("C",BigDecimal.valueOf(30), 1));
		
		Mockito.when(offerDao.findByProductName(Mockito.anyString())).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.valueOf(100), pricer.payTheBill(cartItems));
	}
	
	@Test
	public void given_OfferOnProductInCart_When_PayTheBill_Then_OfferPriceIsApplicable() {
		HashMap<String, List<Product>> cartItems = new HashMap<>();
		cartItems.put("A", buildProducts("A",BigDecimal.valueOf(20), 3));
		
		Mockito.when(offerDao.findByProductName(Mockito.anyString())).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.valueOf(40), pricer.payTheBill(cartItems));
	}
	
	@Test
	public void given_MultipleOfferOnProductInCart_When_PayTheBill_Then_MultipleOfferPriceIsApplicable() {
		HashMap<String, List<Product>> cartItems = new HashMap<>();
		cartItems.put("A", buildProducts("A",BigDecimal.valueOf(20), 9));
		
		Mockito.when(offerDao.findByProductName(Mockito.anyString())).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.valueOf(120), pricer.payTheBill(cartItems));
	}
	
	private List<Product> buildProducts(String name, BigDecimal price, int numberOfProducts) {
		List<Product> productsList = new ArrayList<Product>();
		for (int i = 0; i < numberOfProducts; i++) {	
			productsList.add(new Product(name, price));
		}
		return productsList;
	}
	
}
