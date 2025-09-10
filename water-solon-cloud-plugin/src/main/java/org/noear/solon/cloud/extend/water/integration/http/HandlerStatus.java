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

import org.noear.snack.ONode;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.cloud.extend.water.WaterProps;
import org.noear.solon.cloud.model.Instance;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.water.utils.RuntimeStatus;
import org.noear.water.utils.RuntimeUtils;

/**
 * 运行状态获取处理（用令牌的形式实现安全）//较高频
 *
 * @author noear
 * @since 1.2
 */
public class HandlerStatus implements Handler {
    @Override
    public void handle(Context ctx) throws Throwable {
        String token = ctx.headerOrDefault(WaterProps.http_header_token, "");

        //调用任务必须要有server token
        if (authServerToken(token)) {
            RuntimeStatus rs = RuntimeUtils.getStatus();
            rs.name = Instance.local().service();
            rs.address = Instance.local().address();

            ctx.outputAsJson(ONode.stringify(rs));
        } else {
            ctx.status(400);
            ctx.output("Invalid server token!");
        }
    }

    /**
     * 验证安全性（基于token）
     */
    private boolean authServerToken(String token) {
        return CloudClient.list().inListOfServerToken(token);
    }
}