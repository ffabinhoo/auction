package ai.fabio.auction.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ai.fabio.auction.model.Bid;
@Repository
public interface BidService  extends CrudRepository<Bid, Long> {
	

}
