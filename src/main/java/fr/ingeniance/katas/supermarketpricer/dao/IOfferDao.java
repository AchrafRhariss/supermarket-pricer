package fr.ingeniance.katas.supermarketpricer.dao;

import java.time.LocalDate;
import java.util.List;

import fr.ingeniance.katas.supermarketpricer.models.IOffer;
import fr.ingeniance.katas.supermarketpricer.models.Product;

/**
 * <p>
 * 	Specification for Offers repository, it contains two methods now.
 * </p>
 * @author arhariss
 * 
 */
public interface IOfferDao {
	public IOffer findByProduct(Product product);
	public List<IOffer> findAllLessThan(LocalDate endDate);
}
