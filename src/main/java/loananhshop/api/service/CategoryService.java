package loananhshop.api.service;

import loananhshop.api.model.Category;

import java.util.List;

public interface CategoryService {

    void save(Category category);

    List<Category> findList();

    Category findByName(String name);

    void deleteById(Long id);

    Category findById(Long id);


}
