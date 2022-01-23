package com.cg.service.bill;

import com.cg.model.Bill;
import com.cg.model.Order;
import com.cg.model.dto.IBillDTO;
import com.cg.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class BillServiceImpl implements BillService{
    @Autowired
    private BillRepository billRepository ;

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return billRepository.findById(id);
    }

    @Override
    public Bill getById(Long id) {
        return billRepository.getById(id);
    }

    @Override
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public void remove(Long id) {
       billRepository.deleteById(id);
    }

    @Override
    public List<Bill> findAllByDeletedIsFalse() {
        return billRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Optional<Bill> findByOrder(Order order) {
        return billRepository.findByOrder(order);
    }

    @Override
    public List<Bill>  findAllByDeletedIsFalseAndPaidIsTrueAndCreatedAtIsBetween(Date startDate, Date endDate){
        return billRepository.findAllByDeletedIsFalseAndPaidIsTrueAndCreatedAtIsBetween(startDate,endDate) ;
    }

    @Override
    public List<IBillDTO> findIBillDTOWithCreateAt(Date startDate, Date endDate) {
        return billRepository.findIBillDTOWithCreateAt(startDate, endDate) ;
    };

}
