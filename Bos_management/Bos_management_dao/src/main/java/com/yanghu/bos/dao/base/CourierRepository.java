package com.yanghu.bos.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yanghu.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:07:23 <br/>       
 * JpaSpecificationExecutor<Courier>  添加了where查询 类似 hibernate 的criteria；
 */
public interface CourierRepository extends JpaRepository<Courier, Long>,JpaSpecificationExecutor<Courier> {
    

    @Modifying
    @Query("update from Courier set deltag = 1 where id = ? ")
    void updateDeltagToDel(long l);

    List<Courier> findByDeltagIsNull();


//    List<Courier> findByFixedAreaIdIsNull();

    
}
  
