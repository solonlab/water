package watersev.controller;

import org.noear.snack4.ONode;

/**
 * 数据监视结果
 *
 * @author noear
 */
public class MotResult {
    public final ONode data;
    public final boolean succeed;

    public MotResult(Object data, boolean succeed) {
        this.data = ONode.ofBean(data);
        this.succeed = succeed;
    }

    public static MotResult failure() {
        return new MotResult(null, false);
    }

    public static MotResult succeed(Object data) {
        return new MotResult(data, true);
    }
}
