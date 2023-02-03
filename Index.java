import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class Index {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DBCollection collection = mongoClient.getDB( "mydb" ).getCollection("datamodel");
	//	normalIndex(collection);
		textIndex(collection);
			
		
	}
	static void textIndex(DBCollection collection)
	{
		// Find using the text index
		collection.createIndex(new BasicDBObject("content", "text"));
		collection.insert(new BasicDBObject("content", "textual content is"));
		collection.insert(new BasicDBObject("content", "additional content"));
		collection.insert(new BasicDBObject("content", "irrelevant content is"));
		BasicDBObject search = new BasicDBObject("$search", " content additional is");
		BasicDBObject textSearch = new BasicDBObject("$text", search);
		int matchCount = collection.find(textSearch).count();
		System.out.println("Text search matches: "+ matchCount);		
	}
	
	static void normalIndex(DBCollection collection)
	{
		BasicDBObject basicObj=new BasicDBObject();
		
		 BasicDBObject index = new BasicDBObject();
		 index.put("BooksRead", 1);
		
		collection.createIndex(index);

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
		System.out.println(collection.find(new BasicDBObject("BooksRead.publishedBy", 1)).explain());
	
		List<DBObject> dbj=collection.getIndexInfo();
		for(int i=0;i<dbj.size();i++)
		{
			System.out.println(dbj);
		}
	}

}
