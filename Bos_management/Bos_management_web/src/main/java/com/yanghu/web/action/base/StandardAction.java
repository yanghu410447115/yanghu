package com.yanghu.web.action.base;


import java.io.IOException;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yanghu.bos.domain.base.Standard;
import com.yanghu.bos.service.base.StandardService;
import com.yanghu.web.action.CommonAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.groovy.GJson;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午8:20:45 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Results({@Result(name="success",location="/pages/base/standard.html",type="redirect")})
public class StandardAction extends CommonAction<Standard> {

    public StandardAction() {
        super(Standard.class);  
    }

    @Autowired
    private StandardService standardService;
    
    @Action("standard_save")
    public String save(){
        
      standardService.save(getModel());
        
        return SUCCESS;
    }
    
    @Action("standard_findByPage")
    public String findByPage() throws IOException{
        
        Pageable pageable = new PageRequest(getPage()-1, getRows());
        
        Page<Standard> page = (Page<Standard>) standardService.findAll(pageable);
        
        pageToJson(page, null);
        
        return NONE;
    }
    
    @Action("standard_findAll")
    public String findAll() throws IOException{
        
        Page<Standard> page = standardService.findAll(null);
        List<Standard> list = page.getContent();
        String json = JSONArray.fromObject(list).toString();
        writeJson(json);
        
        return NONE;
    }
}
  
