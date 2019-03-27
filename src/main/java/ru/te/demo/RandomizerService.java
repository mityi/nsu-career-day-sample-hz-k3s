package ru.te.demo;

import java.util.List;

public interface RandomizerService {

    One put(String hash);

    One winner();

    Data getAll();

    Status getStatus();

    class Status {
        public long owner;
        public long backup;
    }

    class Data {
        public List<String> hashes;
    }

    class One {
        public String hash;
    }
}
