package com.chen.f.common.helper;

import com.chen.f.common.pojo.SysParameter;
import com.chen.f.common.service.ISysParameterService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统参数帮助类
 *
 * @author chen
 * @since 2018/11/1 14:11.
 */
public class SysParameterHelper {
    protected static final Logger logger = LoggerFactory.getLogger(SysParameterHelper.class);

    /**
     * 系统参数缓存
     */
    private static final Map<String, SysParameter> sysParameterMap = new ConcurrentHashMap<>();

    private final com.chen.f.common.service.ISysParameterService sysParameterService;

    public SysParameterHelper(ISysParameterService sysParameterService) {
        this.sysParameterService = sysParameterService;
    }

    /**
     * 初始化系统参数
     * <p>
     * 可手动调用做刷新操作
     */
    public void init() {
        logger.debug("系统参数初始化");
        List<SysParameter> allEnabledSysParameterList = sysParameterService.getEnabledSysParameterList();
        //清空缓存
        sysParameterMap.clear();
        if (CollectionUtils.isNotEmpty(allEnabledSysParameterList)) {
            logger.debug("获取了 {} 条系统参数", allEnabledSysParameterList.size());
            allEnabledSysParameterList.forEach((sysParameter -> sysParameterMap.put(sysParameter.getCode(), sysParameter)));
        } else {
            logger.debug("获取了 0 条系统参数");
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
