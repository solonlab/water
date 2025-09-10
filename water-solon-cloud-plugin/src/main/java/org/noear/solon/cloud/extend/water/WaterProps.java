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
package org.noear.solon.cloud.extend.water;

import org.noear.solon.Solon;
import org.noear.solon.cloud.CloudProps;

/**
 * @author noear
 * @since 1.2
 */
public class WaterProps {
    public static final String http_header_from = "Water-From";
    public static final String http_header_trace = "Water-Trace-Id";
    public static final String http_header_token = "Water-Access-Token";


    public static final String GROUP_TOPIC_SPLIT_MART = ":";

    public static final String PROP_EVENT_seal = "event.seal";
    public static final String PROP_EVENT_receive = "event.receive";

}
