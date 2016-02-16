//UploadServlet.java

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Tmp extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.创建一个解析器工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 获取临时文件路径
			String tempPath = this.getServletContext().getRealPath("/temp");
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
						fileName = fileName.substring(fileName
								.lastIndexOf("\\") + 1);
						String saveName = this.generateFileName(fileName);
						InputStream in = item.getInputStream();
						String savePath = this.getServletContext().getRealPath(
								"WEB-INF/upload");
						String savePaths = this.generateFilePath(savePath,
								fileName);
						FileOutputStream out = new FileOutputStream(savePaths
								+ "\\" + fileName);
						byte[] buf = new byte[1024];
						int len = 0;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						in.close();
						out.close();
						item.delete();// 删除临时文件
					}
				}
				request.setAttribute("message", "上传成功");

			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "上传失败");
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
