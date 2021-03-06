package com.chen.f.common.configuration;

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
                RegionIdCardServiceImpl.class.getName(),
                RegionServiceImpl.class.getName(),
                SysDictionaryServiceImpl.class.getName(),
                SysDictionaryItemServiceImpl.class.getName(),
                SysParameterServiceImpl.class.getName(),

                SysApiServiceImpl.class.getName(),
                SysMenuServiceImpl.class.getName(),
                SysOrganizationServiceImpl.class.getName(),
                SysPermissionServiceImpl.class.getName(),
                SysRoleServiceImpl.class.getName(),
                SysTimedTaskLogServiceImpl.class.getName(),
                SysTimedTaskServiceImpl.class.getName(),
                SysUserServiceImpl.class.getName(),

                CountryController.class.getName(),
                RegionController.class.getName(),
                RegionIdCardController.class.getName(),
                SysDictionaryController.class.getName(),
                SysDictionaryItemController.class.getName(),
                SysParameterController.class.getName(),
        };
    }
}
