package com.yanghu.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory.LocationFilterReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanghu.crm.dao.CustomerRepository;
import com.yanghu.crm.domain.Customer;
import com.yanghu.crm.service.CustomerService;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午4:27:28 <br/>       
 */
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
    public List<Customer> findUnassociationCustomers() {
          
        return customerRepository.findByFixedAreaIdIsNull();
    }


    @Override
    public List<Customer> findAssociationCustomers(String fixedAreaId) {
          
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void assignCustomers2FixedArea(Long[] customerId, String fixedAreaId) {
        
        if(StringUtils.isNotEmpty(fixedAreaId)){
            customerRepository.unassignByFixedArea(fixedAreaId);
        }
        
        if(customerId != null && customerId.length>0){
            for (long id : customerId) {
                customerRepository.setFixedAreaId(id,fixedAreaId);
            }
        }
        
        
        
    }

    @Override
    public void save(Customer customer) {
          
        customerRepository.save(customer);
    }

    @Override
    public void active(String telephone) {
          
        customerRepository.active(telephone);
    }

    @Override
    public Customer dologin(String username, String password) {
          
        return customerRepository.findByUsernameAndPassword(username,password);
    }


}
  
