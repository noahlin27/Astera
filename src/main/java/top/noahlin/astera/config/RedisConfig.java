package top.noahlin.astera.config;

public class RedisConfig {
    public static final int maxTotal = 8;
    public static final int maxIdle = 8;
    public static final int minIdle = 0;

    public static final String host = "127.0.0.1";
    public static final int port = 6379;
    public static final int timeout = 2000;
    public static final String password = "123456";
    public static final int database = 1;

}
