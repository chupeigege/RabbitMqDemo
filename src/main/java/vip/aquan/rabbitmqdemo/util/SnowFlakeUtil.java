package vip.aquan.rabbitmqdemo.util;

import cn.hutool.core.util.IdUtil;

/**
 * 雪花算法
 * @author wcp
 * @create: 2020-11-10 16:02
 */
public class SnowFlakeUtil {

    public static Long createSnowflakeId() {
        return IdUtil.createSnowflake(1, 1).nextId();
    }
}