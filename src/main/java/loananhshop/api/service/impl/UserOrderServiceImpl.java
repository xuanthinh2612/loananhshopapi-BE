package loananhshop.api.service.impl;

import loananhshop.api.model.UserOrder;
import loananhshop.api.repository.UserOrderRepository;
import loananhshop.api.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private UserOrderRepository userOrderRepository;

    @Override
    public void save(UserOrder order) {
        userOrderRepository.save(order);
    }

    @Override
    public UserOrder findById(Long id) {
        return userOrderRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        userOrderRepository.deleteById(id);
    }

    @Override
    public List<UserOrder> findAll() {
        return userOrderRepository.findAll();
    }

    @Override
    public List<UserOrder> findAllByStatus(int status) {
        return userOrderRepository.findAllByStatus(status);
    }
}
