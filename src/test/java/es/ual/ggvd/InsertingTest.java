package es.ual.ggvd;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertingTest {

  static MongoDB mongodb;
  static MongoDatabase db;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    mongodb = new MongoDB();

    db = mongodb.connect("ggvdTest");
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {    
    mongodb.disconnect();
  }

  @Test
  public void shouldInsertABasicDocument() {
    mongodb.insertDocument("ggvd", "foo", "bar");
  }

  @Test
  public void shouldInsertAPeople() {
    ArrayList<String> myHobbies = new ArrayList<String>();
    myHobbies.add("music");
    myHobbies.add("movies");

    // Insert a person in the collection "people"
    mongodb.insertPeople("Manolo", "mtorres@ual.es", "ualmtorres", myHobbies,
        "Almeria", "04120");

    // Insert another person in the collection "people"
    mongodb.insertPeople("Antonio", "acorral@ual.es", "ualacorral", myHobbies,
        "Almeria", "04120");
  }

  @Test
  public void shoudInsertManyDocuments() {
    mongodb.insertDocuments("ggvd", "number", 5);
  }

}
