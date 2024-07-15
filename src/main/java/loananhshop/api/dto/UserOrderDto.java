package loananhshop.api.dto;

import loananhshop.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderDto {
    private Long id;


    private int status = 1;

    private String name;

    private String address;

    private String email;

    private String phoneNumber;

    private Long totalAmount;

    private String customerGender;

    private String customerAge;

    private String note;

    private Date createdAt;

    private Date updatedAt;

    private List<ProductIds> productIds;

    private User user;


    public static class ProductIds {
        private Long productId;
        private Long quantity;

        public Long getProductId() {
            return this.productId;
        }

        public Long getQuantity() {
            return this.quantity;
        }
    }

}
