package ru.te.demo;

import com.google.gson.Gson;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import ru.te.demo.impl.RandomizerServiceImpl;

import static spark.Spark.get;
import static spark.Spark.port;

public class DemoApplication {

    private static HazelcastInstance hz = Hazelcast.newHazelcastInstance();

    public static void main(String[] args) {
        port(8080);
        RandomizerService service = new RandomizerServiceImpl(hz);
        Gson gson = new Gson();

        get("/all", "application/json",
            (req, res) -> {
                res.type("application/json");
                return service.getAll();
            }, gson::toJson);

        get("/status", "application/json",
            (req, res) -> {
                res.type("application/json");
                return service.getStatus();
            }, gson::toJson);

        get("/add/:hash", "application/json",
            (req, res) -> {
                String hash = req.params("hash");
                res.type("application/json");
                return service.put(hash);
            }, gson::toJson);

        get("/winner", "application/json",
            (req, res) -> {
                res.type("application/json");
                return service.winner();
            }, gson::toJson);
    }

}
