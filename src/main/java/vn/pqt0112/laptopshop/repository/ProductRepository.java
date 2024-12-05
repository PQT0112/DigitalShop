package vn.pqt0112.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.pqt0112.laptopshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
