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
			// 1.����һ������������
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// ��ȡ��ʱ�ļ�·��
			String tempPath = this.getServletContext().getRealPath("/temp");
			factory.setRepository(new File(tempPath));
			// 2.�õ�һ��������
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			// 3.�����������������������н���
			List<FileItem> list = upload.parseRequest(request);
			// 4.����list���ϣ��õ�ÿ�������������
			for (FileItem item : list) {
				// 5.�ж�item������
				if (item.isFormField()) {
					// ��ͨ������
					String inputName = item.getFieldName();
					// String inputValue=item.getString("UTF-8");�൱���±�����
					String inputValue = item.getString();
					inputValue = new String(inputValue.getBytes("iso8859-1"),
							"UTF-8");

					System.out.println(inputName + "=" + inputValue);
				} else {
					// �ϴ�������
					String fileName = item.getName();// ��ȡ�ļ���
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
						item.delete();// ɾ����ʱ�ļ�
					}
				}
				request.setAttribute("message", "�ϴ��ɹ�");

			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "�ϴ�ʧ��");
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
