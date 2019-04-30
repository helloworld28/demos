package com.jim.java8.mybatis;

import org.apache.ibatis.annotations.Param;

/**
 * @author Jim
 * @date 2019/3/13
 */
public interface UserMapper {

    User selectById(Integer id);

    void updateUserName(@Param("name") String name, @Param("id") Integer id);
}
