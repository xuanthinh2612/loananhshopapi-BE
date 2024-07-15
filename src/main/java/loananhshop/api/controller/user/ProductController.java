package loananhshop.api.controller.user;

import loananhshop.api.dto.ResponseError;
import loananhshop.api.model.Product;
import loananhshop.api.service.ProductService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/product" )
@CrossOrigin("*")
@Slf4j
public class ProductController extends UserBaseController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getListProduct() {
        try {
            List<Product> productList = productService.findAvailableList();
            if (ObjectUtils.isEmpty(productList)) {
                throw new Exception("Không tìm thấy sản phẩm nào!");
            }

            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
//            ResponseError error = new ResponseError();
//            error.setMessage(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/show/{id}" )
    public ResponseEntity<Product> showProductDetail(@PathVariable("id" ) Long id, Model model) {

        try {
            Product product = productService.findById(id);
//            model.addAttribute("product", product);
            // show related product by category
//            List<Product> relatedProduct = productService.findAvailableListByCategoryId(product.getCategory().getId()).stream().limit(12L).toList(); // check again
//            model.addAttribute("relatedProduct", relatedProduct);

            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new Product(), HttpStatus.OK);
        }
    }
}
