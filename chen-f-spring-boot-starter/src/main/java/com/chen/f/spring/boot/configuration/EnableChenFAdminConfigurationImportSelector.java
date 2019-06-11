package com.chen.f.spring.boot.configuration;


import com.chen.f.admin.service.impl.*;
import com.chen.f.admin.web.controller.*;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chen
 * @since 2019/1/14 22:55.
 */
public class EnableChenFAdminConfigurationImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                SysApiServiceImpl.class.getName(),
                SysMenuServiceImpl.class.getName(),
                SysPermissionServiceImpl.class.getName(),
                SysRoleServiceImpl.class.getName(),
                SysTimedTaskLogServiceImpl.class.getName(),
                SysTimedTaskServiceImpl.class.getName(),
                SysUserServiceImpl.class.getName(),


                OnlineController.class.getName(),
                SysApiController.class.getName(),
                SysDictController.class.getName(),
                SysMenuController.class.getName(),
                SysParameterController.class.getName(),
                SysPermissionController.class.getName(),
                SysRoleController.class.getName(),
                SysTimedTaskController.class.getName(),
                SysTimedTaskLogController.class.getName(),
                SysUserController.class.getName(),
                TestController.class.getName(),

        };
    }
}
