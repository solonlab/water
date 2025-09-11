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

import java.util.ArrayList;
import java.util.List;

public class DiscoverM {
    //策略
    public String policy;
    //有域网址
    public String agent;
    //服务列表
    public final List<DiscoverTargetM> list = new ArrayList<>();

    public void add(String protocol, String address, String meta, int w) {
        list.add(new DiscoverTargetM(protocol, address, meta,w));
    }
}
