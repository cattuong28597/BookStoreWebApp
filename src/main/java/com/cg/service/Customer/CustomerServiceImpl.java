package com.cg.service.Customer;

import com.cg.model.Customer;
import com.cg.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer getById(Long id) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void remove(Long id) {
    }

    @Override
    public Boolean existsByUsernameEquals(String slug) {
        return customerRepository.existsByUsernameEquals(slug);
    }

    @Override
    public Boolean existsByUsernameEqualsAndIdIsNot(String slug, long id) {
        return customerRepository.existsByUsernameEqualsAndIdIsNot(slug, id);
    }
}
