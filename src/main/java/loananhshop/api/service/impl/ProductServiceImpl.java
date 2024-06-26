package loananhshop.api.service.impl;

import loananhshop.api.model.Product;
import loananhshop.api.repository.ProductRepository;
import loananhshop.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findList() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByProductName(String name) {
        return productRepository.findAllByName(name);
    }

    @Override
    public List<Product> findByKey(String searchKey) {
        return productRepository.findByKey(searchKey);
    }

    @Override
    public List<Product> findAvailableList() {
        return productRepository.findAvailableList();
    }

    @Override
    public List<Product> findAvailableListByCategoryId(Long id) {
        return productRepository.findAvailableListByCategoryId(id);
    }

    @Override
    public int countAvailable() {
        return productRepository.countAvailable();
    }

    @Override
    public int countByStatus(int status) {
        return productRepository.countByStatus(status);
    }
}
