package com.toowis.simple.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SimpleMapper {
    public String selectSimple();
    public List<Map<String, Object>> selectListCommonCode(String v);
    public int updateCommonCode(java.lang.String value);
}
