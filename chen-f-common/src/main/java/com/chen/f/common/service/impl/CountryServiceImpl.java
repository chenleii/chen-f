package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.mapper.CountryMapper;
import com.chen.f.common.pojo.Country;
import com.chen.f.common.service.ICountryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 国家表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country> implements ICountryService {

}
