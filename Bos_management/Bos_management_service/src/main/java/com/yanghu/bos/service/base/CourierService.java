package com.yanghu.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.yanghu.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:08:26 <br/>       
 */
public interface CourierService {

    Page<Courier> findAll(Pageable pageable, Specification<Courier> specification);

    void save(Courier courier);

    void delete(String ids);

    List<Courier> findAvalible();

    Courier findById(Long courierId);

//    List<Courier> findByFixedAreaIdIsNull();

}
  
