package com.chun.myspringboot.mapper;

import com.chun.myspringboot.pojo.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface SearchMapper {

    //模糊查询
    List<Word> findByNameLike(@Param("name")String wordName);
}
