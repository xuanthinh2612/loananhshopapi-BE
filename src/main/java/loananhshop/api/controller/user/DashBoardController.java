package loananhshop.api.controller.user;

import loananhshop.api.common.CommonConst;
import loananhshop.api.model.Article;
import loananhshop.api.model.Cover;
import loananhshop.api.model.Product;
import loananhshop.api.service.ArticleService;
import loananhshop.api.service.CategoryService;
import loananhshop.api.service.CoverService;
import loananhshop.api.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("")
@Slf4j
public class DashBoardController extends UserBaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CoverService coverService;

    @GetMapping(value = {"/trang-chu", "", "/"})
    public String showDashBoard(Model model, RedirectAttributes attributes) {
        log.debug("user dash board");

        model.addAttribute(CommonConst.PAGE_MODE, CommonConst.HOME_PAGE_MODE);
        List<Cover> mainCoverList = coverService.getMainCoverList().stream().limit(3).toList();
        List<Cover> subCoverList = coverService.getSubCoverList().stream().limit(2).toList();
        List<Article> articleList = articleService.findAvailableList().stream().limit(20).toList();
        List<Product> productList = productService.findAvailableList();
        model.addAttribute("articleList", articleList);
        model.addAttribute("productList", productList);
        model.addAttribute("mainCoverList", mainCoverList);
        model.addAttribute("subCoverList", subCoverList);
        return "user/dashboard/index";
    }

    @GetMapping(value = {"/about", "/trang-chu/about"})
    public String aboutUs(Model model) {
        model.addAttribute(CommonConst.PAGE_MODE, CommonConst.ABOUT_PAGE_MODE);
        return "/user/dashboard/about";
    }

    @GetMapping("/category/{id}")
    public String searchByCategory(@PathVariable("id") Long id, Model model) {

        List<Article> articleList = articleService.findAvailableList().stream().limit(20).toList();
        model.addAttribute("articleList", articleList);
        List<Product> productList = productService.findAvailableListByCategoryId(id);
        model.addAttribute("productList", productList);
        List<Cover> subCoverList = coverService.getSubCoverList().stream().limit(2).toList();
        model.addAttribute("subCoverList", subCoverList);
        model.addAttribute(CommonConst.PAGE_MODE, CommonConst.SEARCH_MODE);

        return "/user/dashboard/index";
    }


    @GetMapping("/search")
    public String searchProduct(@RequestParam("key") String key, Model model) {

        List<Article> articleList = articleService.findAvailableList().stream().limit(20).toList();
        model.addAttribute("articleList", articleList);
        List<Product> productList = productService.findByKey(key);
        model.addAttribute("productList", productList);
        model.addAttribute(CommonConst.PAGE_MODE, CommonConst.SEARCH_MODE);
        List<Cover> subCoverList = coverService.getSubCoverList().stream().limit(2).toList();
        model.addAttribute("subCoverList", subCoverList);

        return "/user/dashboard/index";
    }
}
