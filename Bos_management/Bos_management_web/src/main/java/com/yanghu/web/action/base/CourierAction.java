package com.yanghu.web.action.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.dom4j.CDATA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.parser.deserializer.StringFieldDeserializer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yanghu.bos.domain.base.Courier;
import com.yanghu.bos.domain.base.Standard;
import com.yanghu.bos.service.base.CourierService;
import com.yanghu.web.action.CommonAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:00:14 <br/>       
 */
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
@Results({@Result(name="success",location="/pages/base/courier.html",type="redirect")})
public class CourierAction  extends CommonAction<Courier> {
    
    
    public CourierAction() {
        super(Courier.class);  
    }
    @Autowired
    private CourierService courierService;
    
    //传入的需要作废的id
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    
    @Action("courier_save")
    public String save(){
        
       courierService.save(getModel());
        
        return SUCCESS;
    }
    
    @Action("courier_pageQuery")
    public  String pageQuery() throws IOException{
        //类似 hibernate 的criteria；
        Specification<Courier> specification = new Specification<Courier>() {
               
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
               
                String company = getModel().getCompany();
                String courierNum = getModel().getCourierNum();
                String type = getModel().getType();
                Standard standard = getModel().getStandard();
                //需要将所有条件放在一个集合里面 最后加载为一个整体的条件
                List<Predicate> list = new ArrayList<>();
                     //判断加入条件  
                if(StringUtils.isNotEmpty(company)){
                    Predicate predicate = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(predicate);
                }
                
                if(StringUtils.isNotEmpty(courierNum)){
                    Predicate predicate = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(predicate);
                }
                if(StringUtils.isNotEmpty(type)){
                    Predicate predicate = cb.equal(root.get("type").as(String.class), type);
                    list.add(predicate);
                }
                if(standard != null){
                    String name = standard.getName();
                    if(StringUtils.isNotEmpty(name)){
                        //应该理解为级联查询   ，join 获得关联的表的数据
                        Join<Object, Object> join = root.join("standard");
                        Predicate predicate = cb.equal(join.get("name").as(String.class), name);
                        list.add(predicate);
                    }
                    
                }
                
                if(list.size()==0){
                    return null;
                }
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                Predicate predicate = cb.and(arr);
                
                return predicate;
            }};
        
        Pageable pageable = new PageRequest(getPage()-1, getRows());
        Page<Courier> page = courierService.findAll(pageable,specification);
        
        //配置忽略项，不需要成员直接忽略掉不加载到json文件中，提高查询效率
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas"});//指点不用加载的成员
        
        pageToJson(page, jsonConfig);
        
        return NONE;
    }
    @Action("courier_delete")
    public String delete(){
        System.out.println(ids);
        
        courierService.delete(ids);
        
        return SUCCESS;
        
    }
    
//    @Action("courier_FixedAreaIdIsNull")
//    public String findByFixedAreaIdIsNull() throws IOException{
//        
//        List<Courier> list = courierService.findByFixedAreaIdIsNull();
//        
//        JsonConfig config = new JsonConfig();
//        config.setExcludes(new String[]{"fixedAreas"});
//        listToJson(list, config);
//        return NONE;
//    }
    
    @Action("courier_findAll")
    public String findAll() throws IOException{
        
        Page<Courier>  page = courierService.findAll(null, null);
        List<Courier> list = page.getContent();
        
        JsonConfig config  = new JsonConfig();
        config.setExcludes(new String[]{"fixedAreas"});
        listToJson(list, config);
        
        return NONE;
    }
    //查询在职的员工 
    @Action("courier_findAvalible")
    public String findAvalible() throws IOException{
        
       List<Courier> list =  courierService.findAvalible();
       JsonConfig config  = new JsonConfig();
       config.setExcludes(new String[]{"fixedAreas"});
       listToJson(list, config);
        
        return NONE;
    }
    
}
  
