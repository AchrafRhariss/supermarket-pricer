package fr.ingeniance.katas.supermarketpricer.dao;

import java.time.LocalDate;
import java.util.List;

import fr.ingeniance.katas.supermarketpricer.models.IOffer;

public interface IOfferDao {
	public IOffer findByProductName(String productName);
	public List<IOffer> findAllLessThan(LocalDate endDate);
}
