package org.eck.piccolodb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.google.gson.JsonObject;

public class BasicTest {

    @Test
    public void testSaveAndGet() {
        File basePath = basePath();

        PiccoloDB db = new PiccoloDB();
        db.load(basePath.getAbsolutePath());
        JsonObject o = new JsonObject();
        o.addProperty("field1", "Bla");

        db.save(o, "entitytest", 1l);
        File f = Paths.get(basePath.getAbsolutePath(), "entitytest", "1.json").toFile();
        assertTrue(f.exists());

        JsonObject jsonObject = db.get("entitytest", 1l);
        assertEquals(o, jsonObject);
    }

    @Test
    public void testListAll() {
        File basePath = basePath();

        PiccoloDB db = new PiccoloDB();
        db.load(basePath.getAbsolutePath());

        List<JsonObject> result = db.listAll("entitytest");
        assertEquals(0, result.size());
        
        JsonObject o = new JsonObject();
        o.addProperty("field1", "Bla");
        db.save(o, "entitytest", 1l);

        o = new JsonObject();
        o.addProperty("field1", "Bla2");
        db.save(o, "entitytest", 2l);

        o = new JsonObject();
        o.addProperty("field1", "Bla3");
        db.save(o, "entitytest", 3l);

        result = db.listAll("entitytest");
        assertEquals(3, result.size());
    }

    @Test
    public void testDelete() {
        File basePath = basePath();

        PiccoloDB db = new PiccoloDB();
        db.load(basePath.getAbsolutePath());
        JsonObject o = new JsonObject();
        o.addProperty("field1", "Bla");

        db.save(o, "entitytest", 1l);
        File f = Paths.get(basePath.getAbsolutePath(), "entitytest", "1.json").toFile();
        assertTrue(f.exists());

        assertNotNull(db.get("entitytest", 1l));
        db.delete("entitytest", 1l);
        assertNull(db.get("entitytest", 1l));

        f = Paths.get(basePath.getAbsolutePath(), "entitytest", "1.json").toFile();
        assertFalse(f.exists());

        // Should not throw error
        db.delete("entitytest", 11l);
    }

    private File basePath() {
        Random r = new Random();
        return Paths.get("/tmp", String.valueOf(r.nextInt(150000))).toFile();
    }
}
