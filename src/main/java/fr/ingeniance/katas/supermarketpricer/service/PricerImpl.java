package fr.ingeniance.katas.supermarketpricer.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ingeniance.katas.supermarketpricer.dao.IOfferDao;
import fr.ingeniance.katas.supermarketpricer.models.IOffer;
import fr.ingeniance.katas.supermarketpricer.models.Product;
import fr.ingeniance.katas.supermarketpricer.models.Quantity;

/**
 * <p>
 * 	Implementation for Pricer specification.
 * </p>
 * @author arhariss
 * 
 */

@Service
public class PricerImpl implements IPricer {

	@Autowired
	private IOfferDao offerDao;

	@Override
	public BigDecimal payTheBill(Map<Product, Quantity> cartItems) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (Entry<Product, Quantity> entry : cartItems.entrySet()) {
			IOffer productOffer = offerDao.findByProduct(entry.getKey());
			totalPrice = totalPrice.add(productOffer.calculatePrice(entry.getKey(), entry.getValue()));
		}
		return totalPrice;
	}

}
