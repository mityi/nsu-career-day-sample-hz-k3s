package ru.te.demo;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import static spark.Spark.get;
import static spark.Spark.port;

public class DemoApplication {

    private static HazelcastInstance hz = Hazelcast.newHazelcastInstance();

    public static void main(String[] args) {
        RandomizerService service = new RandomizerService(hz);
        port(8080);
        get("/all", (req, res) -> service.getAll());
        get("/status", (req, res) -> service.getStatus());
        get("/add/:hash", (req, res) -> {
            String hash = req.params("hash");
            long result = service.put(hash);
            return String.valueOf(result);
        });
        get("/winner", (req, res) -> service.winner());
    }

}
