package com.yanghu.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.yanghu.crm.domain.Customer;

/**  
 * ClassName:CoustomerService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午4:26:24 <br/>       
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CustomerService {
    
    @GET
    @Path("/findAll")
     List<Customer> findAll();
    
    @GET
    @Path("/findUnassociationCustomers")
    List<Customer> findUnassociationCustomers();
    @GET
    @Path("/findAssociationCustomers")
    List<Customer> findAssociationCustomers(@QueryParam("fixedAreaId") String fixedAreaId);
    
    @PUT
    @Path("/assignCustomers2FixedArea")
    void assignCustomers2FixedArea(@QueryParam("customerIds") Long[] customerIds,@QueryParam("fixedAreaId")String fixedAreaId);
    @POST
    @Path("/save")
    void save(Customer customer);
    
    @PUT
    @Path("/active")
    void active(@QueryParam("telephone")String telephone);
    
    @GET
    @Path("/dologin")
    Customer dologin(@QueryParam("username")String username,@QueryParam("password")String password);
}
  
