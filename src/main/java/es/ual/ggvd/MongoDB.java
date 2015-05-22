package es.ual.ggvd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDB {

  private MongoClient mongoClient;
  private MongoDatabase db;

  /**
   * Create a connection to a MongoDB database
   * 
   * @param host
   *          MongoDB host
   * @param port
   *          MongoDB port number
   * @param database
   *          MongoDB database
   */
  public MongoDatabase connect(String host, int port, String database) {
    // Create serverAddress, the location a MongoDB server
    ServerAddress serverAddress = new ServerAddress(host, port);

    // Connect to the MongoDB server with internal connection pooling
    mongoClient = new MongoClient(serverAddress);
    
    // Getting a connection easily avoiding ServerAddress
    // mongoClient = new MongoClient(host, port);
    
    // Select the database
    db = mongoClient.getDatabase(database);

    return db;

  }

  /**
   * Create a connection to a MongoDB database using defaults: (host: localhost;
   * port: 27017)
   * 
   * @param database
   *          MongoDB database
   */
  public MongoDatabase connect(String database) {
    // Connect to MongoDB and select the database using defaults
    return connect("localhost", 27017, database);
  }

  /**
   * Close the MongoDB connection
   */
  public void disconnect() {
    mongoClient.close();
  }

  /**
   * Insert a document with one key in a collection
   * 
   * @param collection
   *          The name of the collection
   * @param key
   *          The key of the document
   * @param value
   *          The value of the key
   */
  public void insertDocument(String collection, String key, Object value) {

    // Create the document to insert
    Document document = new Document(key, value);

    // Select the collection
    MongoCollection<Document> col = db.getCollection(collection);

    // Insert the document in the collection
    col.insertOne(document);
  }
  
  /**
   * Insert into a collection a key as many times as it has been specified.
   * The value of the kay will be generated in ascending order starting from 1.
   *   
   * @param collection  The name of the collection
   * @param key The key of the document
   * @param howMany The number of instances to be inserted
   */
  
  public void insertDocuments(String collection, String key, int howMany) {
    // Create the list of documents to insert
    List<Document> documents = new ArrayList<Document>();

    // Select the collection
    MongoCollection<Document> col = db.getCollection(collection);

    // Add new documents to the list of documents
    for (int i = 1; i <= howMany; i++) {
      documents.add(new Document(key, i));
    }

    // Insert the list of documents in the collection
    col.insertMany(documents);
  }

  /**
   * Insert a document in the "people" collection
   * 
   * @param name
   *          person name
   * @param email
   *          personal email
   * @param twitter
   *          personal twitter
   * @param hobbies
   *          ArrayList of hobbies
   * @param city
   *          city
   * @param zip
   *          zip
   */
  public void insertPeople(String name, String email, String twitter,
      ArrayList<String> hobbies, String city, String zip) {

    // Create the document to insert
    Document document = new Document("name", name).append("email", email)
        .append("twitter", twitter).append("hobbies", hobbies)
        .append("location", new Document("city", city).append("zip", zip));

    // Select the "people" collection
    MongoCollection<Document> collection = db.getCollection("people");

    // Insert the document in the "people" collection
    collection.insertOne(document);
  }
  
  /**
   * Find in a collection a document having a key and a value
   * 
   * @param collection  The collection where we are going to search
   * @param key The key to be tested
   * @param value The value to be found
   * @return  The first document satisfying the search criteria 
   */
  public String findDocument(String collection, String key, String value) {
    // Select the collection
    MongoCollection<Document> col = db.getCollection(collection);

    // Create the document to specify find criteria
    Document findDocument = new Document(key, value);

    // Document to store query results
    FindIterable<Document> resultDocument = col.find(findDocument);

    // Return the name of the first returned document
    return resultDocument.first().toJson();

  }

  /**
   * Show People by email
   * 
   * @param email
   *          personal email to find
   */
  public void showPeopleByEmail(String email) {
    // Select the "people" collection
    MongoCollection<Document> collection = db.getCollection("people");

    // Create the document to specify find criteria
    Document findDocument = new Document("email", email);

    // Document to store query results
    MongoCursor<Document> resultDocument = collection.find(findDocument).iterator();

    System.out.println("*** Listing People By Email: " + email);
    
    // Iterate over the results printing each document
    while (resultDocument.hasNext()) {
      System.out.println(resultDocument.next().getString("name"));
    }
  }

  /**
   * Find People by email
   * 
   * @param email
   *          personal email to find
   * @return The name of the people who matches the search criteria
   */
  public String findPeopleByEmail(String email) {
    // Select the "people" collection
    MongoCollection<Document> collection = db.getCollection("people");

    // Create the document to specify find criteria
    Document findDocument = new Document("email", email);

    // Document to store query results
    MongoCursor<Document> resultDocument = collection.find(findDocument).iterator();

    // Return the name of the first returned document
    if (resultDocument.hasNext()) {
      return resultDocument.next().getString("name");
    }
    else {
      return null;
    }

  }

  /**
   * Shows a list of people (limit 5) living in a location.
   * Listing is sorted by name, limited to five people, and only location.zip and name are shown
   *  
   * @param location
   */
  public void sortedAndProjectedFindPeople(String location) {
    // Select the "people" collection
    MongoCollection<Document> collection = db.getCollection("people");

    // Create the document to specify find criteria
    Document findDocument = new Document("location.city", location);

    // Create the document to specify sort criteria
    Document sortingDocument = new Document("name", 1);

    // Create the document to specify projection criteria
    Document projectionDocument = new Document("location", 1).append("name", 1);

    // Document to store query results
    MongoCursor<Document> resultDocument = collection.find(findDocument)
        .sort(sortingDocument).projection(projectionDocument).limit(5).iterator();

    System.out.println("*** Listing 5 people who lives in " + location);
    
    while (resultDocument.hasNext()) {
      Document document = resultDocument.next();

      System.out.println(document.getString("name") + " " +
          ((Document) document.get("location")).getString("zip"));  
          // "location.zip" cannot be directy accessed
          // Instead of, we get "location" and after get the "zip" inside "location" 
    }
  }

  /**
   * Remove People by email
   * 
   * @param email The email of people to be removed
   */
  public void removePeople(String email) {
    // Select the "people" collection
    MongoCollection<Document> collection = db.getCollection("people");

    // Create the document to specify find criteria
    Document findDocument = new Document("email", email);

    // Find one person and delete
    collection.findOneAndDelete(findDocument);
  }

  /**
   * Update the name of people 
   * 
   * @param email The email of people to be modified
   * @param newName The new name of the people
   */
  public void updatePeople(String email, String newName) {
    // Select the "people" collection
    MongoCollection<Document> collection = db.getCollection("people");

    // Create the document to specify find criteria
    Document findDocument = new Document("email", email);

    // Create the document to specify the update
    Document updateDocument = new Document("$set",
        new Document("name", newName));

    // Find one person and delete
    collection.findOneAndUpdate(findDocument, updateDocument);
  }
  
  /**
   * Method to setup a collection with some film stars for an aggregation example
   */
  public void setupActors() {
    // Select the "actor" collection
    MongoCollection<Document> collection = db.getCollection("actor");
    
    // Delete the previous content
    collection.drop();
    
    // The film stars to be added 
    collection.insertOne(new Document("first_name", "Elisabeth").
        append("last_name", "Taylor").
        append("country", "UK").
        append("born", 1932).
        append("genre", "female"));
    collection.insertOne(new Document("first_name", "James").
        append("last_name", "Dean").
        append("country", "USA").
        append("born", 1931).
        append("genre", "male"));
    collection.insertOne(new Document("first_name", "Rock").
        append("last_name", "Hudson").
        append("country", "USA").
        append("born", 1925).
        append("genre", "male"));
    collection.insertOne(new Document("first_name", "Caroll").
        append("last_name", "Baker").
        append("country", "USA").
        append("born", 1931).
        append("genre", "female"));
  }
  
  /**
   * Shows the total of actors by genre using the framework aggregation
   */
  public void aggregate() {
    
    // Select the "actor" collection
    MongoCollection<Document> collection = db.getCollection("actor");
    
    // Create our pipeline operations, first with the $match
    Document match = new Document("$match", new Document("country", "USA"));

    // Now the $group operation
    Document groupFields = new Document( "_id", "$genre").
        append("total", new Document( "$sum", 1));
    Document group = new Document("$group", groupFields);

    // run aggregation
    List<Document> pipeline = Arrays.asList(match, group);
    AggregateIterable<Document> output = collection.aggregate(pipeline);
    
    for (Document document : output) {
      System.out.println(document.toJson());
      
    }
  }

}
