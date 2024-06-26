//package loananhshop.api.exception;
//
//import loananhshop.api.model.Category;
//import loananhshop.api.service.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.servlet.resource.NoResourceFoundException;
//
//import java.util.List;
//
//@ControllerAdvice
//public class GlobalExceptionHandler2 {
//
//    @Autowired
//    CategoryService categoryService;
//    @ModelAttribute("categoryList" )
//    private List<Category> initCategoryList() {
//        return categoryService.findList();
//    }
//
//    @ExceptionHandler(NumberFormatException.class)
//    public String handleNumberFormatException(NumberFormatException ex, Model model) {
//        ex.printStackTrace();
//        model.addAttribute("error", "Invalid ID format");
//        return "shared/errorPage"; // return the name of your custom error page
//    }
//    @ExceptionHandler(NoResourceFoundException.class)
//    public String handleNoResourceFoundException(NoResourceFoundException ex, Model model) {
//        ex.printStackTrace();
//        model.addAttribute("error", "Không Tìm Thấy Đường Dẫn");
//        return "shared/errorPage"; // return the name of your custom error page
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public String handleRuntimeException(RuntimeException ex, Model model) {
//        model.addAttribute("error", "Đã xảy ra lỗi trong hệ thống vui lòng liên hệ admin để kiểm tra.");
//        ex.printStackTrace();
//        return "shared/errorPage"; // return the name of your custom error page
//    }
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public String handleRuntimeException(HttpRequestMethodNotSupportedException ex, Model model) {
//        ex.printStackTrace();
//        model.addAttribute("error", "Đã xảy ra lỗi trong hệ thống vui lòng liên hệ admin để kiểm tra.");
//        return "shared/errorPage"; // return the name of your custom error page
//    }
//    @ExceptionHandler(Exception.class)
//    public String handleGlobalException(Exception ex, Model model) {
//        ex.printStackTrace();
//        model.addAttribute("error", "Đã xảy ra lỗi trong hệ thống vui lòng liên hệ admin để kiểm tra.");
//        return "shared/errorPage"; // return the name of your custom error page
//    }
//}
