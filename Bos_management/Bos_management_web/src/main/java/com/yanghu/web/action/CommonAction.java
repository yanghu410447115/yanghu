package com.yanghu.web.action;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CommentAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:36:56 <br/>       
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {
    
     private Class<T> clazz;
     private T model;
    
    public CommonAction(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T getModel() {
        if(model == null){
            try {
                model = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();  
            }
        }
        return model;
    }
    
    private int page;
    private int rows;
    
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public int getPage() {
        return page;
    }
    public int getRows() {
        return rows;
    }
    
    public void pageToJson(Page<T> page,JsonConfig config) throws IOException{
        
        //当前页面的条数  
        List<T> content = page.getContent();
        //总数据条数
        long total = page.getTotalElements();
        
        Map<String, Object> map = new HashMap<>();
        
        map.put("total", total);
        map.put("rows", content);
        
        String json;
        if(config == null){
           json = JSONObject.fromObject(map).toString();
        }else{
           json = JSONObject.fromObject(map,config).toString();
        }
        writeJson(json);
    }
    
    public void writeJson(String json) throws IOException{
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.close();
        /*response.getWriter().write(json);  */
    }
    
    public void listToJson(List list,JsonConfig config) throws IOException{
        String json;
        if(config == null){
            json = JSONArray.fromObject(list).toString();
         }else{
            json = JSONArray.fromObject(list,config).toString();
         }
        
        writeJson(json);
    }
}
  
