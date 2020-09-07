package com.gurundong.threadproject.thread.common.redis.config;//package com.inspur.bss.order.redis.config;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.time.Duration;
//
//@Configuration
//public class RedisAutoConfig {
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPool,
//                                                         RedisStandaloneConfiguration jedisConfig) {
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisConfig);
//        connectionFactory.setPoolConfig(jedisPool);
//        return connectionFactory;
//    }
//
//    @Configuration
//    public static class JedisConf {
//        @Value("${spring.redis.host}")
//        private String host;
//        @Value("${spring.redis.port}")
//        private Integer port;
//        @Value("${spring.redis.password}")
//        private String password;
////            @Value("${spring.redis.database:0}")
////            private Integer database;
//
//            @Value("${spring.redis.jedis.pool.max-active}")
//            private Integer maxActive;
//            @Value("${spring.redis.jedis.pool.max-idle}")
//            private Integer maxIdle;
//            @Value("${spring.redis.jedis.pool.max-wait}")
//            private Long maxWait;
//            @Value("${spring.redis.jedis.pool.min-idle}")
//            private Integer minIdle;
//
//        @Bean
//        public JedisPoolConfig jedisPool() {
//            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//            jedisPoolConfig.setMaxIdle(maxIdle);
//            jedisPoolConfig.setMaxWaitMillis(maxWait);
//            jedisPoolConfig.setMaxTotal(maxActive);
//            jedisPoolConfig.setMinIdle(minIdle);
//            return jedisPoolConfig;
//        }
//
//        @Bean
//        public RedisStandaloneConfiguration jedisConfig() {
//            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//            config.setHostName(host);
//            config.setPort(port);
//            //config.setDatabase(database);
//            config.setPassword(RedisPassword.of(password));
//            return config;
//        }
//    }
//}
//
//
