package com.toowis.simple.mapper;

import com.toowis.simple.annotation.DBXMapper;

/**
 * DBX 핸들링 매퍼
 */
@DBXMapper
public interface DBXSimpleMapper {
    public String selectSimple();
}
