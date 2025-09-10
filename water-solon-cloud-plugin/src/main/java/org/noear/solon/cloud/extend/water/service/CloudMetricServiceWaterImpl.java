/*
 * Copyright 2017-2025 noear.org and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noear.solon.cloud.extend.water.service;

import org.noear.solon.cloud.service.CloudMetricService;
import org.noear.water.WW;
import org.noear.water.WaterClient;

import java.util.Map;

/**
 * 分布式计数服务
 *
 * @author noear
 * @since 1.4
 */
public class CloudMetricServiceWaterImpl implements CloudMetricService {
    @Override
    public void addCounter(String group, String category, String item, long increment, Map<String, String> attrs) {
        WaterClient.Track.addCount(group, category, item, increment);

    }

    @Override
    public void addTimer(String group, String category, String item, long record, Map<String, String> attrs) {
        if (WW.track_service.equals(group)) {
            WaterClient.Track.addTimerByNode(category, item, record);
        } else if (WW.track_from.equals(group)) {
            WaterClient.Track.addTimerByFrom(category, item, record);
        } else {
            WaterClient.Track.addTimer(group, category, item, record);
        }
    }

    @Override
    public void addGauge(String group, String category, String item, long number, Map<String, String> attrs) {
        WaterClient.Track.addGauge(group, category, item, number);
    }
}
