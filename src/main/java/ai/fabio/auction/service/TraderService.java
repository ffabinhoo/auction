package ai.fabio.auction.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ai.fabio.auction.model.Trader;

@Repository
public interface  TraderService  extends CrudRepository<Trader, Long> {

}
