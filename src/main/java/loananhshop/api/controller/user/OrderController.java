package loananhshop.api.controller.user;

import loananhshop.api.common.CommonConst;
import loananhshop.api.dto.UserOrderDto;
import loananhshop.api.mapper.UserOrderMapper;
import loananhshop.api.model.Product;
import loananhshop.api.model.ProductOrder;
import loananhshop.api.model.UserOrder;
import loananhshop.api.service.ProductService;
import loananhshop.api.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class OrderController extends UserBaseController {

    @Autowired
    ProductService productService;

    @Autowired
    UserOrderService userOrderService;

//    @GetMapping("/new/{id}")
//    public String newOrder(@PathVariable("id") Long id, Model model){
//        Product product = productService.findById(id);
//
//        if (ObjectUtils.isEmpty(product) || isNotValidProduct(product)) {
//            return "redirect:/";
//        }
//        UserOrder order = new UserOrder();
//        order.setProduct(product);
//        model.addAttribute("product", product);
//        model.addAttribute("order", order);
//        return "user/order/new";
//    }

    @PostMapping("/create")
    public String createOrder(@RequestBody UserOrderDto orderDto) {

        UserOrder order = UserOrderMapper.mapToEntity(orderDto);

        // handle list product_order
        Set<ProductOrder> productOrders = new HashSet<>();


        for (UserOrderDto.ProductIds productIdItem : orderDto.getProductIds()) {
            Product product = productService.findById(productIdItem.getProductId());
            if (ObjectUtils.isEmpty(product) || isNotValidProduct(product)) {
                return "error";
            } else {
                ProductOrder productOrder = new ProductOrder();
                productOrder.setProduct(product);
                productOrder.setQuantity(productIdItem.getQuantity());
                productOrder.setTotal(productIdItem.getQuantity() * product.getCurrentPrice());
                productOrders.add(productOrder);
            }
        }


        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        order.setStatus(CommonConst.ORDERED);
        order.setProductOrderList(productOrders);
        order.setUser(getCurrentLoggedInUser());
        userOrderService.save(order);

        return "success";
    }

//    @GetMapping("/histories")
//    public String orderHistory(Model model){
//        // logic here after get current user
//        // pending
//        return "/user/order/histories";
//    }

    private boolean isNotValidProduct(Product product) {
        return product.getStatus() != CommonConst.ProductStatus.available.code()
                && product.getStatus() != CommonConst.ProductStatus.sale.code();
    }

}
