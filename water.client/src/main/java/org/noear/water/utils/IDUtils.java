/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package org.noear.water.utils;

import org.noear.redisx.RedisClient;
import org.noear.water.WaterSetting;

import java.util.UUID;

/**
 * 分布式ID工具
 *
 * @author noear
 * @since 2.0
 * */
public class IDUtils {
    private static RedisClient _redis_idx = WaterSetting.redis_cfg().getRd(1);

    public static String guid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static long newID(String group, String key, int cacheTime) {
        return _redis_idx.openAndGet((ru) -> ru.key(group).expire(cacheTime).hashIncr(key, 1l));
    }

    public static long newID(String group, String key) {
        return newID(group, key, 60 * 60 * 24 * 365 * 10);
    }

    public static long newIDOfDate(String group, String key) {
        String group2 = group + "." + Datetime.Now().toString("yyyyMMdd");
        return newID(group2, key, 60 * 60 * 25);
    }

    public static long newIDOfHour(String group, String key) {
        String group2 = group + "." + Datetime.Now().toString("yyyyMMddHH");
        return newID(group2, key, 60 * 60 * 2);
    }
}
