package com.yu.mapper;

import com.yu.pojo.beike;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface bkMapper {
    @Insert("insert into beike values(#{region},#{money},#{time})")
    void add(@Param("region") String region,@Param("money")int money,@Param("time") String time);

    @Delete("delete from beike")
    void clean();

    @Select("SELECT * FROM beike  ORDER BY money desc  LIMIT 0,8")
    List<beike> query();
}
