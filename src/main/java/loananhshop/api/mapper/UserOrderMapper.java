package loananhshop.api.mapper;

import loananhshop.api.dto.UserOrderDto;
import loananhshop.api.model.UserOrder;

public class UserOrderMapper {

    public static UserOrder mapToEntity(UserOrderDto orderDto) {
        UserOrder userOrder = new UserOrder();
//        userOrder.setId(orderDto.getId()); // ?
          userOrder.setCustomerName(orderDto.getName());
          userOrder.setCustomerAddress(orderDto.getAddress());
          userOrder.setCustomerPhoneNumber(orderDto.getPhoneNumber());
          userOrder.setTotalAmount(orderDto.getTotalAmount());
          return  userOrder;
    }
}
