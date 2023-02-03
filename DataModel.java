import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.MongoClient;


public class DataModel {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DBCollection collection = mongoClient.getDB( "mydb" ).getCollection("datamodel");
		System.out.println(mongoClient.getDB( "mydb" ).getStats());
		System.out.println(collection.getStats());
//		System.out.println("Before Drop Collection");
//		dropCollection(collection);	
//		System.out.println("After Drop Collection");
//		createEmbeddedRelationship(collection);
//		createReferenceRelationship(collection);
//		createDBRefRelationship();
		
		
	}
	
	static void dropCollection(DBCollection collection)
	{
		collection.remove(new BasicDBObject());
		
	}
	
	static void createDBRefRelationship()throws Exception
	{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB  baseDB=mongoClient.getDB( "mydb" );
		DBCollection collection = baseDB.getCollection("dbref");
		DBCollection collection_1 = mongoClient.getDB( "mydb" ).getCollection("dbreferencing");
		
		collection.remove(new BasicDBObject());
		collection_1.remove(new BasicDBObject());
		
		BasicDBObject addressObj=new BasicDBObject();
		addressObj.put("name", "x");
		addressObj.put("_id", 100);
		collection.insert(addressObj);
		
		
		
		DBRef addressRef = new DBRef(baseDB, "dbref", addressObj.get("_id"));
		
		
		
		BasicDBObject addressObj_1=new BasicDBObject();
		addressObj_1.put("_id", 1);
		addressObj_1.put("address", addressRef);
		collection_1.save(addressObj_1);
		
		DBObject fred = collection_1.findOne();
		System.out.println(fred);
		DBRef address = (DBRef)fred.get("address");
		System.out.println(address.getId());
	}
	
	static void createReferenceRelationship(DBCollection collection)
	{
		BasicDBObject addressObj=new BasicDBObject();
		addressObj.put("building", "170 A, Acropolis Apt");
		addressObj.put("pincode", 456789);
		addressObj.put("city", "Chicago");
		addressObj.put("state", "Illinois");		
		
		collection.insert(addressObj);
		
		BasicDBObject addressObj1=new BasicDBObject();
		addressObj1.put("building", "170 A, Acropolis Apt");
		addressObj1.put("pincode", 456789);
		addressObj1.put("city", "Chicago");
		addressObj1.put("state", "Illinois");		
		
		collection.insert(addressObj1);
		
		
		BasicDBObject basicObj=new BasicDBObject();
		basicObj.put("name", "Bhargav");
		basicObj.put("age",100);
		basicObj.put("salary", 1000);
		
		basicObj.put("address",Arrays.asList(addressObj.get("_id"),addressObj1.get("_id")));
		collection.insert(basicObj);
		
		System.out.println(basicObj);
		
	}
	
	static void createEmbeddedRelationship(DBCollection collection)
	{
		BasicDBObject basicObj=new BasicDBObject();
		basicObj.put("name", "Bhargav");
		basicObj.put("age",100);
		basicObj.put("salary", 1000);
		BasicDBObject addressObj=new BasicDBObject();
		addressObj.put("building", "170 A, Acropolis Apt");
		addressObj.put("pincode", 456789);
		addressObj.put("city", "Chicago");
		addressObj.put("state", "Illinois");
		
		basicObj.put("Address",addressObj);
		
		 List<BasicDBObject> booksRead = new ArrayList<BasicDBObject>();
		 BasicDBObject book1 = new BasicDBObject();
		 book1.put("name", "The Immortals of Meluha");
		 book1.put("authorName", "Amish Tripathi");
		 book1.put("publishedBy", "Westland Press");
		 booksRead.add(book1);

		 BasicDBObject book2 = new BasicDBObject();
		
		 book2.put("name", "The Krishna Key");
		 book2.put("authorName", "Ashwin Sanghi");
		 book2.put("publishedBy", "Westland Ltd");
		 booksRead.add(book2);

		 BasicDBObject book3 = new BasicDBObject();
		 book3.put("name", "Sita: An Illustrated Retelling of Ramayana");
		 book3.put("authorName", "Devdutt Pattanaik");
		 book3.put("publishedBy", "Mehata Publishing Ltd");
		 booksRead.add(book3);

		 basicObj.put("BooksRead", booksRead);
	
		collection.insert(basicObj);
		
		System.out.println(collection.findOne());

	}

}
