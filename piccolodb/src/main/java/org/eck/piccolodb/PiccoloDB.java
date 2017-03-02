package org.eck.piccolodb;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eck.piccolodb.exceptions.PiccoloDBFileNotFoundException;
import org.eck.piccolodb.utils.FileUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PiccoloDB {
    private String dbPath;
    private JsonParser jsonParser = new JsonParser();

    public void load(String dbPath) {
        this.dbPath = dbPath;
    }

    public void save(JsonObject object, String entity, Long id) {
        File file = FileUtils.createFile(getEntityPath(entity, id));
        FileUtils.writeFile(object.toString(), file);
    }

    public JsonObject get(String entity, Long id) {
        try {
            File file = getEntityPath(entity, id).toFile();
            String content = FileUtils.readFile(file.getAbsolutePath());
            return jsonParser.parse(content).getAsJsonObject();
        } catch (PiccoloDBFileNotFoundException e) {
            return null;
        }
    }

    public List<JsonObject> listAll(String entity) {
        List<JsonObject> result = new ArrayList<JsonObject>();

        File entityFolder = new File(dbPath, entity);
        if (entityFolder.exists()) {
            File[] listFiles = entityFolder.listFiles();
            if (listFiles != null) {
                for (File entityFile : listFiles) {
                    String readFile = FileUtils.readFile(entityFile.getAbsolutePath());
                    result.add(jsonParser.parse(readFile).getAsJsonObject());
                }
            }
        }

        return result;
    }

    public void delete(String entity, long id) {
        File file = FileUtils.createFile(getEntityPath(entity, id));
        file.delete();
    }
    
    private Path getEntityPath(String entity, Long id) {
    	return Paths.get(dbPath, entity, id + ".json");
    }

}
