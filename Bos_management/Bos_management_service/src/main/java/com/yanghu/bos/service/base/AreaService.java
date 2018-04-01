package com.yanghu.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yanghu.bos.domain.base.Area;

/**  
 * ClassName:ArseService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:16:00 <br/>       
 */
public interface AreaService {

    void save(List<Area> list);

    Page<Area> findAll(Pageable pageable);

    List<Area> likeQuery(String str);

    Area saveTransientArea(Area area);


}
  
