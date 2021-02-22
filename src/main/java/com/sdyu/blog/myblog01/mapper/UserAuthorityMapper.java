package com.sdyu.blog.myblog01.mapper;

import com.sdyu.blog.myblog01.entity.UserAuthority;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserAuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAuthority record);

    int insertSelective(UserAuthority record);

    UserAuthority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAuthority record);

    int updateByPrimaryKey(UserAuthority record);
}