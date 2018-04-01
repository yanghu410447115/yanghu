package com.yanghu.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanghu.bos.dao.base.CourierRepository;
import com.yanghu.bos.domain.base.Courier;
import com.yanghu.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:08:48 <br/>       
 */
@Service("courierService")
@Transactional
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;
    
    

    @Override
    public void save(Courier courier) {
          
        courierRepository.save(courier);
        
    }
    
    
    @Override
    public void delete(String ids) {
        if(StringUtils.isEmpty(ids)){
            return ;
        }
        String[] strings = ids.split(",");
        for (String id : strings) {
            courierRepository.updateDeltagToDel(Long.parseLong(id));
        }
        
    }

    @Override
    public Page<Courier> findAll(Pageable pageable, Specification<Courier> spec) {
          
        
        return courierRepository.findAll(spec, pageable);
    }

    @Override
    public List<Courier> findAvalible() {
          
        return courierRepository.findByDeltagIsNull();
    }

    @Override
    public Courier findById(Long courierId) {
          
        return courierRepository.findOne(courierId);
    }

//    @Override
//    public List<Courier> findByFixedAreaIdIsNull() {
//          
//        return courierRepository.findByFixedAreaIdIsNull();
//    }
}
  
