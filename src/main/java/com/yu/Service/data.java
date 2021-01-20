package com.yu.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu.mapper.bkMapper;
import com.yu.pojo.beike;
import com.yu.utlis.MybatisUtli;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class data {
    public static String getDataJson()  {

        SqlSession sqlSession = MybatisUtli.getSqlSession();
        ObjectMapper mapper = new ObjectMapper();
        bkMapper bkMapper = sqlSession.getMapper(bkMapper.class);
        List<beike> query = bkMapper.query();
        String JSON = null;
        try {
            JSON = mapper.writeValueAsString(query);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return JSON;
    }
}
