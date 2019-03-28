package ru.te.demo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.te.demo.RandomizerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomizerServiceImpl implements RandomizerService {

    private static final Logger logger = LoggerFactory.getLogger(RandomizerServiceImpl.class);

    private final Set<String> hashes;

    public RandomizerServiceImpl(Set<String> hashes) {
        this.hashes = hashes;
    }

    public One put(String hash) {
        logger.info("add: " + hash);
        hashes.add(hash);
        return getOne(hash);
    }

    public One winner() {
        logger.info("try to find winner ... ");
        int size = hashes.size();
        if (size == 0) {
            return getOne("Nil");
        }
        int i = new Random().nextInt(size);
        List<String> all = new ArrayList<>(hashes);
        return getOne(all.get(i));
    }

    public Status getStatus() {
        Status status = new Status();
        status.owner = hashes.size();
        status.backup = 0;
        logger.info("status is ... ");
        return status;
    }

    public Data getAll() {
        Data data = new Data();
        data.hashes = new ArrayList<>(hashes);
        logger.info("get all data ... ");
        return data;
    }

    private One getOne(String hash) {
        One one = new One();
        one.hash = hash;
        return one;
    }

}