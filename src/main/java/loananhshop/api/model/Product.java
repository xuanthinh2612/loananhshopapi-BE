package loananhshop.api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column
    @NotEmpty(message = "Tên sản phẩm không được để trống" )
    private String name;

    @Column
    @NotNull(message = "Giá sản phẩm không được để trống" )
    private Long currentPrice;

    @Column
    @NotEmpty(message = "Đơn vị sản phẩm không được để trống" )
    private String unit;

    @Column
    @Nullable
    private Long oldPrice;

    @Column
    @NotEmpty(message = "Hãy mô tả chi tiết về sản phẩm" )
    private String description;

    @Column(columnDefinition = "TEXT" )
    private String subDescription_1;

    @Column(columnDefinition = "TEXT" )
    private String subDescription_2;

    @Column(columnDefinition = "TEXT" )
    private String subDescription_3;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private int status = 1;

    @Column
    private Long remainAmount;

    @ManyToOne
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "products_sub_content_list",
            joinColumns = @JoinColumn(name = "product_id" ),
            inverseJoinColumns = @JoinColumn(name = "sub_content_list_id" )
    )
    private List<SubContent> subContentList;

    public Image getAvatar() {
        Image defaultAvatar = null;
        Image avatar = null;
        for (SubContent subContent : subContentList) {
            // check avatar image and return image
            Image image = subContent.getImage();
            if (!ObjectUtils.isEmpty(image)) {
                if (ObjectUtils.isEmpty(defaultAvatar)) {
                    defaultAvatar = image;
                }
                if (image.isAvatar()) {
                    avatar = image;
                    break;
                }
            }
        }

        return ObjectUtils.isEmpty(avatar) ? defaultAvatar : avatar;
    }

}
