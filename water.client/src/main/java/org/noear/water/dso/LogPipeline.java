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
import org.noear.water.model.LogM;
import org.noear.water.utils.EventPipeline;

import java.util.List;

/**
 * 写入时，先写到队列
 * 提交时，每次提交100条；消费完后暂停1秒
 *
 * @author noear
 * @since 2.0
 * */
public class LogPipeline extends EventPipeline<LogM> {
    private static final LogPipeline singleton = new LogPipeline();

    public static LogPipeline singleton() {
        return singleton;
    }

    private LogPipeline() {
        super();
    }

    @Override
    protected void handle(List<LogM> logEvents) {
        WaterClient.Log.appendAll(logEvents, true);
    }
}
