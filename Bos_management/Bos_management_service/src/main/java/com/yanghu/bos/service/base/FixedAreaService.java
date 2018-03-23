package com.yanghu.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yanghu.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午2:51:07 <br/>       
 */

public interface FixedAreaService {
    Page<FixedArea> findAll(Pageable pageable);

    FixedArea findById(Long id);

    void assignCourierToFixedArea(Long id, Long takeTimeId, Long courierId);

}
  
