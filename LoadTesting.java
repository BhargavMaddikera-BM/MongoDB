import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class LoadTesting {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db=mongoClient.getDB( "mydb" );
		DBCollection collection = db.getCollection("crud");
		System.out.println("start");
		dropCollection(collection);	
		insertDocument(collection);
		System.out.println("The Number of Documents in Collection is  "+collection.getCount() );
		findDocument(collection);
		System.out.println("completed");
	}
	
	static void dropCollection(DBCollection collection)
	{
		collection.remove(new BasicDBObject());
	}
	
	
	static void insertDocument(DBCollection collection )
	{
		for(int i=0;i<10000000;i++)
		{
			BasicDBObject basicObj=new BasicDBObject();
			
			    basicObj.put("_id", i);
				basicObj.put("name", "Bhargav"+i);
				basicObj.put("age", i);
				basicObj.put("salary", i);
				basicObj.put("gender", "M");
				
				BasicDBObject addressObj=new BasicDBObject();
				addressObj.put("building", "170 A, Acropolis Apt");
				addressObj.put("pincode", 456789);
				addressObj.put("city", "Chicago");
				addressObj.put("state", "Illinois");				
				basicObj.put("Address",addressObj);
				
		
		
			collection.insert(basicObj);
		}	
	}
	
	static void findDocument(DBCollection collection )
	{
		//find some Documents
		BasicDBObject findObject=new BasicDBObject();	
		findObject.put("name", new BasicDBObject("$in", Arrays.asList("Bhargav9708906")));
		DBCursor dbCursor = collection.find(findObject);
	      DBObject record = null;
	      
	      while (dbCursor.hasNext())
	      {
	        record = dbCursor.next();	       
	        System.out.println("The name is "+record.get("name"));
	        System.out.println("The age is "+record.get("age").toString());
	        System.out.println("The salary is " +record.get("salary"));
	        System.out.println("The Address is " +record.get("Address"));
	      }
	      
	  
	      
		
	}

}
