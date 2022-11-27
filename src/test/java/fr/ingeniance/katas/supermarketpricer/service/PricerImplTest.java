package fr.ingeniance.katas.supermarketpricer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PricerImplTest {

	@Spy
	private IPricer pricer;
	
	@Test
	public void given_NoProductInCart_When_PayTheBill_Then_TotalPriceIsZero() {
		assertEquals(BigDecimal.ZERO, pricer.payTheBill());
	}
}
