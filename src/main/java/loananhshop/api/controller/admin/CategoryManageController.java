package loananhshop.api.controller.admin;

import jakarta.validation.Valid;
import loananhshop.api.controller.helper.Helper;
import loananhshop.api.model.Category;
import loananhshop.api.model.Image;
import loananhshop.api.service.CategoryService;
import loananhshop.api.service.ImageService;
import loananhshop.api.service.file.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category/")
@CrossOrigin("*")
public class CategoryManageController extends AdminBaseController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private Environment environment;
    @Autowired
    private Helper helper;
    @Autowired
    private ImageService imageService;
    @Autowired
    FilesStorageService storageService;

    /**
     * List
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> index() {
        List<Category> categoryList = categoryService.findList();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    /**
     * get category by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/save")
    public String createCategory(@Valid @RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }
        categoryService.save(category);
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        try {
            categoryService.deleteById(id);
        } catch (Exception e) {
            return "error";
        }

        return "success";
    }

    //========================= PRIVATE METHOD =====================
}
