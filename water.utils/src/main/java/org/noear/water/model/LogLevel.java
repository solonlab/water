/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package org.noear.water.model;

public enum LogLevel {
    TRACE(1),
    DEBUG(2),
    INFO(3),
    WARN(4),
    ERROR(5);

    public final int code;

    public static LogLevel of(int code){
        for(LogLevel v : values()){
            if(v.code == code){
                return v;
            }
        }
        //默认值
        return INFO;
    }

    LogLevel(int code) {
        this.code = code;
    }
}
