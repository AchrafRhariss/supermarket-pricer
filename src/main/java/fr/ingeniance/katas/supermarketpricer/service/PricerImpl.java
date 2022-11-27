package fr.ingeniance.katas.supermarketpricer.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class PricerImpl implements IPricer {

	@Override
	public BigDecimal payTheBill() {
		return BigDecimal.ZERO;
	}

}
