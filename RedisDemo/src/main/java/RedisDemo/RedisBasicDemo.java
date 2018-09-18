package RedisDemo;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisBasicDemo {
    public static void main( String[] args ) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
        poolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
        poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE * 2);
        poolConfig.setJmxEnabled(true);
        poolConfig.setMaxWaitMillis(3000);

        JedisPool jedisPool = new JedisPool(poolConfig, "139.198.13.12", 4125, 2000, "uuid845tylabc123");

        try(Jedis client = jedisPool.getResource()){
            String[] keys = {"150111:name", "150111:article:18", "150111:savenames", "150111:ids", "150111:students"};
            client.del(keys);

            // 1. String
            client.set(keys[0], "Jack");
            System.out.println(client.get(keys[0]));

            // 2. Hash
            client.hset(keys[1], "Id", "18");
            client.hset(keys[1], "Title", "小团队构建大网站");
            System.out.println(client.hgetAll(keys[1]));

            // 3. List
            client.rpush(keys[2], "Jane", "Jim", "Catherine", "Joe");
            System.out.println(client.lrange(keys[2], 0, -1));

            // 4. Set
            client.sadd(keys[3], "14", "16", "15", "17", "12", "14", "13");
            System.out.println(client.smembers(keys[3]));

            // 5. Sorted Set
            client.zadd(keys[4], 99, "Jane");
            client.zadd(keys[4], 92, "Jim");
            client.zadd(keys[4], 98, "Tony");
            client.zadd(keys[4], 97, "Mary");
            client.zadd(keys[4], 95, "Catherine");
            client.zadd(keys[4], 91, "Joe");
            System.out.println(client.zrangeWithScores(keys[4], 0, -1));
        }

        System.exit(0);
    }
}
