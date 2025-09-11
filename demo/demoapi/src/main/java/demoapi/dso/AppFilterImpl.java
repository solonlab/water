/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package demoapi.dso;

import lombok.extern.slf4j.Slf4j;
import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.logging.utils.TagsMDC;

/**
 * @author noear 2021/11/7 created
 */
@Slf4j
@Component
public class AppFilterImpl implements Filter {
    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        //1.开始计时（用于计算响应时长）
        long start = System.currentTimeMillis();

        TagsMDC.tag1(ctx.pathNew());

        if ("HEAD".equals(ctx.method()) == false) {
            //排除water的路径
            if (ctx.path().startsWith("/_") == false) {
                log.info("> Headers: {}\n> Params: {}", ctx.headerMap(), ctx.paramMap());
            }
        }

        try {
            chain.doFilter(ctx);
        } catch (Throwable e) {
            //2.顺带记录个异常
            log.error("< Error: {}", e);
        } finally {
            String output = ctx.attr("output");

            if (Utils.isNotEmpty(output)) {
                //排除water的路径
                if (ctx.path().startsWith("/_") == false) {
                    log.info("< Body: {}", output);
                }
            }

            //3.获得接口响应时长
            long milliseconds = System.currentTimeMillis() - start;
            CloudClient.metric().addTimer(Solon.cfg().appName(), "path", ctx.pathNew(), milliseconds);
        }
    }
}