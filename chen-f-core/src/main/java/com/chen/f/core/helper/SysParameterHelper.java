package com.chen.f.core.helper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.core.mapper.SysParameterMapper;
import com.chen.f.core.pojo.SysParameter;
import com.chen.f.core.pojo.enums.StatusEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @since 2018/11/1 14:11.
 */
public class SysParameterHelper {
    protected static final Logger logger = LoggerFactory.getLogger(SysParameterHelper.class);

    private static Map<String, SysParameter> sysParameterMap;

    private final SysParameterMapper sysParameterMapper;

    public SysParameterHelper(SysParameterMapper sysParameterMapper) {
        this.sysParameterMapper = sysParameterMapper;
    }

    /**
     * 初始化系统参数
     * <p>
     * 可手动调用做刷新操作
     */
    public void init() {
        logger.debug("系统参数初始化");
        List<SysParameter> sysParameterList = sysParameterMapper.selectList(Wrappers.<SysParameter>lambdaQuery().eq(SysParameter::getStatus, StatusEnum.ENABLED));

        if (CollectionUtils.isNotEmpty(sysParameterList)) {
            logger.debug("获取了 {} 条系统参数", sysParameterList.size());
            sysParameterMap = new ConcurrentHashMap<>(sysParameterList.size());
            sysParameterList.forEach((sysParameter -> sysParameterMap.put(sysParameter.getCode(), sysParameter)));
        } else {
            logger.debug("获取了 0 条系统参数");
            sysParameterMap = Collections.emptyMap();
        }
        logger.debug("系统参数初始化结束");
    }

    /**
     * 获取系统参数
     *
     * @param code 系统参数标识
     * @return 系统参数
     */
    public static SysParameter get(String code) {
        return sysParameterMap.get(code);
    }

    /**
     * 获取系统参数的值
     *
     * @param code 系统参数标识
     * @return 系统参数值
     */
    public static String getValue(String code) {
        SysParameter sysParameter = get(code);
        if (sysParameter != null) {
            return sysParameter.getValue();
        }
        return null;
    }
}
