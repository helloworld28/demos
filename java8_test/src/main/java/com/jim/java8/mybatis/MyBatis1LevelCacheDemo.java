package com.jim.java8.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * 一二级缓存使用例子
 *
 * @author Jim
 * @date 2019/3/9
 */
public class MyBatis1LevelCacheDemo {

    private SqlSessionFactory sqlSessionFactory;

    @BeforeMethod
    public void setUp() throws IOException {
        String configFile = "mybatis-config.xml";
        sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(MyBatis1LevelCacheDemo.class.getClassLoader(), configFile));

    }

    @Test
    public void test1LevelCache() throws IOException {
        //当一个session里使用一级缓存
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(2);


        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);

        User user2 = mapper2.selectById(2);
        String oldUserName = user2.getUsername();

        //当另个会话更新了值
        mapper.updateUserName("fooooo", 2);

        //另个会话依然取到旧值
        assertEquals(mapper.selectById(2).getUsername(), "fooooo");
        assertEquals(mapper2.selectById(2).getUsername(), oldUserName);
        sqlSession.rollback();
    }

    public void test2LevelCache() {
        //二级缓存,就是等另外个会话commit,就会缓存起来
        //根据namespace缓存,这样联表查询时namespace不一致,导致更新单表没有及时让缓存失效,联表查询有问题就可能取到脏数据就会问题
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(2);


        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
    }


}
