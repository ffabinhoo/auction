package ai.fabio.auction.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ai.fabio.auction.model.Product;

@Repository
public interface  ProductService  extends CrudRepository<Product, Long> {

}
