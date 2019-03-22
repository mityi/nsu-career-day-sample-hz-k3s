package ru.te.demo;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IMap;
import com.hazelcast.monitor.LocalMapStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class RandomizerService {

    private static final Logger logger = LoggerFactory.getLogger(RandomizerService.class);

    private final IMap<Long, String> map;
    private final IAtomicLong nextId;

    public RandomizerService(HazelcastInstance hz) {
        this.map = hz.getMap("grid-db");
        this.nextId = hz.getAtomicLong("grid-db-Id");
    }

    public long put(String hash) {
        logger.info("put:"+hash);
        long key = nextId.getAndIncrement();
        map.put(key, hash);
        return key;
    }

    public String winner() {
        logger.info("try to find winner");
        int size = map.size();
        int result;
        if (size == 0) {
            return "NONE";
        } else {
            int i = new Random().nextInt(size);
            return map.get((long) i);
        }
    }

    public String getAll() {
        logger.info("get all data ...");
        StringBuffer allData = new StringBuffer();
        map.forEach((k, v) -> allData.append("k:v -> ").append(k).append(":").append(v).append("\n"));
        return allData.toString();
    }

    public String getStatus() {
        logger.info("status is ...");
        LocalMapStats localMapStats = map.getLocalMapStats();
        return "owner: " + localMapStats.getOwnedEntryCount() + " \n"
                + "backup: " + localMapStats.getBackupEntryCount();
    }
}