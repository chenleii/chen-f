package com.chen.f.spring.boot.configuration;

import com.chen.f.common.service.impl.*;
import com.chen.f.common.web.controller.*;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chen
 * @since 2019/1/15 0:16.
 */
public class EnableChenFCommonConfigurationImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                CountryServiceImpl.class.getName(),
                RegionIdCardPrefixServiceImpl.class.getName(),
                RegionServiceImpl.class.getName(),
                SysDictServiceImpl.class.getName(),
                SysParameterServiceImpl.class.getName(),

                CountryController.class.getName(),
                RegionController.class.getName(),
                RegionIdCardPrefixController.class.getName(),
                SysDictController.class.getName(),
                SysParameterController.class.getName(),
        };
    }
}
