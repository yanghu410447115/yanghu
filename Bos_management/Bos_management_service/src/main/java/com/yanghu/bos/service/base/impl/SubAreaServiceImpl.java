package com.yanghu.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.inject.PrivateBinder;
import com.yanghu.bos.dao.base.SubAreaRepository;
import com.yanghu.bos.domain.base.SubArea;
import com.yanghu.bos.service.base.SubAreaService;

/**  
 * ClassName:SubAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午9:20:16 <br/>       
 */
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
       @Autowired
       private SubAreaRepository subAreaRepository;

    @Override
    public void save(SubArea model) {
          
        subAreaRepository.save(model);
        
    }

    @Override
    public Page<SubArea> findAll(Pageable pageable) {
          
        return subAreaRepository.findAll(pageable);
    }
}
  
