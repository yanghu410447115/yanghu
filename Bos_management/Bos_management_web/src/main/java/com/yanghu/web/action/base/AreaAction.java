package com.yanghu.web.action.base;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
import org.springframework.ui.Model;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yanghu.bos.domain.base.Area;
import com.yanghu.bos.domain.base.Standard;
import com.yanghu.bos.service.base.AreaService;
import com.yanghu.utils.MyStringUtils;
import com.yanghu.utils.PinYin4jUtils;
import com.yanghu.web.action.CommonAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:10:33 <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({@Result(name="success",location="/pages/base/area.html",type="redirect")})
public class AreaAction extends CommonAction<Area> {
    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    
    public AreaAction() {
        super(Area.class);  
    }
    //模糊查询快速查找到目标area
    private String q;
    public void setQ(String q) {
        this.q = q;
    }

    @Autowired
    private AreaService areaService;
    //接收传入文件
    private File file;
    public void setFile(File file) {
        this.file = file;
    }
    
    @Action("area_import")
    public String importXSL(){
          try {
            //创建xsl 解析器;
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            //解析指定索引的工作簿  0代表第一个
            HSSFSheet sheet = workbook.getSheetAt(0);
            
            List<Area> list = new ArrayList<>();
            
            for (Row row : sheet) {
                
                if(row.getRowNum() == 0){
                    continue;
                }
                
                Area area = new Area();
                
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                
                province = MyStringUtils.cutLastWord(province);
                city = MyStringUtils.cutLastWord(city);
                district = MyStringUtils.cutLastWord(district);
                
                area.setCity(city);
                area.setDistrict(district);
                area.setProvince(province);
                area.setPostcode(postcode);
                
                String citycode = PinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
                area.setCitycode(citycode);
                
                String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
                String shortcode = PinYin4jUtils.stringArrayToString(headByString);
                area.setShortcode(shortcode);
                
                list.add(area);
            }
            
            areaService.save(list);
            
        } catch (Exception e) {
            e.printStackTrace();  
            
            return NONE;
        }
        return SUCCESS;
    }
    
    @Action("area_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(getPage() - 1, getRows());
        Page<Area> page = areaService.findAll(pageable);

        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"subareas"});
        
        pageToJson(page, config);
        
        return NONE;
    }
    @Action("area_findAll")
    public String findAll() throws IOException{
        List<Area> list;
        //无需查询的成员 优先声明公用
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"subareas"});
        //快速查询功能
        if(StringUtils.isNotEmpty(q)){
            q = "%" +q.toUpperCase() + "%";
            list = areaService.likeQuery(q);
            if(list.size()<1){
                q=null;
                findAll();
            }
            listToJson(list, config);
            return NONE;
        }
        
        Page<Area> page = areaService.findAll(null);
        list = page.getContent();
        
        listToJson(list, config);
        
        return NONE;
    }
}
  
