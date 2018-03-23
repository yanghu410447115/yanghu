package com.yanghu.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanghu.bos.dao.base.CourierRepository;
import com.yanghu.bos.dao.base.FixedAreaRepository;
import com.yanghu.bos.dao.base.TakeTimeRepository;
import com.yanghu.bos.domain.base.Courier;
import com.yanghu.bos.domain.base.FixedArea;
import com.yanghu.bos.domain.base.TakeTime;
import com.yanghu.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午2:51:41 <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    @Autowired
    private CourierRepository courierRepository;

    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
          
        return fixedAreaRepository.findAll(pageable);
    }

    @Override
    public FixedArea findById(Long id) {
          
        return fixedAreaRepository.findOne(id);
    }
    
    
    //使用hibernate的缓存直接存入 需要在一个事物类完成所以所有操作在这个事物任务中
    @Override
    public void assignCourierToFixedArea(Long id, Long takeTimeId, Long courierId) {
          
        FixedArea fixedArea = findById(id);
        
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        
        Courier courier = courierRepository.findOne(courierId);
        
        courier.setTakeTime(takeTime);
        
        fixedArea.getCouriers().add(courier);
        
    }
    
    
    
}
  
