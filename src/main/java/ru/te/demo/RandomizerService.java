package ru.te.demo;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IMap;
import com.hazelcast.monitor.LocalMapStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomizerService {

    private final IMap<Long, String> map;
    private final IAtomicLong nextId;

    @Autowired
    public RandomizerService(HazelcastInstance hz) {
        this.map = hz.getMap("grid-db");
        this.nextId = hz.getAtomicLong("grid-db-Id");
    }

    public long put(String hash) {
        long key = nextId.getAndIncrement();
        map.put(key, hash);
        return key;
    }

    public String winner() {
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
        StringBuffer allData = new StringBuffer();
        map.forEach((k, v) -> allData.append("k:v -> ").append(k).append(":").append(v).append("\n"));
        return allData.toString();
    }

    public String getStatus() {
        LocalMapStats localMapStats = map.getLocalMapStats();
        return "owner: " + localMapStats.getOwnedEntryCount() + " \n"
                + "backup: " + localMapStats.getBackupEntryCount();
    }
}