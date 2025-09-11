/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package org.noear.water.dso;

import org.noear.water.WaterClient;
import org.noear.water.track.TrackEvent;
import org.noear.water.track.TrackEventBuffer;
import org.noear.water.track.TrackEventGather;

import java.util.Map;

/**
 * @author noear 2022/6/30 created
 */
public class TrackPipeline extends TrackEventBuffer {
    private static final TrackPipeline singleton = new TrackPipeline();

    public static TrackPipeline singleton() {
        return singleton;
    }

    @Override
    protected void flush(Map<String, TrackEvent> mainSet, Map<String, TrackEvent> serviceSet, Map<String, TrackEvent> fromSet) throws Throwable {
        synchronized (this) {
            TrackEventGather gather = new TrackEventGather();
            gather.mainSet = mainSet;
            gather.serviceSet = serviceSet;
            gather.fromSet = fromSet;

            WaterClient.Track.appendAll(gather, false);

            mainSet.clear();
            serviceSet.clear();
            fromSet.clear();
        }
    }
}
