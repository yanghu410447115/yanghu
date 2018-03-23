package com.yanghu.web.action.base;

import java.io.IOException;

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

import com.yanghu.bos.domain.base.Area;
import com.yanghu.bos.domain.base.SubArea;
import com.yanghu.bos.service.base.SubAreaService;
import com.yanghu.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:SubAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:39:40 <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
public class SubAreaAction extends CommonAction<SubArea> {
    @Autowired
    private SubAreaService subAreaService;
    
    public SubAreaAction() {
        super(SubArea.class);  
    }
    
    @Action("subarea_pageQuery")
    public String pageQuery() throws IOException{
        
        Pageable pageable = new PageRequest(getPage() - 1, getRows());
        Page<SubArea> page = subAreaService.findAll(pageable);

        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"subareas"});
        
        pageToJson(page, config);
        
        return NONE;
    }
    @Action("subarea_save")
    public String save(){
        
        subAreaService.save(getModel());
        
        return SUCCESS;
    }

}
  
