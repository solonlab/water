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
import org.noear.water.track.TrackNames;

/**
 * @author noear 2022/7/1 created
 */
public class NamesUtils {
    public static RedisClient rd_track_md5;

    static {
        rd_track_md5 = WaterSetting.redis_track_cfg().getRd(6);
        TrackNames.singleton().bind(rd_track_md5);
    }

    public static String getNameMd5(String name) {
        return TrackNames.singleton().getNameMd5(name);
    }

    public static String getName(String nameMd5) {
        return TrackNames.singleton().getName(nameMd5);
    }
}
