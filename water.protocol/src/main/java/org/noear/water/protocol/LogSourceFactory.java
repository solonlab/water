/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package org.noear.water.protocol;

import org.noear.water.protocol.model.log.LoggerMeta;

import java.io.IOException;

public interface LogSourceFactory {
    /**
     * 更新日志源
     * */
    void updateSource(String logger) throws IOException;

    /**
     * 获取日志源
     * */
    LogSource getSource(String logger);

    /**
     * 获取日志器元信息
     * */
    LoggerMeta getLoggerMeta(String logger);
}
