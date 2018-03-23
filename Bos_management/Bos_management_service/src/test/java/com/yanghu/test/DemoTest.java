package com.yanghu.test;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.yanghu.bos.domain.base.Area;
import com.yanghu.crm.domain.Customer;

/**  
 * ClassName:demoTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午5:44:05 <br/>       
 */
public class DemoTest {
    
   public static void main(String[] args) {
    
        Collection<? extends Customer> collection = WebClient.create("http://localhost:8180/crm/webService/customer/findAll").getCollection(Customer.class);
        for (Customer customer : collection) {
            System.out.println(customer);
        }
   }
}
  
