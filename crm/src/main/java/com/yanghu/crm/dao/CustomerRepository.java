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

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    
    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ? ")
    @Modifying
    void unassignByFixedArea(String fixedAreaId);
    
    @Query("update Customer set fixedAreaId = ?2 where id = ?1")
    @Modifying
    void setFixedAreaId(Long id, String fixedAreaId);
    
    @Query("update Customer set type=1 where telephone = ?")
    @Modifying
    void active(String telephone);

    Customer findByUsernameAndPassword(String username, String password);
}
  
