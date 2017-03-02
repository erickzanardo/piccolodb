package org.eck.piccolodb.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eck.Locomotive;
import org.eck.piccolodb.PiccoloDB;

import java.util.List;

public class PiccoloDBRestServer {
    public static void main(String args[]) {
        PiccoloDB db = new PiccoloDB();
        db.load("/tmp/rest-test");

        Locomotive locomotive = new Locomotive(8080);

        locomotive.get("/entities/:entity/:id", (req, resp) -> {
            JsonObject jsonObject = db.get(req.param("entity").asString(), req.param("id").asString());
            if (jsonObject != null) {
                resp.headers().put("Content-Type", "application/json");
                resp.append(jsonObject.toString());
            } else {
                resp.status(404);
            }
            resp.send();
        });

        locomotive.get("/entities/:entity", (req, resp) -> {
            List<JsonObject> entity = db.listAll(req.param("entity").asString());
            JsonArray arr = new JsonArray();
            entity.stream().forEach(obj -> arr.add(obj));

            resp.headers().put("Content-Type", "application/json");
            resp.append(arr.toString());
            resp.send();
        });

        locomotive.put("/entities/:entity/:id", (req, resp) -> {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(req.body()).getAsJsonObject();
            db.save(jsonObject, req.param("entity").asString(), req.param("id").asString());

            resp.status(204);
            resp.send();
        });

        locomotive.boot();
    }
}
