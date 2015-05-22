package es.ual.ggvd;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.client.MongoDatabase;

public class ConnectionTest {
  
  MongoDB mongodb;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    mongodb = new MongoDB();
  }

  @After
  public void tearDown() throws Exception {
    mongodb.disconnect();
  }

  @Test
  public void shouldCreateANewMongoDBClient() {
    
    MongoDatabase db = mongodb.connect("localhost", 27017, "ggvdTest");
    
    assertThat(db, is(notNullValue()));  
    
  }

  @Test
  public void shouldCreateANewMongoDBClientUsingDefaults() {
    
    MongoDatabase db = mongodb.connect("ggvdTest");
    
    assertThat(db, is(notNullValue()));  
    
  }
}
