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

public class QueryingTest {

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
  public void shouldFindDocument() {
    String jsonDocument = mongodb.findDocument("ggvd", "foo", "bar");

    assertNotNull(jsonDocument);
  }
  
  @Test
  public void shouldShowPeopleByEmail() {
     mongodb.showPeopleByEmail("mtorres@ual.es");
  }
  
  @Test
  public void shouldFindPeople() {
     assertEquals(mongodb.findPeopleByEmail("mtorres@ual.es"), "Manolo");
  }
  
  @Test
  public void shouldSortAndProjectAQuery() {
    mongodb.sortedAndProjectedFindPeople("Almeria");
  }
  
  @Test
  public void shouldAggregate() {
    mongodb.setupActors();
    mongodb.aggregate();
  }
}
