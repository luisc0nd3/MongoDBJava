package es.ual.ggvd;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mongodb.client.MongoDatabase;

public class FinishTest {

  @Test
  public void shouldDeleteDatabase() {
    MongoDB mongodb = new MongoDB();
    
    MongoDatabase db = mongodb.connect("localhost", 27017, "ggvdTest");
    
    db.drop();
  }

}
