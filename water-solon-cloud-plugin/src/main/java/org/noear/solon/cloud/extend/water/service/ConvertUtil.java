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

import org.noear.snack.ONode;
import org.noear.solon.Utils;
import org.noear.solon.cloud.model.Discovery;
import org.noear.solon.cloud.model.Instance;
import org.noear.water.model.DiscoverM;

import java.util.Map;

/**
 * 转换工具
 *
 * @author noear
 * @since 1.2
 */
class ConvertUtil {
    public static Discovery from(String group, String service, DiscoverM d1) {
        if (d1 == null) {
            return null;
        } else {
            Discovery d2 = new Discovery(group, service);
            d2.agent(d1.agent);
            d2.policy(d1.policy);

            d1.list.forEach((t1) -> {
                Map<String, String> meta = null;
                if (Utils.isNotEmpty(t1.meta)) {
                    meta = ONode.deserialize(t1.meta);
                }

                String[] hostAndPort = t1.address.split(":");
                int port = 0;
                if (hostAndPort.length > 1) {
                    port = Integer.parseInt(hostAndPort[1]);
                }

                Instance instance = new Instance(service, hostAndPort[0], port)
                        .weight(t1.weight)
                        .metaPutAll(meta); //会自动处理 protocol

                d2.instanceAdd(instance);
            });

            return d2;
        }
    }
}
