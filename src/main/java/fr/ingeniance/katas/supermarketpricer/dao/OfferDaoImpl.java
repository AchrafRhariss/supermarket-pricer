package fr.ingeniance.katas.supermarketpricer.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import fr.ingeniance.katas.supermarketpricer.models.BuyTwoGetOneOfferImpl;
import fr.ingeniance.katas.supermarketpricer.models.IOffer;
import fr.ingeniance.katas.supermarketpricer.models.NoOfferImpl;


@Repository
public class OfferDaoImpl implements IOfferDao {

	private Map<String, IOffer> pricingRules = new HashMap<>();
	
	{
		//Imitating the database query
		pricingRules.put("A", new BuyTwoGetOneOfferImpl());
	}
	
	@Override
	public IOffer findByProductName(String productName) {
		// TODO Auto-generated method stub
		return pricingRules.getOrDefault(productName, new NoOfferImpl());
	}

	@Override
	public List<IOffer> findAllLessThan(LocalDate endDate) {
		// TODO Auto-generated method stub
		return new ArrayList<IOffer>(pricingRules.values());
	}

}
