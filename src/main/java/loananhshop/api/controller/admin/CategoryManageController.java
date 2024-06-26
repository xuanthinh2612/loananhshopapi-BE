package loananhshop.api.controller.admin;

import loananhshop.api.controller.helper.Helper;
import loananhshop.api.model.*;
import loananhshop.api.service.CategoryService;
import loananhshop.api.service.ImageService;
import loananhshop.api.service.file.FilesStorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("admin/category")
@SessionAttributes("category")
public class CategoryManageController extends AdminBaseController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private Environment environment;
    @Autowired
    private Helper helper;
    @Autowired
    private ImageService imageService;

    @ModelAttribute("category")
    private Category initCategory() {
        return new Category();
    }

    @Autowired
    FilesStorageService storageService;

    /**
     * List
     */
    @GetMapping("/index")
    public String index(Model model) {
        List<Category> categoryList = categoryService.findList();
        model.addAttribute("categoryList", categoryList);
        return "admin/category/index";
    }

    /**
     * New
     */
    @GetMapping("/new")
    public String newCategory(@ModelAttribute("category") Category category, Model model) {
        if (ObjectUtils.isEmpty(category)) {
            category = new Category();
        }
        model.addAttribute("category", category);
        return "admin/category/new";
    }

    @PostMapping("/initImage")
    public String initImage(@ModelAttribute("category") Category category, @RequestParam("imageFile") MultipartFile imageFile, Model model) {

        // save image file
        String fileName = helper.genRandomFileName(imageFile.getOriginalFilename());
        Image image = new Image();
        try {
            storageService.save(imageFile, fileName);
            image.setImageName(fileName);
            image.setImageUrl("/" + environment.getProperty("upload.path") + "/" + fileName);
            category.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCategory(category, model);
    }

    @PostMapping(params = "create")
    public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model,
                                 SessionStatus status) {
        if (result.hasErrors()) {
            return "admin/category/new";
        }

        categoryService.save(category);
        status.setComplete();

        return index(model);
    }

    /**
     * Edit
     */

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/edit";
    }

    @PostMapping(params = "update")
    public String update(@ModelAttribute("category") Category category, Model model, SessionStatus status) {
        categoryService.save(category);
        status.setComplete();
        return "redirect:/admin/category/index";
    }

    @PostMapping(params = "cancel")
    public String cancelNewCategory(@ModelAttribute("category") Category category, Model model, SessionStatus status) {
        Image image = category.getImage();
        if (ObjectUtils.isEmpty(category.getId()) && !ObjectUtils.isEmpty(image)) {
            // delete image by file name
            deleteImageFile(image);
        }
        status.setComplete();

        return "redirect:/admin/category/index";
    }

    @PostMapping(params = "delete")
    public String deleteCategory(@ModelAttribute("category") Category category, Model model, SessionStatus status) {
        try {
            Image image = category.getImage();
            category.setImage(new Image()); // check this again
            categoryService.save(category);
            categoryService.deleteById(category.getId());
            if (!ObjectUtils.isEmpty(image)) {
                // delete image by file name
                deleteImageFile(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        status.setComplete();

        return "redirect:/admin/category/index";
    }

    //========================= PRIVATE METHOD =====================
    private void deleteImageFile(Image image) {
        try {
            // delete image file
            storageService.deleteByFileName(image.getImageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
