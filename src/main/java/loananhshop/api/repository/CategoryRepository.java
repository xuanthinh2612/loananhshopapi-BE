package loananhshop.api.repository;

import loananhshop.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoriesByName(String name);

}
