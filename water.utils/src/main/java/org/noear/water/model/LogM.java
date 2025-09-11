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

import java.util.Date;

public class LogM {
    public long log_id;
    public String logger;
    public String trace_id;
    public int level;
    public String tag;
    public String tag1;
    public String tag2;
    public String tag3;
    public String tag4;
    public long weight;//=tag5
    public String group;//=tag6
    public String service;//=tag7
    public String class_name;
    public String thread_name;
    public String content;
    public String metainfo;
    public String from;
    public int log_date;
    public Date log_fulltime;
}
