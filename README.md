piccolodb
=========

Very simple embed nosql database to store and retrive data in JSON format, it is built on top of google's Json library (gson). It's recommended for simple data storage and retrive.

## Basic Features

### Load the database

In PiccoloDB the database is a simple folder with files in it, so basically any folder can be used, here is an example of how load a database in a random selected folder:

```java
String basePath = System.getProperty("java.io.tmpdir") + "TESTDB\\";
PiccoloDB db = new PiccoloDB();
db.load(basePath);
```

### Save a Json
```java
JsonObject o = new JsonObject();
o.addProperty("field1", "Bla");
//          Entity/Table Identifier
db.save(o, "entitytest", 1l);
```
### Retrieve a Json
```java
JsonObject jsonObject = db.get("entitytest", 1l);
```

### List all jsons from an entity
```java
List<JsonObject> result = db.listAll("entitytest");
```

More features comming out soon!
