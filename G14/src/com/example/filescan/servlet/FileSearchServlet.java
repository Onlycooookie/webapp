package com.example.filescan.servlet;

import com.example.filescan.entity.CustomFile;
import com.example.filescan.entity.SearchResult;
import com.example.filescan.entity.SearchResultItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class FileSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 将请求转发到搜索页面
        req.getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        req.setAttribute("keyword", keyword);
        // 获取文件索引
        List<CustomFile> list = (List<CustomFile>) this.getServletContext().getAttribute("fileIndex");
        List<SearchResult> resultList = new ArrayList<>();
        if (list != null) {
            // 遍历文件索引
            for (CustomFile customFile : list) {
                // 在文件的段落中搜索关键词
                SearchResult searchResult = new SearchResult();
                List<String> paragraph = customFile.getParagraph();
                // 遍历段落中的每一行
                for (int i = 0; i < paragraph.size(); i++) {
                    String s = paragraph.get(i);
                    if (s.contains(keyword)){
                        // 创建搜索结果项并添加到搜索结果中
                        SearchResultItem searchResultItem = new SearchResultItem();
                        searchResultItem.setContent(s);
                        searchResultItem.setLineNum(i);
                        searchResult.getList().add(searchResultItem);
                    }
                }
                if (searchResult.getList().size() > 0){
                    searchResult.setPath(customFile.getPath());
                    resultList.add(searchResult);
                }
            }
        }
        req.setAttribute("resultList", resultList);
        // 将请求转发到搜索页面
        req.getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(req, resp);
    }
}
