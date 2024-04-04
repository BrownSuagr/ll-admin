package com.love.low.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.low.entity.Company;
import org.apache.ibatis.annotations.Mapper;

/**
 * @name: CompanyMapper
 * @description: 公司信息表 Mapper 接口
 * @author: BrownSugar
 * @date: 2024-04-02 08:24:51
 * @version: 1.0.0
 * @see BaseMapper
 **/
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {

}
