package loananhshop.api.controller.admin;

import loananhshop.api.common.CommonConst;
import loananhshop.api.controller.helper.Helper;
import loananhshop.api.model.Article;
import loananhshop.api.model.Image;
import loananhshop.api.model.SubContent;
import loananhshop.api.service.ArticleService;
import loananhshop.api.service.ImageService;
import loananhshop.api.service.SubContentService;
import loananhshop.api.service.file.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/blogs")
@CrossOrigin("*")
public class ArticleManageController extends AdminBaseController {

    @Autowired
    private Environment environment;

    @Autowired
    FilesStorageService storageService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SubContentService subContentService;
    @Autowired
    private Helper helper;

    /**
     * List
     **/

    @GetMapping("")
    public ResponseEntity<List<Article>> index(Model model) {
        List<Article> articleList = articleService.findAll();

        return new ResponseEntity<>(articleList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public String createArticle(@RequestBody Article article, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }
        articleService.save(article);
        return "success";
    }

    /**
     * Get Article By ID
     **/

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") Long id) {
        Article article = articleService.findById(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PostMapping("/disable/{id}")
    public String disableArticle(@PathVariable("id") Long id, Model model) {
        Article article = articleService.findById(id);
        article.setStatus(CommonConst.FLAG_OFF);
        articleService.save(article);
        return "success";
    }

    @PostMapping("/enable/{id}")
    public String enableArticle(@PathVariable("id") Long id, Model model) {
        Article article = articleService.findById(id);
        article.setStatus(CommonConst.FLAG_ON);
        articleService.save(article);
        return "success";
    }

    @PostMapping("/setOnTop/{id}")
    public String setOnTop(@PathVariable("id") Long id, Model model) {
        Article article = articleService.findById(id);
        if (article.getOnTop() == CommonConst.FLAG_ON) {
            return "success";
        }
        article.setOnTop(CommonConst.FLAG_ON);
        articleService.save(article);
        return "success";
    }

    @PostMapping("/setOffTop/{id}")
    public String setOffTop(@PathVariable("id") Long id, Model model) {
        Article article = articleService.findById(id);
        article.setOnTop(CommonConst.FLAG_OFF);
        articleService.save(article);
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id) {
        try {
            articleService.deleteById(id);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
    

    @PostMapping("/addImage/{subContentIndex}")
    public String addImage(@ModelAttribute("article") Article article, @PathVariable("subContentIndex") int subContentIndex, @RequestParam("imageFile") MultipartFile imageFile, Model model) {

        assert article.getSubContentList() != null;
        SubContent subContent = article.getSubContentList().get(subContentIndex);

        String fileName = helper.genRandomFileName(imageFile.getOriginalFilename());
        Image image = new Image();

        try {
            storageService.save(imageFile, fileName);
            image.setImageName(fileName);
            image.setImageUrl("/" + environment.getProperty("upload.path") + "/" + fileName);
            subContent.setImage(image);
            articleService.save(article);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";

    }


    @PostMapping("/deleteSubContent/{subContentIndex}")
    public String deleteSubContent(@ModelAttribute("article") Article article, @PathVariable("subContentIndex") int subContentIndex, Model model) {
        try {
            assert article.getSubContentList() != null;
            SubContent subContent = article.getSubContentList().get(subContentIndex);
            article.getSubContentList().remove(subContentIndex);
            articleService.save(article);
            deleteSubContentImageFile(subContent);
            if (!ObjectUtils.isEmpty(subContent.getId())) {
                subContentService.deleteById(subContent.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";

    }

    @PostMapping("/setImageAsAvatar/{subContentIndex}")
    public String setImageAsAvatar(@ModelAttribute("article") Article article, @PathVariable("subContentIndex") int subContentIndex, Model model) {
        try {
            assert article.getSubContentList() != null;
            SubContent subContent = article.getSubContentList().get(subContentIndex);
            Image image = subContent.getImage();
            assert image != null;
            image.setAvatar(true);
            imageService.save(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";

    }

    @PostMapping("/removeImageFromAvatar/{subContentIndex}")
    public String removeImageFromAvatar(@ModelAttribute("article") Article article, @PathVariable("subContentIndex") int subContentIndex, Model model) {
        try {
            assert article.getSubContentList() != null;
            SubContent subContent = article.getSubContentList().get(subContentIndex);
            Image image = subContent.getImage();
            assert image != null;
            image.setAvatar(false);
            imageService.save(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";

    }

    /**
     * =================== PRIVATE =======================
     **/
    private void deleteSubContentImageFile(SubContent subContent) {
        Image image = subContent.getImage();
        if (!ObjectUtils.isEmpty(image)) {
            try {
                // delete image from upload file
                storageService.deleteByFileName(image.getImageName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
