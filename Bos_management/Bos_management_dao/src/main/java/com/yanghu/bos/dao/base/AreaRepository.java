package com.yanghu.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yanghu.bos.domain.base.Area;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:17:21 <br/>       
 */
public interface AreaRepository extends JpaRepository<Area, Long> {
    @Modifying
    @Query("from Area where shortcode like ?1 or citycode like ?1")
    List<Area> likeQuery(String str);
    
}
  
