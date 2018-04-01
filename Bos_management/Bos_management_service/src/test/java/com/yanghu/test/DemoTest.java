package com.yanghu.test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.yanghu.bos.domain.base.Area;
import com.yanghu.crm.domain.Customer;


/**  
 * ClassName:demoTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午5:44:05 <br/>       
 */
public class DemoTest {
    
  /* public static void main(String[] args) {
    
        Collection<? extends Customer> collection = WebClient.create("http://localhost:8180/crm/webService/customer/findAll").getCollection(Customer.class);
        for (Customer customer : collection) {
            System.out.println(customer);
        }
   }*/
   
    
    
    //约数倍数卡牌
   @Test
   public void test01(){
       int[] num = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
       
       List<Integer> list = new ArrayList<>();
       for(int i = 0;i<num.length;i++){
           int n = 1;
           for(int j =0;j<num.length;j++){
               if(i==j){
                   continue;
               }
               if(num[i]>num[j] && num[i]%num[j]==0 ){
                   
                   n++;
               }
               if(num[i]<=num[j] && num[j]%num[i]==0 ){
                   n++;
               }
           }
           if(n%2 ==1){
               list.add(num[i]);
           }
           
       }
       System.out.println(list);
   }
   
   
   @Test
   public void test02(){
       
       System.out.println(9%18);
   }
}
  
