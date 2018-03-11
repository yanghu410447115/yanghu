package com.ithiema.web.action;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:UserAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月9日 下午9:06:04 <br/>       
 */

@Controller
@Scope("prototype")//多例模式
//若要返回json数据 需要导入struts2-json的jar包 和 配置为 json-default
@ParentPackage("struts-default")//继承的sturts配置的包结构  
@Namespace("/")
public class UserAction extends ActionSupport implements ModelDriven<User>  {

    private User user;
    @Autowired
    private UserService userService;
    
    @Override //属性驱动 注入user
    public User getModel() {
        if(user == null){
            user = new User();
        }
        return user;
    }
    @Action("user_findById" )//页面访问的路径
    public String findById(){
        
        User user =  userService.findByid(1);
        
        System.out.println(user);
        
        return NONE;
    }

}
  
