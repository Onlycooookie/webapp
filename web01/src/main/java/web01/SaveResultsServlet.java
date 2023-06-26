package web01;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveResultsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] selectedResults = request.getParameterValues("result");
        saveToFile(selectedResults);
        
        //�ض��򵽱���ɹ�ҳ��
        response.sendRedirect("saveSuccess.jsp");
    }
    
    private void saveToFile(String[] selectedResults) throws IOException {
        String filePath = "file.txt";  //ָ�������ļ���·�����ļ���
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        if (selectedResults != null) {
            for (String result : selectedResults) {
                writer.write(result + "\n");
            }
        }
        writer.close();
    }
}

