package loananhshop.api.controller.user;

import loananhshop.api.common.CommonConst;
import loananhshop.api.model.Article;
import loananhshop.api.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/blogs" )
@CrossOrigin("*")
public class ArticleController extends UserBaseController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("" )
    public ResponseEntity<List<Article>> getListArticle(Model model) {

        List<Article> articleList = articleService.findAvailableList();

        return new ResponseEntity<>(articleList, HttpStatus.OK);
    }

    @GetMapping("/{id}" )
    public ResponseEntity<Article> showDetail(@PathVariable("id" ) Long id, Model model) {
        Article article = articleService.findById(id);

        if (article.getStatus() == CommonConst.FLAG_OFF) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

//        List<Article> relatedListBottom = new ArrayList<>(articleService.findAvailableList().stream().limit(20).toList());
//        relatedListBottom.remove(article);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/main-blog")
    public ResponseEntity<Article> getMainArticle() {
        Article mainArticle = null;
        List<Article> articleList = articleService.findAvailableList();

        for (Article article : articleList) {
            if (article.getOnTop() == CommonConst.FLAG_ON) {
                mainArticle = article;
                break;
            }
        }
        return new ResponseEntity<>(mainArticle, HttpStatus.OK);
    }
}
