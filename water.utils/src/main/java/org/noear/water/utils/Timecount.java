/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package org.noear.water.utils;

public class Timecount {
    private long start_time;

    //@XNote("开始计时")
    public Timecount start() {
        start_time = System.currentTimeMillis();
        return this;
    }

    //@XNote("结束计时，并返回间隔时间")
    public Timespan stop() {
        return new Timespan(System.currentTimeMillis(), start_time);
    }


    //@XNote("结束计时，并返回间隔秒数")
    public String stop(long ref_second) {
        double temp = (stop().milliseconds() / 10) / 100.00d;

        if (temp > ref_second) {
            return temp + "s******慢!!!";
        } else {
            return temp + "s";
        }
    }
}
