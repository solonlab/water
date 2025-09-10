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
import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.cloud.model.Instance;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.water.WaterClient;

/**
 * 服务停目处理（用强制ip名单处理安全）//超低频//一般在固定IP下运行，给运维手动用
 *
 * @author noear
 * @since 1.2
 */
public class HandlerStop implements Handler {
    @Override
    public void handle(Context ctx) throws Throwable {
        ctx.output(handle0(ctx));
    }

    private String handle0(Context ctx) throws Throwable {
        String ip = ctx.realIp();

        if (authServerIp(ip)) {
            stateSet(false);
            Solon.stop();
            return "OK";
        } else {
            return (ip + ", not is safelist!");
        }
    }

    public void stateSet(boolean enabled) {
        Instance instance = Instance.local();

        if (Utils.isNotEmpty(instance.address())) {
            String meta = null;
            if (instance.meta() != null) {
                meta = ONode.stringify(instance.meta());
            }

            WaterClient.Registry.set(Solon.cfg().appGroup(), instance.service(), instance.address(), meta, enabled);
        }
    }

    private boolean authServerIp(String ip) {
        if (Solon.cfg().isDriftMode()) {
            //isDriftMode，拒绝停止
            return false;
        } else {
            return CloudClient.list().inListOfServerIp(ip);
        }
    }
}
