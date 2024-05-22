package loananhshop.api.service.impl;

import loananhshop.api.model.Category;
import loananhshop.api.repository.CategoryRepository;
import loananhshop.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

   @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }


    @Override
    public List<Category> findList() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findCategoriesByName(name);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }


}
