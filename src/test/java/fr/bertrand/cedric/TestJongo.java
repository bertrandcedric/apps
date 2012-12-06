package fr.bertrand.cedric;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.foursquare.fongo.Fongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class TestJongo {
	@Test
	public void fongo() {
		Fongo fongo = new Fongo("mongo server 1");

		// once you have a DB instance, you can interact with it
		// just like you would with a real one.
		DB db = fongo.getDB("mydb");
		DBCollection collection = db.getCollection("mycollection");
		collection.insert(new BasicDBObject("name", "jon"));

		assertThat(collection.findOne().get("name")).isEqualTo("jon");
	}
}
