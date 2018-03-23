package com.yanghu.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanghu.bos.dao.base.TakeTimeRepository;
import com.yanghu.bos.domain.base.TakeTime;
import com.yanghu.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 上午11:36:07 <br/>       
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public TakeTime findById(Long takeTimeId) {
          
        return takeTimeRepository.findOne(takeTimeId);
    }
}
  
