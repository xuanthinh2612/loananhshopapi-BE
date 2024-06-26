package loananhshop.api.repository;

import loananhshop.api.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // Status 1: public 0 : unPublic
    @Query(value = "select * from article where status = 1", nativeQuery = true)
    List<Article> findAvilableList();

    @Query(value = "select count(*) from article where status = 1", nativeQuery = true)
    int countPublic();

    @Query(value = "select count(*) from article where status = 0", nativeQuery = true)
    int countNonPublic();
}
