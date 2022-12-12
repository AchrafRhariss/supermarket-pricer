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
import fr.ingeniance.katas.supermarketpricer.models.Quantity;
import fr.ingeniance.katas.supermarketpricer.models.TwoKilosForThreeDollarsOfferImpl;
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
		Map<Product, Quantity> cartItems = new HashMap<>();
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(BigDecimal.ZERO, pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_OneProductInCart_When_PayTheBill_Then_TotalPriceIsTheProductPrice() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), Quantity.of(Unit.PIECE, 1.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(new BigDecimal("20.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_ProductWithNoOfferInCart_When_PayTheBill_Then_TotalPriceIsTheProductPrice() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), Quantity.of(Unit.PIECE, 1.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new NoOfferImpl());
		
		assertEquals(new BigDecimal("20.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_MoreProductsInCart_When_PayTheBill_Then_TotalPriceIsTheProductsSumPrice() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), Quantity.of(Unit.PIECE, 1.0));
		cartItems.put(new Product("B",BigDecimal.valueOf(50)), Quantity.of(Unit.PIECE, 1.0));
		cartItems.put(new Product("C",BigDecimal.valueOf(30)), Quantity.of(Unit.PIECE, 1.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(new BigDecimal("100.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_OfferOnProductInCart_When_PayTheBill_Then_OfferPriceIsApplicable() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), Quantity.of(Unit.PIECE, 3.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(new BigDecimal("40.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_MultipleOfferOnProductInCart_When_PayTheBill_Then_MultipleOfferPriceIsApplicable() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20)), Quantity.of(Unit.PIECE, 9.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		assertEquals(new BigDecimal("120.00"), pricer.payTheBill(cartItems));
	}
	
	
	@Test
	void given_OneProductInCartWith20PercentDiscountOffer_When_PayTheBill_Then_TotalPriceIsTheDiscountPrice() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(100)), Quantity.of(Unit.PIECE, 1.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new DiscountOfferImpl());
		
		assertEquals(new BigDecimal("80.00"), pricer.payTheBill(cartItems));
	}
	
	
	@Test
	void given_OneProductInCartWith50PercentDiscountOffer_When_PayTheBill_Then_TotalPriceIsTheDiscountPrice() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(100)), Quantity.of(Unit.PIECE, 1.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new DiscountOfferImpl(0.5));
		
		assertEquals(new BigDecimal("50.00"), pricer.payTheBill(cartItems));
	}
	
	
	@Test
	void given_MultipleProductsInCartWith20PercentDiscountOffer_When_PayTheBill_Then_TotalPriceIsTheDiscountPrice() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(10)), Quantity.of(Unit.PIECE, 2.0));
		cartItems.put(new Product("B",BigDecimal.valueOf(20)), Quantity.of(Unit.PIECE, 3.0));
		cartItems.put(new Product("C",BigDecimal.valueOf(10)), Quantity.of(Unit.PIECE, 2.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new DiscountOfferImpl(0.2));
		
		assertEquals(new BigDecimal("80.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_WeightableProductWithIncompatibleOfferInCart_When_PayTheBill_Then_ExceptionIsRaised() {
		Map<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(10),Unit.POUND), Quantity.of(Unit.PIECE, 3.0));
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new BuyTwoGetOneOfferImpl());
		
		Exception exception = assertThrows(IllegalStateException.class, () -> pricer.payTheBill(cartItems));
		String expectedMessage = "Offer isn't applicable for weightable products";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void given_OneWeightableProductInCart_When_PayTheBill_Then_TotalPriceIsTheProductPrice() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(20),Unit.GRAM), Quantity.of(Unit.GRAM, 1.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new NoOfferImpl());
		
		assertEquals(new BigDecimal("20.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_OrderedQuantityUnitDifferentThanProductUnitInCart_When_PayTheBill_Then_TotalPriceIsUnitConvertedQuantityPrice() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(1000),Unit.KILO), Quantity.of(Unit.GRAM, 500.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new NoOfferImpl());
		
		assertEquals(new BigDecimal("500.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_2KiloFor3OfferProductInCart_When_PayTheBill_Then_TotalPriceIsOfferPrice() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(2),Unit.KILO), Quantity.of(Unit.KILO, 2.0));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new TwoKilosForThreeDollarsOfferImpl());
		
		assertEquals(new BigDecimal("3.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_PoundQuantitySatisfied2KgFor3OfferProductInCart_When_PayTheBill_Then_TotalPriceIsOfferPrice() {
		HashMap<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(2),Unit.KILO), Quantity.of(Unit.POUND, 4.4));
		
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new TwoKilosForThreeDollarsOfferImpl());
		
		assertEquals(new BigDecimal("3.00"), pricer.payTheBill(cartItems));
	}
	
	@Test
	void given_UnitProductWithIncompatibleOfferInCart_When_PayTheBill_Then_ExceptionIsRaised() {
		Map<Product, Quantity> cartItems = new HashMap<>();
		cartItems.put(new Product("A",BigDecimal.valueOf(10),Unit.PIECE), Quantity.of(Unit.KILO, 3.0));
		Mockito.when(offerDao.findByProduct(ArgumentMatchers.isA(Product.class))).thenReturn(new TwoKilosForThreeDollarsOfferImpl());
		
		Exception exception = assertThrows(IllegalStateException.class, () -> pricer.payTheBill(cartItems));
		String expectedMessage = "Offer isn't applicable for unit products";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
}
