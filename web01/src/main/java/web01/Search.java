
//文件选择

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DiskExplorerServlet")
public class Search extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String selectedDir = request.getParameter("selectedDir");
        if (selectedDir != null) {
        	loadFolders(new File(selectedDir), response);
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//		String folderPath = request.getParameter("folderPath");
//        if (folderPath != null) {
//            exploreFiles(folderPath, response);
//            return;
//        }
//
//        // 获取磁盘列表
//        List<File> disks = loadFolders(new File("/")); // 指定根目录
//
//        // 将磁盘列表传递给 JSP 页面
//        request.setAttribute("disks", disks);
//        request.getRequestDispatcher("/index.jsp").forward(request, response);

        String action = request.getParameter("action");
        if ("confirm".equals(action)) {
            // 在这里处理确定按钮的逻辑，将文档名称和路径保存到数据库或其他存储方式中
            response.getWriter().println("文件已保存");
            return;
        }

        String folderPath = request.getParameter("folderPath");
        if (folderPath != null) {
            loadFolders(new File(folderPath), response);
            return;
        }

        String filePath = request.getParameter("filePath");
        if (filePath != null) {
            exploreFiles(filePath, response);
        }
    }

	
//	private List<File> loadFolders(File parentFolder) {
//        List<File> folders = new ArrayList<>();
//        if (parentFolder.exists() && parentFolder.isDirectory()) {
//            for (File subFolder : parentFolder.listFiles(File::isDirectory)) {
//                folders.add(subFolder);
//            }
//        }
//        return folders;
//    }
	
	private void loadFolders(File folder, HttpServletResponse response) throws IOException {
        if (folder.exists() && folder.isDirectory()) {
            StringBuilder sb = new StringBuilder();
            for (File subFolder : folder.listFiles(File::isDirectory)) {
                sb.append("<button onclick=\"exploreFolder('").append(subFolder.getAbsolutePath()).append("')\">")
                    .append(subFolder.getName()).append("</button><br>");
            }
            response.getWriter().println(sb.toString());
        }
    }

    private void exploreFiles(String folderPath, HttpServletResponse response) throws IOException {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
        	// 获取文件夹中的文档文件（.doc 和 .txt 和 .pdf）
        	File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".doc") || name.toLowerCase().endsWith(".txt") || name.toLowerCase().endsWith(".pdf");
                }
            });
            if (files != null) {
                StringBuilder sb = new StringBuilder();
                for (File file : files) {
                    sb.append(file.getAbsolutePath()).append("<br>");
                }
                // 将文件列表返回给客户端
                response.getWriter().println(sb.toString());
            }
        }
    }

}
