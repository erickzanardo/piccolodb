package org.eck.piccolodb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.google.gson.JsonObject;

public class BasicTest {

    @Test
    public void testSaveAndGet() {
        String basePath = System.getProperty("java.io.tmpdir") + "TESTDB\\";

        PiccoloDB db = new PiccoloDB();
        db.load(basePath);
        JsonObject o = new JsonObject();
        o.addProperty("field1", "Bla");

        db.save(o, "entitytest", 1l);
        File f = new File(basePath + "entitytest\\1.json");
        assertTrue(f.exists());

        JsonObject jsonObject = db.get("entitytest", 1l);
        assertEquals(o, jsonObject);
    }

    @Test
    public void testListAll() {
        String basePath = System.getProperty("java.io.tmpdir") + "TESTDB\\";

        PiccoloDB db = new PiccoloDB();
        db.load(basePath);

        JsonObject o = new JsonObject();
        o.addProperty("field1", "Bla");
        db.save(o, "entitytest", 1l);

        o = new JsonObject();
        o.addProperty("field1", "Bla2");
        db.save(o, "entitytest", 2l);

        o = new JsonObject();
        o.addProperty("field1", "Bla3");
        db.save(o, "entitytest", 3l);

        List<JsonObject> result = db.listAll("entitytest");
        assertEquals(3, result.size());
    }

    @Test
    public void testDelete() {
        String basePath = System.getProperty("java.io.tmpdir") + "TESTDB\\";

        PiccoloDB db = new PiccoloDB();
        db.load(basePath);
        JsonObject o = new JsonObject();
        o.addProperty("field1", "Bla");

        db.save(o, "entitytest", 1l);
        File f = new File(basePath + "entitytest\\1.json");
        assertTrue(f.exists());

        assertNotNull(db.get("entitytest", 1l));
        db.delete("entitytest", 1l);
        assertNull(db.get("entitytest", 1l));

        f = new File(basePath + "entitytest\\1.json");
        assertFalse(f.exists());

        // Should not throw error
        db.delete("entitytest", 11l);
    }
}
