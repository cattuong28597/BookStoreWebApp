package com.cg.service.cartdetail;

import com.cg.model.CartDetail;
import com.cg.repository.CartDetailRepository;
import com.cg.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public List<CartDetail> findAll() {
        return cartDetailRepository.findAll();
    }

    @Override
    public Optional<CartDetail> findById(Long id) {
        return cartDetailRepository.findById(id);
    }

    @Override
    public CartDetail getById(Long id) {
        return cartDetailRepository.getById(id);
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public void remove(Long id) {
        cartDetailRepository.deleteById(id);
    }

    @Override
    public List<CartDetail> findCartDetailByCartAndDeletedIsFalse(Long id_cart) {
        return cartDetailRepository.findCartDetailByCartAndDeletedIsFalse(cartRepository.getById(id_cart));
    }
}
