package com.cg.service.Customer;

import com.cg.model.Customer;
import com.cg.service.IGeneralService;

public interface CustomerService extends IGeneralService<Customer> {

    Boolean existsByUsernameEquals(String slug);

    Boolean existsByUsernameEqualsAndIdIsNot(String slug, long id);

}
