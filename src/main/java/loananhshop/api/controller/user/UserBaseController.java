package loananhshop.api.controller.user;

import loananhshop.api.controller.BaseController;
import loananhshop.api.model.Category;
import loananhshop.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public class UserBaseController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categoryList" )
    private List<Category> initCategoryList() {
        return getListCategory();
    }

    protected List<Category> getListCategory() {
        return categoryService.findList();
    }

}
