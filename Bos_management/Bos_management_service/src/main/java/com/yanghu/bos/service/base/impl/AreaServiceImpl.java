package com.yanghu.bos.service.base.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanghu.bos.dao.base.AreaRepository;
import com.yanghu.bos.domain.base.Area;
import com.yanghu.bos.service.base.AreaService;
import com.yanghu.utils.PinYin4jUtils;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:16:38 <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    
      @Autowired
      private AreaRepository areaRepository;

    @Override
    public void save(List<Area> list) {
          
        areaRepository.save(list);
    }

    @Override
    public Page<Area> findAll(Pageable pageable) {
          
        return areaRepository.findAll(pageable);
    }

    @Override
    public List<Area> likeQuery(String str) {
          
        return areaRepository.likeQuery(str);
    }

    @Override
    public Area saveTransientArea(Area area) {
        
        String province = area.getProvince();
        String city = area.getCity();
        String district = area.getDistrict();
        
        String citycode = PinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
        area.setCitycode(citycode);
        
        String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
        String shortcode = PinYin4jUtils.stringArrayToString(headByString);
        area.setShortcode(shortcode);
          
        return areaRepository.save(area);
    }

      
      
    
}
  
