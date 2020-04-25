package com.chen.f.core.page;

import java.io.Serializable;

/**
 * 顺序类型枚举
 *
 * @author chen
 * @since 2020/4/23 16:11.
 */
public enum OrderTypeEnum implements Serializable {
    //升序
    ASC,
    //倒序
    DESC,

    ;

    public boolean isAsc() {
        return this == ASC;
    }

    public boolean isDesc() {
        return this == DESC;
    }
}
