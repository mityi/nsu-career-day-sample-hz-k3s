package ru.te.demo;

import com.google.gson.Gson;
import ru.te.demo.impl.RandomizerServiceImpl;

import java.util.HashSet;
import java.util.Set;

import static spark.Spark.get;
import static spark.Spark.port;

public class DemoApplication {

    private static Set<String> hashes = new HashSet<>();

    public static void main(String[] args) {
        port(8080);
        RandomizerService service = new RandomizerServiceImpl(hashes);
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
