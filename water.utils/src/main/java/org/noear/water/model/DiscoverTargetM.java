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

public class DiscoverTargetM {
    public final String protocol;
    public final String address;
    public final String meta;
    public final int weight;

    public DiscoverTargetM(String protocol, String address, String meta, int weight) {
        this.protocol = protocol;
        this.address = address;
        this.meta = meta;
        this.weight = weight;
    }
}
