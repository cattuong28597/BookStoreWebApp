package com.cg.service.voucher;

import com.cg.model.Voucher;
import com.cg.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public class VoucherServiceImpl implements  VoucherService{
    @Autowired
    private VoucherRepository voucherRepository ;

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        return voucherRepository.findById(id);
    }

    @Override
    public Voucher getById(Long id) {
        return voucherRepository.getById(id);
    }

    @Override
    public Voucher save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public void remove(Long id) {
        voucherRepository.deleteById(id);
    }

    @Override
    public List<Voucher> findVoucherByDeletedIsFalse() {
        return voucherRepository.findVoucherByDeletedIsFalse();
    }
}
