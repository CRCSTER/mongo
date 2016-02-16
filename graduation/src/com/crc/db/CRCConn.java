package com.crc.db;

import java.net.UnknownHostException;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class CRCConn {

	public static DBCollection getConnection(String conneName) {

		Mongo mongo = null;
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
		DB db = mongo.getDB("first");
		DBCollection collection = db.getCollection(conneName);
		return collection;
	}
	
}
