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

import org.noear.water.model.ConfigSetM;

/**
 * 配置处理
 *
 * @author noear
 * @since 2.0
 * */
@FunctionalInterface
public interface ConfigHandler {
    void handle(ConfigSetM cfgSet);
}
