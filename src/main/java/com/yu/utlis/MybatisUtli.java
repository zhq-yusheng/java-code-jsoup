package com.yu.utlis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
//封装mybatis的工具类
public class MybatisUtli{
    private static SqlSessionFactory sqlSessionFactory;
    static {//固定写法 读取mybatis的配置文件
        try{
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // 封装执行SQL语句的对象
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}