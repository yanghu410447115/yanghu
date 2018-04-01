package com.yanghu.crm.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.yanghu.crm.domain.Customer;

/**  
 * ClassName:CustomerDao <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午4:59:55 <br/>       
 */

/**支持多的条件查询  额外继承  JpaSpecificationExecutor
 * JpaRepository  spring data jpa 帮助管理 dao层 自动生成实现类实现查询方法
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //jpa 支持语言结构化自动查询
    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);
    
    
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ? ")
    @Modifying
    void unassignByFixedArea(String fixedAreaId);
    //自定义语句查询，类似hibernate的语句查询   ？后面可以接数字表示指定第几个参数放在这个位置
    //update 更新语句 需要加入  modifying 的注释
    @Query("update Customer set fixedAreaId = ?2 where id = ?1")
    @Modifying
    void setFixedAreaId(Long id, String fixedAreaId);
    
    @Query("update Customer set type=1 where telephone = ?")
    @Modifying
    void active(String telephone);

    Customer findByUsernameAndPassword(String username, String password);
    //select 的语句 直接使用就可以了
    @Query("select fixedAreaId from Customer where address = ? ")
    String findFixedAreaIdByAddress(String address);
}
  
