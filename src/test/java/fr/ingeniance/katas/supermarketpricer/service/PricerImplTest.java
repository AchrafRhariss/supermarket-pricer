package fr.ingeniance.katas.supermarketpricer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import fr.ingeniance.katas.supermarketpricer.models.Unit;

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
		Map<Product, Double> cartItems = new HashMap<>();
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.ZERO, pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_OneProductInCart_When_PayTheBill_Then_TotalPriceIsTheProductPrice() {
		HashMap<Product, Double> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), 1.0);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(new BigDecimal("20.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_ProductWithNoOfferInCart_When_PayTheBill_Then_TotalPriceIsTheProductPrice() {
		HashMap<Product, Double> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), 1.0);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new NoOfferImpl());
		
		assertEquals(new BigDecimal("20.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_MoreProductsInCart_When_PayTheBill_Then_TotalPriceIsTheProductsSumPrice() {
		HashMap<Product, Double> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), 1.0);
		cartItems.put(new Product("B",BigDecimal.valueOf(50)), 1.0);
		cartItems.put(new Product("C",BigDecimal.valueOf(30)), 1.0);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(new BigDecimal("100.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_OfferOnProductInCart_When_PayTheBill_Then_OfferPriceIsApplicable() {
		HashMap<Product, Double> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), 3.0);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(new BigDecimal("40.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_MultipleOfferOnProductInCart_When_PayTheBill_Then_MultipleOfferPriceIsApplicable() {
		HashMap<Product, Double> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), 9.0);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(new BigDecimal("120.00"), pricer.payTheBill(cartItems));
	}
	
	
	@Test
	void given_OneProductInCartWith20PercentDiscountOffer_When_PayTheBill_Then_TotalPriceIsTheDiscountPrice() {
		HashMap<Product, Double> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(100)), 1.0);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new DiscountOfferImpl());
		
		assertEquals(new BigDecimal("80.00"), pricer.payTheBill(cartItems));
	}
	
	
	@Test
	void given_OneProductInCartWith50PercentDiscountOffer_When_PayTheBill_Then_TotalPriceIsTheDiscountPrice() {
		HashMap<Product, Double> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(100)), 1.0);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new DiscountOfferImpl(0.5));
		
		assertEquals(new BigDecimal("50.00"), pricer.payTheBill(cartItems));
	}
	
	
	@Test
	void given_MultipleProductsInCartWith20PercentDiscountOffer_When_PayTheBill_Then_TotalPriceIsTheDiscountPrice() {
		HashMap<Product, Double> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(10)), 2.0);
		cartItems.put(new Product("B",BigDecimal.valueOf(20)), 3.0);
		cartItems.put(new Product("C",BigDecimal.valueOf(10)), 2.0);
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new DiscountOfferImpl(0.2));
		
		assertEquals(new BigDecimal("80.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_WeightTableProductWithIcompatibleOfferInCart_When_PayTheBill_Then_ExceptionIsRaised() {
		Map<Product, Double> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(10),Unit.POUND), 3.0);
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		Exception exception = assertThrows(IllegalStateException.class, () -> pricer.payTheBill(cartItems));
		String expectedMessage = "Offer isn't applicable for weightable products";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
}
