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
package org.noear.solon.cloud.extend.water.integration.http;

import org.noear.solon.Utils;
import org.noear.solon.cloud.extend.water.WaterProps;
import org.noear.solon.cloud.extend.water.service.CloudEventServiceWaterImpl;
import org.noear.solon.cloud.model.Event;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.solon.core.handle.MethodType;
import org.noear.water.WaterClient;
import org.noear.water.dso.MessageHandler;
import org.noear.water.model.MessageM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息接收处理（用签名的形式实现安全）//高频
 *
 * @author noear
 * @since 1.2
 */
public class HandlerReceive implements Handler, MessageHandler {
    static final Logger log = LoggerFactory.getLogger(HandlerReceive.class);

    private CloudEventServiceWaterImpl eventService;

    public HandlerReceive(CloudEventServiceWaterImpl eventService) {
        this.eventService = eventService;
    }

    @Override
    public void handle(Context ctx) throws Throwable {
        try {
            if (MethodType.HEAD.name.equals(ctx.method()) || ctx.paramMap().size() == 0) {
                ctx.output("HEAD-OK");
                return;
            }

            String rst = WaterClient.Message.receiveMessage(ctx::param, eventService.getSeal(), this);
            ctx.output(rst);
        } catch (Throwable e) {
            e = Utils.throwableUnwrap(e);
            log.warn(e.getMessage(), e);
            ctx.output(e);
        }
    }

    @Override
    public boolean handle(MessageM msg) throws Throwable {
        Event event = null;
        if (msg.topic.contains(WaterProps.GROUP_TOPIC_SPLIT_MART)) {
            String[] groupAndTopic = msg.topic.split(WaterProps.GROUP_TOPIC_SPLIT_MART);
            event = new Event(groupAndTopic[1], msg.message);
            event.group(groupAndTopic[0]);
        } else {
            event = new Event(msg.topic, msg.message);
        }

        event.key(msg.key);
        event.tags(msg.tags);
        event.times(msg.times);

        return eventService.onReceive(msg.topic, event); // 可以不吃异常
    }
}