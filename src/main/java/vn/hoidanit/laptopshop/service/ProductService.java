package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    
    public List<Product> getAllProduct(){
        return this.productRepository.findAll();
    }

    public Product getProductById(long id){
        return this.productRepository.findById(id);
    }

    
    public void deleteProduct(long id){
        this.productRepository.deleteById(id);
    }
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

public Product handleSave(Product product) {

    Product pro = this.productRepository.save(product);
        return pro;
    }

    public Product fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

}
