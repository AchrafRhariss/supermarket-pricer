package fr.ingeniance.katas.supermarketpricer.dao;

import java.time.LocalDate;
import java.util.List;

import fr.ingeniance.katas.supermarketpricer.models.IOffer;
import fr.ingeniance.katas.supermarketpricer.models.Product;

public interface IOfferDao {
	public IOffer findByProduct(Product product);
	public List<IOffer> findAllLessThan(LocalDate endDate);
}
