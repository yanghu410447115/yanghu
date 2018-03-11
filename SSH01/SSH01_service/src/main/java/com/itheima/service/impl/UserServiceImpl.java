package com.itheima.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.service.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月9日 下午9:02:55 <br/>       
 */
@Service
@Transactional  //开启事物
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserDao userDao;

    @Override
    public User findByid(int i) {
        
          
        return userDao.findById(i);
    }

}
  
