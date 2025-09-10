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

import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.cloud.CloudProps;
import org.noear.solon.cloud.model.Instance;
import org.noear.solon.cloud.service.CloudLogService;
import org.noear.solon.logging.event.LogEvent;
import org.noear.water.dso.LogPipeline;
import org.noear.water.model.LogM;
import org.noear.water.utils.Datetime;
import org.noear.water.utils.LogHelper;

/**
 * 分布式日志服务
 *
 * @author noear
 * @since 1.3
 */
public class CloudLogServiceWaterImpl implements CloudLogService {
    private String loggerNameDefault;

    public CloudLogServiceWaterImpl(CloudProps cloudProps) {
        loggerNameDefault = cloudProps.getLogDefault();

        if (Utils.isEmpty(loggerNameDefault)) {
            if (Utils.isNotEmpty(Solon.cfg().appName())) {
                loggerNameDefault = Solon.cfg().appName() + "_log";
            }
        }

        if (Utils.isEmpty(loggerNameDefault)) {
            System.err.println("[WARN] Solon.cloud no default logger is configured");
        }
    }

    @Override
    public void append(LogEvent logEvent) {
        String loggerName = logEvent.getLoggerName();

        if (Utils.isEmpty(loggerName)) {
            return;
        }

        if (loggerName.contains(".")) {
            loggerName = loggerNameDefault;
        }

        Datetime datetime = Datetime.Now();

        LogM log = new LogM();

        log.log_id = SnowflakeUtil.genId();
        log.group = Solon.cfg().appGroup();
        log.service = Solon.cfg().appName();

        log.logger = loggerName;
        log.level = (logEvent.getLevel().code / 10);
        log.content = LogHelper.contentAsString(logEvent.getContent());

        if (logEvent.getMetainfo() != null) {
            log.tag = logEvent.getMetainfo().get("tag0");
            log.tag1 = logEvent.getMetainfo().get("tag1");
            log.tag2 = logEvent.getMetainfo().get("tag2");
            log.tag3 = logEvent.getMetainfo().get("tag3");
            log.tag4 = logEvent.getMetainfo().get("tag4");
        }

        if (logEvent.getLoggerName().contains(".")) {
            log.class_name = logEvent.getLoggerName();
        }

        log.thread_name = Thread.currentThread().getName();
        log.trace_id = CloudClient.trace().getTraceId(); //WaterClient.waterTraceId();
        log.from = Instance.local().serviceAndAddress(); //WaterClient.localServiceHost();

        log.log_date = datetime.getDate();
        log.log_fulltime = datetime.getFulltime();

        LogPipeline.singleton().add(log);
    }
}
