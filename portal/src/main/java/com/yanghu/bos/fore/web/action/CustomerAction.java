package com.yanghu.bos.fore.web.action;

import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.FaultAction;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yanghu.crm.domain.Customer;
import com.yanghu.utils.MailUtils;
import com.yanghu.utils.SmsUtils;

import aj.org.objectweb.asm.Type;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午3:03:36 <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

    private Customer customer;
    
    @Autowired
    private JmsTemplate jmsTemplate;//Spring 
    
    @Autowired
    private RedisTemplate<String , String> redisTemplate;
    
    @Override
    public Customer getModel() {
        if(customer == null){
            customer = new Customer();
        }
        return customer;
    }
    //属性注入短信验证码
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    //属性注入邮箱激活码
    private String activeCode;
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
    @Action("customer_sendSMS")
    public String sendSMS(){
        
        final String code = RandomStringUtils.randomNumeric(6);
        ServletActionContext.getRequest().getSession().setAttribute("SMSCode", code);
        System.out.println(code);
        System.out.println(customer.getTelephone());
       //普通发送占用同一线程  需要在发送完毕信息之后才能反馈给客户端  
       /* try {
            SmsUtils.sendSms(customer.getTelephone(), code);
        } catch (ClientException e) {
              System.out.println("发送失败");
            e.printStackTrace();  
        }*/
        //第一个参数  队列名称  第二个参数 发送消息的消息发送者
        jmsTemplate.send("sms",new MessageCreator() {
            //直接new的接口 填写内部内 创建一个消息发送者 并添加了消息内容
            @Override
            public Message createMessage(Session session) throws JMSException {
                 //session 域中 包括了5种message 类型对象的创建方式  这次使用mapmessage 发送消息
                MapMessage message = session.createMapMessage();
                
                message.setString("tel", customer.getTelephone());
                message.setString("code", code);
                return message;
            }
        });
        
        
        return NONE;
    }
    @Action(value ="customer_regist",results={
            @Result(name="success",location="/signup-success.html",type="redirect"),
            @Result(name="error",location="/signup-fail.html",type="redirect")})
    public String register(){
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute("SMSCode");
        if(StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(checkcode) && code.equals(checkcode)){
            //验证成功可以注册
            System.out.println(customer);
            WebClient.create("http://localhost:8180/crm/webService/customerService/save").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(customer);
            System.out.println("注册成功");
             //邮箱激活用户
            String activeCode= RandomStringUtils.randomNumeric(24);
            //激活码保存到redis中
            redisTemplate.opsForValue().set(customer.getTelephone(), activeCode, 1, TimeUnit.DAYS);
            //发送邮件
            String emailBody = "尊敬的"+customer.getUsername() +"用户，感谢您使用本产品，请点击这里<a herf='http://localhost:8280/portal/customer_active.action?telephone="+customer.getTelephone()+"&activeCode="+activeCode+">激活</a>链接进行注册激活";
            MailUtils.sendMail(customer.getEmail(), "注册激活", emailBody);
        }else{
            return ERROR;
        }
        return SUCCESS;
    }
    //邮箱激活 待测试···
    @Action(value = "customer_active",results={
            @Result(name="success",location="/",type="redirect"),
            @Result(name="error",location="/",type="redirect")
    })
    public String active(){
        String code = redisTemplate.opsForValue().get(customer.getTelephone());
        //激活码验证
        if(StringUtils.isNotEmpty(checkcode) && checkcode.equals(code)){
            WebClient.create("http://localhost:8180/crm/webService/customerService/active").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).query("telephone", customer.getTelephone()).put(null);
            return SUCCESS;
        }
        return ERROR;
    }
    @Action(value = "customer_login",results={
            @Result(name="success",location="/index.html",type="redirect"),
            @Result(name="error",location="/",type="redirect"),
            @Result(name="defeat",location="/",type="redirect")
    })
    public String login(){
        
        Customer lgCustomer = WebClient.create("http://localhost:8180/crm/webService/customerService/login").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).query("username", customer.getUsername()).query("password", customer.getPassword()).get(customer.getClass());
        //登录失败 跳转回登录页面
        if(lgCustomer == null){
            return "defeat";
        }
        //账号未激活 
        if(lgCustomer.getType() == null){
            return ERROR;
        }
        ActionContext.getContext().getValueStack().set("user", lgCustomer);
        
        return SUCCESS;
    }
    

}
  
