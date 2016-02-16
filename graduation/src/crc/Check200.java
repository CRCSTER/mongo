package crc;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.crc.db.CRCConn;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class Check200 extends HttpServlet {
	public int get200Count() {
		DBCollection dbCollection = CRCConn.getConnection("users");
		
		return (int)dbCollection.getCount(new BasicDBObject("state", "200"));
	}
	
	public int postCount() {
		DBCollection dbCollection = CRCConn.getConnection("users");
		return (int)dbCollection.getCount(new BasicDBObject("method", "post"));
	}
	
	public int reqCount() {
		DBCollection dbCollection = CRCConn.getConnection("users");
		return (int)dbCollection.getCount();
	}
	public static String maxIp(){
		DBCollection dbCollection = CRCConn.getConnection("users");
		
		
		DBObject keyDbObject = new BasicDBObject("key", "ip");
		DBObject initialDbObject = new BasicDBObject("data", 
				new BasicDBObject("data", "{}"));
		String reduceFunc = 
				"function(doc,prev) {" +
					" if(doc.ip in prev.data) { " +
						"prev.data[doc.ip] += 1; " +
					"} else { " +
						" prev.data[doc.ip] = 1; " +
					"}" +
				"}";
		
		DBObject group = dbCollection.group(
				keyDbObject, null, initialDbObject, reduceFunc
				);

		BasicDBObject basicDBObject = (BasicDBObject)group.get("0");
		basicDBObject = (BasicDBObject)basicDBObject.get("data");
		basicDBObject.remove("data");
		
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(basicDBObject.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return getMax(jsonObject);
	}
		
	public static String getMax(JSONObject jsonObject) {
		if (jsonObject == null) {
			return "";
		}
		Iterator<String> keys = jsonObject.keys();
		String ip = "";
		Double max = 0.0;
		while (keys.hasNext()) {
			String str = keys.next();
			Object object = null;
			try {
				object = jsonObject.get(str);
				String count = object.toString();
				Double cn = Double.valueOf(count);
				if (cn > max) {
					max = cn;
					ip = str;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		return ip;
	}
	/*
	public static void main(String[] args) throws JSONException {
		DBObject maxIp = maxIp();
		System.out.println(maxIp);
		BasicDBObject basicDBObject = (BasicDBObject)maxIp.get("0");
		basicDBObject = (BasicDBObject)basicDBObject.get("data");
		basicDBObject.remove("data");
		System.out.println(getMax(new JSONObject(basicDBObject.toString())));
	}*/
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws IOException {
		resp.setContentType("ContentType:text/plain;charset=utf-8");
		resp.getWriter().write(get200Count()+"");
	}
	
}
