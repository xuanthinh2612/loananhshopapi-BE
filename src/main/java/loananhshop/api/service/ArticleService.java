package loananhshop.api.service;

import loananhshop.api.model.Article;

import java.util.List;

public interface ArticleService extends CommonService<Article> {
    List<Article> findAvailableList();

    int countNonPublic();
    int countPublic();
}
