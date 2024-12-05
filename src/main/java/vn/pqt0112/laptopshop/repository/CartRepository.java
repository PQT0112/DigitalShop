package vn.pqt0112.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.pqt0112.laptopshop.domain.Cart;
import vn.pqt0112.laptopshop.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(User user);
}
