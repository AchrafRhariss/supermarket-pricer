package fr.ingeniance.katas.supermarketpricer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import fr.ingeniance.katas.supermarketpricer.dao.OfferDaoImpl;
import fr.ingeniance.katas.supermarketpricer.models.BuyTwoGetOneOfferImpl;
import fr.ingeniance.katas.supermarketpricer.models.DiscountOfferImpl;
import fr.ingeniance.katas.supermarketpricer.models.NoOfferImpl;
import fr.ingeniance.katas.supermarketpricer.models.Product;

@SpringBootTest
public class PricerImplTest {

	@Spy
	@InjectMocks
	private PricerImpl pricer;
	
	@Mock
	private OfferDaoImpl offerDao;

	@BeforeEach
	void setUp() {
		//pricer = new PricerImpl();
	}
	
	@Test
	void given_NoProductInCart_When_PayTheBill_Then_TotalPriceIsZero() {
		Map<Product, Integer> cartItems = new HashMap<>();
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.ZERO, pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_OneProductInCart_When_PayTheBill_Then_TotalPriceIsTheProductPrice() {
		HashMap<Product, Integer> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), 1);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.valueOf(20), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_ProductWithNoOfferInCart_When_PayTheBill_Then_TotalPriceIsTheProductPrice() {
		HashMap<Product, Integer> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), 1);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new NoOfferImpl());
		
		assertEquals(BigDecimal.valueOf(20), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_MoreProductsInCart_When_PayTheBill_Then_TotalPriceIsTheProductsSumPrice() {
		HashMap<Product, Integer> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), 1);
		cartItems.put(new Product("B",BigDecimal.valueOf(50)), 1);
		cartItems.put(new Product("C",BigDecimal.valueOf(30)), 1);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.valueOf(100), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_OfferOnProductInCart_When_PayTheBill_Then_OfferPriceIsApplicable() {
		HashMap<Product, Integer> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), 3);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.valueOf(40), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_MultipleOfferOnProductInCart_When_PayTheBill_Then_MultipleOfferPriceIsApplicable() {
		HashMap<Product, Integer> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), 9);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.valueOf(120), pricer.payTheBill(cartItems));
	}
	
	
	@Test
	void given_OneProductInCartWith20PercentDiscountOffer_When_PayTheBill_Then_TotalPriceIsTheDiscountPrice() {
		HashMap<Product, Integer> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(100)), 1);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new DiscountOfferImpl());
		
		assertEquals(BigDecimal.valueOf(80.0), pricer.payTheBill(cartItems));
	}
	
	
	@Test
	void given_OneProductInCartWith50PercentDiscountOffer_When_PayTheBill_Then_TotalPriceIsTheDiscountPrice() {
		HashMap<Product, Integer> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(100)), 1);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new DiscountOfferImpl(0.5));
		
		assertEquals(BigDecimal.valueOf(50.0), pricer.payTheBill(cartItems));
	}
	
	
	@Test
	void given_MultipleProductsInCartWith20PercentDiscountOffer_When_PayTheBill_Then_TotalPriceIsTheDiscountPrice() {
		HashMap<Product, Integer> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(10)), 2);
		cartItems.put(new Product("B",BigDecimal.valueOf(20)), 3);
		cartItems.put(new Product("C",BigDecimal.valueOf(10)), 2);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new DiscountOfferImpl(0.2));
		
		assertEquals(BigDecimal.valueOf(80.0), pricer.payTheBill(cartItems));
	}
	
}
