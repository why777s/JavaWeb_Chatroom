package why777s.DAO;

import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import org.hibernate.Session;
import why777s.Entity.User;

import javax.persistence.criteria.Order;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

/**
 * Created by wangzhaojun on 2017/2/26.
 */
public class UserQuery {
    public boolean isValidate(String name,String pwd){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        //2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        /****上面是配置准备，下面开始我们的数据库操作******/
        Session session = sessionFactory.openSession();
        String hql = "select u.passWord from User u where u.userName=?";
        Query query = session.createQuery(hql).setParameter(0,name);
        List<String> list = query.getResultList();
        String result_pwd = list.get(0);
        System.out.println("查询结束！");
        System.out.println(result_pwd);
        session.close();
        if (result_pwd.equals(pwd)){
            return true;
        }
        return  false;
    }

    public boolean is_Regist_Successful(String name ,String pwd){

        try{
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            //2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
            SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            /****上面是配置准备，下面开始我们的数据库操作******/
            Session session = sessionFactory.openSession();

            User registerUser = new User();
            registerUser.setPassWord(pwd);
            registerUser.setUserName(name);
            session.beginTransaction();
            session.save(registerUser);
            session.getTransaction().commit();
            session.close();
            System.out.println("插入数据成功"+name+".."+pwd);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
     }

}