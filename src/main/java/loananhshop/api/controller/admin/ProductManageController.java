package loananhshop.api.controller.admin;

import jakarta.validation.Valid;
import loananhshop.api.common.CommonConst;
import loananhshop.api.controller.helper.Helper;
import loananhshop.api.model.Image;
import loananhshop.api.model.Product;
import loananhshop.api.model.SubContent;
import loananhshop.api.repository.ImageRepository;
import loananhshop.api.service.CategoryService;
import loananhshop.api.service.ImageService;
import loananhshop.api.service.ProductService;
import loananhshop.api.service.SubContentService;
import loananhshop.api.service.file.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product/" )
@CrossOrigin("*")
public class ProductManageController extends AdminBaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Environment environment;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SubContentService subContentService;

    @Autowired
    private Helper helper;

//    @ModelAttribute("categoryList" )
//    public List<Category> initListCategory() {
//        return categoryService.findList();
//    }
//
//    @ModelAttribute("product" )
//    private Product initEntity() {
//        return new Product();
//    }

    @Autowired
    FilesStorageService storageService;
    @Autowired
    private ImageRepository imageRepository;

    /**
     * List
     **/

    @GetMapping("/list" )
    public ResponseEntity<List<Product>> findAllListProduct() {
        List<Product> productList = productService.findList();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    /**
     * create
     **/
    @PostMapping("/create" )
    public String createProduct(@Valid  @RequestBody Product product, BindingResult result) {
        // check valid model
        if (result.hasErrors()) {
            return "error";
        }
        try {
            productService.save(product);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    @PutMapping("/update" )
    public String editProduct(@Valid  @RequestBody Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }
        productService.save(product);
        return "success";
    }


    @DeleteMapping("/delete/{id}" )
    public String deleteProduct(@PathVariable("id" ) Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return "error";
        }
        List<SubContent> subContentList = product.getSubContentList();
        for (SubContent subContent : subContentList) {
            // delete image file
            deleteSubContentImageFile(subContent);
        }
        productService.deleteById(id);
        return "success";
    }

    @PutMapping("/public/{id}" )
    public String publicProduct(@PathVariable("id" ) Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return "error";
        }
        product.setStatus(CommonConst.ProductStatus.available.code());
        productService.save(product);
        return "success";
    }

    @PutMapping("/onSale/{id}" )
    public String onSaleProduct(@PathVariable("id" ) Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return "error";
        }
        product.setStatus(CommonConst.ProductStatus.sale.code());
        productService.save(product);
        return "success";
    }

    @PutMapping("/offProduct/{id}" )
    public String offProduct(@PathVariable("id" ) Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return "error";
        }
        product.setStatus(CommonConst.ProductStatus.banded.code());
        productService.save(product);
        return "success";
    }

    @PutMapping("/setSoldOut/{id}" )
    public String setSoldOut(@PathVariable("id" ) Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return "error";
        }
        product.setStatus(CommonConst.ProductStatus.soldOut.code());
        productService.save(product);
        return "success";
    }

//
//    @PostMapping("/initImage" )
//    public String initImage(@ModelAttribute("product" ) Product product, @RequestParam("imageFiles" ) MultipartFile[] imageFiles, Model model) {
//        List<SubContent> subContentList = product.getSubContentList();
//        if (ObjectUtils.isEmpty(subContentList)) {
//            subContentList = new ArrayList<>();
//        }
//
//        // save image file
//        for (MultipartFile file : imageFiles) {
//
//            SubContent subContent = new SubContent();
//            String fileName = helper.genRandomFileName(file.getOriginalFilename());
//
//            Image image = new Image();
//            try {
//                storageService.save(file, fileName);
//                image.setImageName(fileName);
//                image.setImageUrl("/" + environment.getProperty("upload.path" ) + "/" + fileName);
//                subContent.setImage(image);
//                subContentList.add(subContent);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        product.setSubContentList(subContentList);
//        return newProduct(product, model);
//    }
//
//    @PostMapping("/cancel" )
//    public String cancelInitProduct(@ModelAttribute("product" ) Product product, Model model, SessionStatus status) {
//        List<SubContent> subContentList = product.getSubContentList();
//        if (!ObjectUtils.isEmpty(subContentList)) {
//            for (SubContent subContent : subContentList) {
//                // delete image by file name
//                deleteSubContentImageFile(subContent);
//            }
//        }
//        status.setComplete();
//        return index(model);
//    }
//
//    @PostMapping("/removeNewImage/{subContentIndex}" )
//    public String removeNewImage(@ModelAttribute("product" ) Product product, @PathVariable("subContentIndex" ) int subContentIndex, Model model) {
//        try {
//            SubContent subContent = product.getSubContentList().get(subContentIndex);
//            // delete image file
//            deleteSubContentImageFile(subContent);
//            subContent.setImage(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return newProduct(product, model);
//    }
//
//    @PostMapping("/removeSubContent/{subContentIndex}" )
//    public String removeSubContent(@ModelAttribute("product" ) Product product, @PathVariable("subContentIndex" ) int subContentIndex, Model model) {
//        try {
//            SubContent subContent = product.getSubContentList().get(subContentIndex);
//            // delete image file
//            deleteSubContentImageFile(subContent);
//            product.getSubContentList().remove(subContent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return newProduct(product, model);
//    }
//
//    @PostMapping("/changeImage/{subContentIndex}" )
//    public String changeImage(@ModelAttribute("product" ) Product product, @RequestParam("imageFile" ) MultipartFile imageFile, @PathVariable("subContentIndex" ) int subContentIndex, Model model) {
//        try {
//            SubContent subContent = product.getSubContentList().get(subContentIndex);
//            Image image = subContent.getImage();
//            if (ObjectUtils.isEmpty(image)) {
//                image = new Image();
//                subContent.setImage(image);
//            }
//
//            String fileName = helper.genRandomFileName(imageFile.getOriginalFilename());
//
//            storageService.save(imageFile, fileName);
//            image.setImageName(fileName);
//            image.setImageUrl("/" + environment.getProperty("upload.path" ) + "/" + fileName);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return newProduct(product, model);
//    }
//
//    /**
//     * Edit
//     **/
//
//    @GetMapping("/edit/{id}" )
//    public String editProduct(@PathVariable("id" ) Long id, Model model) {
//        Product product = productService.findById(id);
//        if (product == null) {
//            return index(model);
//        }
//        model.addAttribute("product", product);
//        return "admin/product/edit";
//    }
//

//    @PostMapping("/addImage" )
//    public String addImage(@ModelAttribute("product" ) Product product, @RequestParam("imageFile" ) MultipartFile imageFile, Model model) {
//
//        SubContent subContent = new SubContent();
//        product.getSubContentList().add(subContent);
//
//        String fileName = helper.genRandomFileName(imageFile.getOriginalFilename());
//        Image image = new Image();
//
//        try {
//            storageService.save(imageFile, fileName);
//            image.setImageName(fileName);
//            image.setImageUrl("/" + environment.getProperty("upload.path" ) + "/" + fileName);
//            subContent.setImage(image);
//            productService.save(product);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return editProduct(product.getId(), model);
//
//    }
//
//    @PostMapping("/replaceImage/{subContentIndex}" )
//    public String replaceImage(@ModelAttribute("product" ) Product product, @PathVariable("subContentIndex" ) int subContentIndex, @RequestParam("newImageFile" ) MultipartFile newImageFile, Model model) {
//
//        assert product.getSubContentList() != null;
//        SubContent subContent = product.getSubContentList().get(subContentIndex);
//
//        String fileName = helper.genRandomFileName(newImageFile.getOriginalFilename());
//        Image image = new Image();
//
//        try {
//            storageService.save(newImageFile, fileName);
//            image.setImageName(fileName);
//            image.setImageUrl("/" + environment.getProperty("upload.path" ) + "/" + fileName);
//            subContent.setImage(image);
//            productService.save(product);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return editProduct(product.getId(), model);
//
//    }
//
//    @PostMapping("/deleteImage/{subContentIndex}" )
//    public String deleteImage(@ModelAttribute("product" ) Product product, @PathVariable("subContentIndex" ) int subContentIndex, Model model) {
//        try {
//            assert product.getSubContentList() != null;
//            SubContent subContent = product.getSubContentList().get(subContentIndex);
//            Image image = subContent.getImage();
//            // delete image file
//            deleteSubContentImageFile(subContent);
//            subContent.setImage(null);
//            subContentService.save(subContent);
//
//            assert image != null;
//            if (!ObjectUtils.isEmpty(image.getId())) {
//                imageService.deleteById(image.getId());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return editProduct(product.getId(), model);
//
//    }
//
//    @PostMapping("/deleteSubContent/{subContentIndex}" )
//    public String deleteSubContent(@ModelAttribute("product" ) Product product, @PathVariable("subContentIndex" ) int subContentIndex, Model model) {
//        try {
//            assert product.getSubContentList() != null;
//            SubContent subContent = product.getSubContentList().get(subContentIndex);
//            product.getSubContentList().remove(subContentIndex);
//            productService.save(product);
//            deleteSubContentImageFile(subContent);
//            if (!ObjectUtils.isEmpty(subContent.getId())) {
//                subContentService.deleteById(subContent.getId());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return editProduct(product.getId(), model);
//
//    }
//
//    @PostMapping("/setImageAsAvatar/{subContentIndex}" )
//    public String setImageAsAvatar(@ModelAttribute("product" ) Product product, @PathVariable("subContentIndex" ) int subContentIndex, Model model) {
//        try {
//            assert product.getSubContentList() != null;
//            SubContent subContent = product.getSubContentList().get(subContentIndex);
//            Image image = subContent.getImage();
//            assert image != null;
//            image.setAvatar(true);
//            imageService.save(image);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return editProduct(product.getId(), model);
//
//    }
//
//    @PostMapping("/removeImageFromAvatar/{subContentIndex}" )
//    public String removeImageFromAvatar(@ModelAttribute("product" ) Product product, @PathVariable("subContentIndex" ) int subContentIndex, Model model) {
//        try {
//            assert product.getSubContentList() != null;
//            SubContent subContent = product.getSubContentList().get(subContentIndex);
//            Image image = subContent.getImage();
//            assert image != null;
//            image.setAvatar(false);
//            imageService.save(image);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return editProduct(product.getId(), model);
//
//    }
//
//    /**
//     * Detail
//     **/
//
//    @GetMapping("/show/{id}" )
//    public String showDetail(@PathVariable("id" ) Long id, Model model) {
//        Product product = productService.findById(id);
//        if (product == null) {
//            return index(model);
//        }
//        model.addAttribute("product", product);
//        return "admin/product/show";
//    }

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
