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

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.function.BiConsumer;

/**
 * @author noear
 */
public class PingUtils {
    public static void ping(String address) throws Exception {
        ping(address, 3000);
    }

    /**
     * Ping 一个地址
     *
     * @param address （例：192.168.1.1 或 192.168.1.1:8080）
     */
    public static void ping(String address, int millis) throws Exception {
        if (address.contains(":")) {
            String host = address.split(":")[0];
            int port = Integer.parseInt(address.split(":")[1]);

            try (Socket socket = new Socket()) {
                SocketAddress addr = new InetSocketAddress(host, port);
                socket.connect(addr, millis);
            }
        } else {
            InetAddress.getByName(address).isReachable(millis);
        }
    }

    public static void pingAsyn(String address, int millis, BiConsumer<Boolean, Throwable> callback) {
        RunUtils.runAsyn(() -> {
            try {
                ping(address, millis);
                callback.accept(true, null);
            } catch (Throwable e) {
                callback.accept(false, e);
            }
        });
    }
}
