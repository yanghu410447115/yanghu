package com.yanghu.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.yanghu.bos.domain.base.FixedArea;
import com.yanghu.bos.service.base.FixedAreaService;
import com.yanghu.crm.domain.Customer;
import com.yanghu.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午2:46:25 <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({@Result(name = "success",location="/pages/base/fixed_area.html",type="redirect")})
public class FixedAreaAction extends CommonAction<FixedArea> {
     
    @Autowired
    private FixedAreaService fixedAreaService;
    
    private Long[] customerIds;
    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }
    
    //注入班次id
    private Long takeTimeId;
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    //注入快递员id
    private Long courierId;
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    
    
    public FixedAreaAction() {
        super(FixedArea.class);  
    }
    
    @Action("fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException{
        
        Pageable pageable = new PageRequest(getPage()-1, getRows());
        Page<FixedArea> page = fixedAreaService.findAll(pageable);
        
        JsonConfig config =  new JsonConfig();
        config.setExcludes(new String[]{"subareas","couriers"});
        
        pageToJson(page, config);
        
        return NONE;
    }
    @Action("fixedAreaAction_findUnassociationCustomers")
    public String findUnassociationCustomers() throws IOException{
        
        List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/crm/webService/customerService/findUnassociationCustomers").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        
        listToJson(list, null);
        
        return NONE;
    }
    @Action("fixedAreaAction_findAssociationCustomers")
    public String findAssociationCustomers() throws IOException{
        
        List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/crm/webService/customerService/findAssociationCustomers").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).query("fixedAreaId", getModel().getId()).getCollection(Customer.class);
        
        listToJson(list, null);
        
        return NONE;
    }
    
    @Action("fixedAreaAction_assignCustomers2FixedArea")
    public String assignCustomerToFixedArea(){
        if(customerIds == null){
            customerIds = new Long[]{(long) -1};
        }
        WebClient.create("http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).query("customerIds", customerIds).query("fixedAreaId", getModel().getId()).put(null);
        
        return SUCCESS;
    }
    
    @Action("fixedAreaAction_assignCourierToFixedArea")
    public String assignCourierToFixedArea(){
        
        fixedAreaService.assignCourierToFixedArea(getModel().getId(),takeTimeId,courierId);
        
        /*Courier courier = courierService.findById(courierId);
        TakeTime takeTime = takeTimeService.findById(takeTimeId);
        FixedArea fixedArea =fixedAreaService.findById(getModel().getId());
        courier.setTakeTime(takeTime);
        fixedArea.getCouriers().add(courier);*/
        return SUCCESS;
    }
    
}
  
