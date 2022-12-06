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


@Repository
public class OfferDaoImpl implements IOfferDao {

	private Map<Product, IOffer> pricingRules = new HashMap<>();
	
	{
		//Imitating the database query
		Product productA = new Product("A", BigDecimal.valueOf(20));
		pricingRules.put(productA, new BuyTwoGetOneOfferImpl());
	}
	
	@Override
	public IOffer findByProduct(Product product) {
		// TODO Auto-generated method stub
		return pricingRules.getOrDefault(product, new NoOfferImpl());
	}

	@Override
	public List<IOffer> findAllLessThan(LocalDate endDate) {
		// TODO Auto-generated method stub
		return new ArrayList<IOffer>(pricingRules.values());
	}

}
