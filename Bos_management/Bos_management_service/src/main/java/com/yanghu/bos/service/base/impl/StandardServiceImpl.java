package com.yanghu.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanghu.bos.dao.base.StandardRepository;
import com.yanghu.bos.domain.base.Standard;
import com.yanghu.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午8:29:55 <br/>       
 */

@Service
@Transactional
public class StandardServiceImpl implements StandardService {
    
    @Autowired
    private  StandardRepository standardRepository;

    @Override
    public void save(Standard standard) {
          
        standardRepository.save(standard);
    }

    @Override
    public Page<Standard> findAll(Pageable pageable) {
          
        return standardRepository.findAll(pageable);
    }


    
}
  
