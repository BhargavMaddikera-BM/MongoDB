import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;


public class CRUDOperations {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub

		List l=new ArrayList();
		ServerAddress srv=new ServerAddress("localhost");
	    l.add(srv);
	    
	    
	    
		MongoClient mongoClient = new MongoClient( l );
		DB db=mongoClient.getDB( "mydb" );
		DBCollection collection = db.getCollection("crud");
		System.out.println("start");
		dropCollection(collection);		
		insertDocument(collection);		
		findDocument(collection);
		
//		System.out.println("hai");
//		findDocument(collection);
//		updateDocument(collection);
//		findDocument(collection);
//		deleteDocument(collection);
//		aggregrateDocument(collection);
//		System.out.println("completed");
		
	}
	
	static void dropCollection(DBCollection collection)
	{
		collection.remove(new BasicDBObject());
	}
	
	static void aggregrateDocument(DBCollection collection)
	{
		DBObject projectFields = new BasicDBObject("name",1);
		projectFields.put("salary", 1);
	    DBObject project = new BasicDBObject("$project", projectFields);
		
		DBObject groupIdFields = new BasicDBObject("_id","$name");
	    groupIdFields.put("DisplaySum", new BasicDBObject("$sum", "$salary"));
	    groupIdFields.put("Count", new BasicDBObject("$sum",1));
	    DBObject group = new BasicDBObject("$group", groupIdFields);

	    DBObject sort = new BasicDBObject("$sort", new BasicDBObject("_id",1)); 
	    List<DBObject> pipeline = Arrays.asList(project, group, sort);
	    
	    AggregationOutput output = collection.aggregate(pipeline);
       for(DBObject result : output.results()){
    	     System.out.println(result);
       }
       
	    
	}
	
	static void insertDocument(DBCollection collection )
	{
		System.out.println("Before Insert");
		for(int i=0;i<10;i++)
		{
			BasicDBObject basicObj=new BasicDBObject();
			
			
			if(i<=300000)
			{ 
				basicObj.put("name", "Bhargav");
				basicObj.put("age", 10);
				basicObj.put("salary", 100);
				
			}
			else if(i<700000)
			{
				basicObj.put("name", "Bhargav1");
				basicObj.put("age", 20);
				basicObj.put("salary", 200);
			}
			else
			{
				basicObj.put("name", "Bhargav");
				basicObj.put("age", 30);
				basicObj.put("salary", 300);	
			}
			collection.insert(basicObj);
		}	
	}
	
	static void deleteDocument(DBCollection collection )
	{
		BasicDBObject removeObject=new BasicDBObject();	
		removeObject.put("name", new BasicDBObject("$in", Arrays.asList("Bhargav0","Bhargav3","Bhargav7")));
		collection.remove(removeObject);
	}

	static void updateDocument(DBCollection collection )
	{
		BasicDBObject query=new BasicDBObject();
		query.put("name", "Bhargav");
		
		BasicDBObject newDocument = new BasicDBObject();
	    newDocument.put("age", 111);
	    newDocument.put("gender", 1);
	  
	    BasicDBObject updateObj = new BasicDBObject();
	    updateObj.put("$set", newDocument);
	collection.update(query, updateObj);
	//check unset and inc operator
	
		
		
	}
	
	static void findDocument(DBCollection collection )
	{
		//find some Documents
		BasicDBObject findObject=new BasicDBObject();	
		findObject.put("name", new BasicDBObject("$in", Arrays.asList("Bhargav")));
		DBCursor dbCursor = collection.find(findObject);
	      DBObject record = null;
	      
	      while (dbCursor.hasNext())
	      {
	        record = dbCursor.next();	       
	        System.out.println(record.get("name"));
	        System.out.println(record.get("age"));
	        System.out.println(record.get("gender"));
	      }
	      
	  
	      
		
	}
}
