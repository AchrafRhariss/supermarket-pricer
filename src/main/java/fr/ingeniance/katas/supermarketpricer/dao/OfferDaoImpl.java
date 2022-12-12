package fr.ingeniance.katas.supermarketpricer.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import fr.ingeniance.katas.supermarketpricer.models.BuyTwoGetOneOfferImpl;
import fr.ingeniance.katas.supermarketpricer.models.IOffer;
import fr.ingeniance.katas.supermarketpricer.models.NoOfferImpl;
import fr.ingeniance.katas.supermarketpricer.models.Product;
import fr.ingeniance.katas.supermarketpricer.models.Unit;

/**
 * <p>
 * 	1st implementation for Offers repository, that imitates the database queries.
 * </p>
 * @author arhariss
 * 
 */

@Repository
public class OfferDaoImpl implements IOfferDao {

	private Map<Product, IOffer> pricingRules = new HashMap<>();
	
	public OfferDaoImpl() {
		//Imitating the database query
		Product productA = new Product("A", BigDecimal.valueOf(20), Unit.PIECE);
		pricingRules.put(productA, new BuyTwoGetOneOfferImpl());
	}
	

	public Map<Product, IOffer> getPricingRules() {
		return pricingRules;
	}

	public void setPricingRules(Map<Product, IOffer> pricingRules) {
		this.pricingRules = pricingRules;
	}

	@Override
	public IOffer findByProduct(Product product) {
		return pricingRules.getOrDefault(product, new NoOfferImpl());
	}

	@Override
	public List<IOffer> findAllLessThan(LocalDate endDate) {
		return new ArrayList<>(pricingRules.values());
	}

}
