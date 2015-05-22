package es.ual.ggvd;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.mongodb.client.MongoDatabase;

public class DeletingTest {

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
  public void shoudRemoveAPeople() {
    mongodb.removePeople("acorral@ual.es");
    
    assertNull(mongodb.findPeopleByEmail("acorral@ual.es"));
  }
}
