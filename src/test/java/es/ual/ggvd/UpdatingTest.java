package es.ual.ggvd;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.mongodb.client.MongoDatabase;

public class UpdatingTest {

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
  public void shouldUpdateAPeople() {
    assertEquals(mongodb.findPeopleByEmail("mtorres@ual.es"), "Manolo");

    mongodb.updatePeople("mtorres@ual.es", "Manuel");
    assertEquals(mongodb.findPeopleByEmail("mtorres@ual.es"), "Manuel");
    
  }
}
