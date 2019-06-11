package com.chen.f.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.core.mapper.CountryMapper;
import com.chen.f.core.pojo.Country;
import com.chen.f.core.service.ICountryService;
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
