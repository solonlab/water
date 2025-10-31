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

import org.noear.snack4.ONode;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

public class PropertiesM  extends Properties {

    public PropertiesM(){
        super();
    }

    public PropertiesM(Properties props){
        super(props);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> tmp = new HashMap<>();
        this.forEach((k, v) -> {
            if (k instanceof String) {
                tmp.put((String) k, v);
            }
        });

        return tmp;
    }

    public ONode toNode() {
        ONode tmp = new ONode();
        this.forEach((k, v) -> {
            if (k instanceof String) {
                tmp.set((String) k, v);
            }
        });

        return tmp;
    }

    public <T> T toObject(Class<T> clz) {
        return toNode().toBean(clz);
    }

    /**
     * 查找 keyStarts 开头的所有配置；并生成一个新的 配置集
     *
     * @param keyStarts key 的开始字符
     */
    public PropertiesM getProp(String keyStarts) {
        PropertiesM prop = new PropertiesM();
        doFind(keyStarts + ".", prop::put);
        return prop;
    }

    private void doFind(String keyStarts, BiConsumer<String, String> setFun) {
        String key2 = keyStarts;
        int idx2 = key2.length();

        forEach((k, v) -> {
            if (k instanceof String && v instanceof String) {
                String keyStr = (String) k;

                if (keyStr.startsWith(key2)) {
                    String key = keyStr.substring(idx2);

                    setFun.accept(key, (String) v);
                    if (key.contains("-")) {
                        String[] ss = key.split("-");
                        StringBuilder sb = new StringBuilder(key.length());
                        sb.append(ss[0]);
                        for (int i = 1; i < ss.length; i++) {
                            if (ss[i].length() > 1) {
                                sb.append(ss[i].substring(0, 1).toUpperCase()).append(ss[i].substring(1));
                            } else {
                                sb.append(ss[i].toUpperCase());
                            }
                        }
                        setFun.accept(sb.toString(), (String) v);
                    }
                }
            }
        });
    }
}
