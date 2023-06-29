package com.example.filescan.servlet;

import com.example.filescan.entity.CustomFile;
import com.example.filescan.util.FileReaderUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查文件索引是否存在
        Object fileIndex = this.getServletContext().getAttribute("fileIndex");
        if (fileIndex == null) {
            // 获取可用的驱动器路径列表
            List<String> list = FileReaderUtil.getAvailableDrivePaths();
            req.getSession().setAttribute("driveList", list);
            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
        } else {
            // 如果文件索引已存在，则重定向到搜索页面
            resp.sendRedirect(req.getContextPath() + "/search");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("path");
        String type = req.getParameter("type");
        if ("confirm".equals(type) && !"".equals(path)){
            try {
                // 扫描指定路径下的文件并建立索引
                List<CustomFile> customFiles = FileReaderUtil.scanDir(path);
                this.getServletContext().setAttribute("fileIndex", customFiles);
                resp.sendRedirect(req.getContextPath() + "/search");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else{
            File[] files = null;
            if ("enter".equals(type) && path != null && !"".equals(path)){
                // 进入指定路径
                File file = new File(path);
                files = file.listFiles();
                req.getSession().setAttribute("path", path);
            }else if ("back".equals(type) && req.getSession().getAttribute("path") != null){
                // 返回上级路径
                File file = new File((String) req.getSession().getAttribute("path"));
                File parentFile = file.getParentFile();
                if (parentFile != null){
                    files = parentFile.listFiles();
                    req.getSession().setAttribute("path", parentFile.getAbsolutePath());
                }
            }
            List<String> list = new ArrayList<>();
            if (files != null){
                // 获取路径列表
                for (File file1 : files) {
                    if (file1.isDirectory()){
                        list.add(file1.getAbsolutePath());
                    }
                }
                req.getSession().setAttribute("driveList", list);
            }
            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
        }
    }
}
