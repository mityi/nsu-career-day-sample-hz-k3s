package ru.te.demo.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.monitor.LocalMapStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.te.demo.RandomizerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomizerServiceImpl implements RandomizerService {

    private static final Logger logger = LoggerFactory.getLogger(RandomizerServiceImpl.class);

    private final IMap<String, String> hashes;

    public RandomizerServiceImpl(HazelcastInstance hz) {
        this.hashes = hz.getMap("grid-db");
    }

    public One put(String hash) {
        logger.info("add: " + hash);
        this.hashes.put(hash, hash);
        return getOne(hash);
    }

    public One winner() {
        logger.info("try to find winner ... ");
        int size = this.hashes.size();
        if (size == 0) {
            return getOne("Nil");
        }
        int i = new Random().nextInt(size);
        List<String> all = all();
        return getOne(all.get(i));

    }

    public Status getStatus() {
        LocalMapStats localMapStats = this.hashes.getLocalMapStats();
        Status status = new Status();
        status.owner = localMapStats.getOwnedEntryCount();
        status.backup = localMapStats.getBackupEntryCount();
        logger.info("status is ... ");
        return status;
    }

    public Data getAll() {
        Data data = new Data();
        data.hashes = all();
        logger.info("get all data ... ");
        return data;
    }

    private One getOne(String hash) {
        One one = new One();
        one.hash = hash;
        return one;
    }

    private List<String> all() {
        return new ArrayList<>(this.hashes.keySet());
    }

}