package com.itheima.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SetFactoryBean;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;

/**  
 * ClassName:UserDaoImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月9日 下午8:18:46 <br/>       
 */


@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    //spring 的方式 自动注入  sessionfactory 
    // 父类方法中  修饰为 fanl 不可以重写 所以用 方法注释的方法调用父类方法注入sessionfactory
    @Autowired
    public void SetFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public User findById(int i) {
        
        User user = getHibernateTemplate().get(User.class, i);
        
        return user;
    }
    
    
}
  
