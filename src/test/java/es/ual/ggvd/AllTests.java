package es.ual.ggvd;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
  ConnectionTest.class, 
  InsertingTest.class,
  QueryingTest.class,
  UpdatingTest.class,
  DeletingTest.class,
  FinishTest.class
})

public class AllTests {
  
}
