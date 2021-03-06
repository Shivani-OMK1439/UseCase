package com.helper.dao;

import com.helper.entity.CourseDetail;
import com.helper.entity.UserInfo;
import com.helper.entity.UserTokenDetails;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.management.Query;
import java.time.LocalDateTime;

@Repository
public class UserTokenDetailsDao {

    @Autowired
    @Qualifier("hibernate4AnnotatedSessionFactory")
    private SessionFactory sessionFactory;


    public void save(UserTokenDetails userTokenDetails) throws Exception {
        try {

            Session session = sessionFactory.openSession();
            Transaction tx;
            tx = session.beginTransaction();

            session.save(userTokenDetails);
            tx.commit();

        }
        catch (Exception e){
            System.out.println("Something went Wrong");
            throw e;

        }
    }


    public LocalDateTime getValidUpto(String userToken)
    {
        Session session = this.sessionFactory.openSession();
        UserTokenDetails userTokenDetails = (UserTokenDetails) session.get(UserTokenDetails.class,userToken);
       /* if(userTokenDetails==null)
            return null;
        */
        LocalDateTime localDateTime = userTokenDetails.getValidUpto();
        session.close();
        return localDateTime;
    }




}
