/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package org.noear.water.track;

import org.noear.redisx.RedisClient;

import java.util.Map;

public class TrackBuffer extends TrackEventBuffer {
    private static final TrackBuffer singleton = new TrackBuffer();

    public static TrackBuffer singleton() {
        return singleton;
    }

    private TrackBuffer() {
        super();
    }

    private RedisClient _redisX;

    public void bind(RedisClient redisX) {
        _redisX = redisX;
    }

    /**
     * 提交并清空
     */
    @Override
    public void flush(Map<String, TrackEvent> mainSet, Map<String, TrackEvent> serviceSet, Map<String, TrackEvent> fromSet) throws Throwable {
        if (_redisX == null) {
            return;
        }

        synchronized (this) {
            if (_redisX != null) {
                _redisX.open((ru) -> {
                    try {
                        for (Map.Entry<String, TrackEvent> kv : mainSet.entrySet()) {
                            TrackUtils.trackAll(ru, kv.getKey(), kv.getValue());
                        }

                        for (Map.Entry<String, TrackEvent> kv : serviceSet.entrySet()) {
                            TrackUtils.trackAll(ru, kv.getKey(), kv.getValue());
                        }

                        for (Map.Entry<String, TrackEvent> kv : fromSet.entrySet()) {
                            TrackUtils.trackAll(ru, kv.getKey(), kv.getValue());
                        }
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                });
            }

            mainSet.clear();
            serviceSet.clear();
            fromSet.clear();
        }
    }
}
