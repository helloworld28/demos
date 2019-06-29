package com.jim.java8.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * @author Jim
 * @date 2019/3/9
 */
public class MyBatisDemo {

    public static void main(String[] args) throws IOException {

        String configFile = "mybatis-config.xml";

        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(MyBatisDemo.class.getClassLoader(), configFile));
        SqlSession sqlSession =
                sqlSessionFactory.openSession();

        Object reslut = sqlSession.selectOne("org.mybatis.example.UserMapper.selectUser", 2);



        sqlSession.close();
        System.out.println(reslut);

    }
}
