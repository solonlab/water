/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package waterapi.dso;

import org.noear.solon.core.event.EventBus;
import org.noear.water.model.LogM;
import org.noear.water.protocol.ProtocolHub;
import org.noear.water.utils.EventPipeline;

import java.util.List;

/**
 * 写入时，先写到队列
 * 提交时，每次提交500条；消费完后暂停0.2秒
 *
 * @author noear
 * */
public class LogPipelineLocal extends EventPipeline<LogM> {
    private static LogPipelineLocal singleton = new LogPipelineLocal();

    public static LogPipelineLocal singleton() {
        return singleton;
    }

    private LogPipelineLocal() {
        super(200, 500);
    }

    @Override
    protected void handle(List<LogM> logEvents) {
        try {
            ProtocolHub.logStorer.writeAll(logEvents);
        } catch (Throwable ex) {
            EventBus.publish(ex); //todo: EventBus.pushAsyn(ex);
        }
    }
}