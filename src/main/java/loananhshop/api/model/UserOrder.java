package loananhshop.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long totalAmount;

    @Column
    private int status = 1;

    @Column
    private String customerName;

    @Column
    private String customerAge;

    @Column
    private String customerGender;

    @Column
    private String customerAddress;

    @Column
    private String customerPhoneNumber;

    @Column
    private String customerEmail;

    @Column(columnDefinition="TEXT")
    private String note;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "user_order_product_list",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
    private Set<ProductOrder> productOrderList;

    @ManyToOne
    private User user;
}
