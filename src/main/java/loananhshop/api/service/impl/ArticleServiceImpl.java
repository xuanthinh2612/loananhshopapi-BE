package loananhshop.api.service.impl;

import loananhshop.api.model.Article;
import loananhshop.api.repository.ArticleRepository;
import loananhshop.api.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void save(Article article) {
        article.setCreatedAt(new Date());
        articleRepository.save(article);
    }

    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> findAvailableList() {
        return articleRepository.findAvilableList();
    }

    @Override
    public int countNonPublic() {
        return articleRepository.countNonPublic();
    }

    @Override
    public int countPublic() {
        return articleRepository.countPublic();
    }
}
