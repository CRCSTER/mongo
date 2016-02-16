package crc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.crc.db.CRCConn;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class Data extends HttpServlet {


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String data = 
				"[" +
				    "{"+
						"\"ip\": \"127.0.0.1\", "+
						"\"method\": \"get\","+
						"\"kbs\":\"1231\","+
						"\"state\":\"304\""+
				   " },"+
				"]";
		JSONArray jsonArray = new JSONArray();
		List<DBObject> array = CRCConn.getConnection("users").find().toArray();
		for (DBObject dbObject : array) {
			JSONObject jsonObject = new JSONObject(dbObject.toMap());
			jsonArray.put(jsonObject);
		}
		
		resp.getWriter().write(jsonArray.toString());
		
	}
}
