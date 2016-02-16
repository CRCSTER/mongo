package crc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.UnknownHostException;  
import java.util.Date;  

import com.crc.db.CRCConn;
import com.mongodb.*;
import com.mongodb.util.JSON;
public class fileupload extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		upload(req);
		resp.sendRedirect("/graduation/test.jsp");
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
	}
	
	public void upload(HttpServletRequest request) {

		try {
			// 1.创建一个解析器工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 获取临时文件路径
			String tempPath = this.getServletContext().getRealPath("/temp");
			tempPath = "E:/upload";
			factory.setRepository(new File(tempPath));
			// 2.得到一个解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			// 3.将请求传入解析器，对请求进行解析
			List<FileItem> list = upload.parseRequest(request);
			// 4.迭代list集合，得到每个输入项的数据
			for (FileItem item : list) {
				// 5.判断item的类型
				if (item.isFormField()) {
					// 普通输入项
					String inputName = item.getFieldName();
					// String inputValue=item.getString("UTF-8");相当于下边两句
					String inputValue = item.getString();
					inputValue = new String(inputValue.getBytes("iso8859-1"),
							"UTF-8");

					System.out.println(inputName + "=" + inputValue);
				} else {
					// 上传输入项
					String fileName = item.getName();// 获取文件名
					if (!fileName.equals("")) {
						String origName = fileName;
						fileName = fileName.substring(fileName
								.lastIndexOf("\\") + 1);
						String saveName = this.generateFileName(fileName);
						InputStream in = item.getInputStream();
//						String savePath = this.getServletContext().getRealPath(
//								"WEB-INF/upload");
//						String savePaths = this.generateFilePath(savePath,
//								fileName);
//						FileOutputStream out = new FileOutputStream(savePaths
//								+ "\\" + fileName);
//						byte[] buf = new byte[1024];
//						int len = 0;
//						while ((len = in.read(buf)) > 0) {
//							out.write(buf, 0, len);
//						}
						
						InputStreamReader isr = new InputStreamReader(in);
						BufferedReader br = new BufferedReader(isr);
						StringBuffer sb = new StringBuffer();
						String tmp;
						while((tmp = br.readLine()) != null) {
							sb.append(tmp+"\r\n");
							tmp = null;
						}
						System.out.println("OrigName:" + origName);
						if (origName.endsWith("csv")) {
							saveCSV(sb.toString());
						} else if (origName.endsWith("json")) {
							JSONArray jsonArray = new JSONArray(sb.toString());
							save(jsonArray);	
						} else {
							System.out.println("文件名匹配错误，改程序吧/hanx");
						}
						in.close();
//						out.close();
						item.delete();// 删除临时文件
					}
				}
				request.setAttribute("message", "上传成功");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "上传失败");
		}
//		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	public void save(JSONArray jsonArray) throws UnknownHostException {
		DBCollection collection = CRCConn.getConnection("users");
		int size = jsonArray.length();
		JSONObject jsonObject = null;
		for(int i=0; i< size; i++) {
			jsonObject = jsonArray.optJSONObject(i);
			if (jsonObject != null) {
				collection.insert((DBObject)JSON.parse(jsonObject.toString()));
			}
		
		}
	}
	
	public void saveCSV(String csvValue) throws JSONException {
		DBCollection collection = CRCConn.getConnection("users");
//		int size = jsonArray.length();
		String elems[] = csvValue.split("\r\n");
		int size = elems.length;
		JSONObject jsonObject = null;
		System.out.println(Arrays.toString(elems));
		for(int i=0; i< size; i++) {
//			jsonObject = jsonArray.optJSONObject(i);
			jsonObject = new JSONObject();
			String vs[] = elems[i].split(",");
			jsonObject.put("ip", vs[0]);
			jsonObject.put("method", vs[1]);
			jsonObject.put("kbs", vs[2]);
			jsonObject.put("state", vs[3]);
			if (jsonObject != null) {
				collection.insert((DBObject)JSON.parse(jsonObject.toString()));
			}
		
		}
	}
	
	public String generateFileName(String filename) {
		return UUID.randomUUID().toString() + "_" + filename;
	}
	
	public String generateFilePath(String path, String filename) {
		int dir1 = filename.hashCode() & 0xf;
		int dir2 = (filename.hashCode() >> 4) & 0xf;

		String savePath = path + "\\" + dir1 + "\\" + dir2;
		File f = new File(savePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		return savePath;
	}
}
