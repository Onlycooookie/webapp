package web01;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Download {
    public void downloadToFile(String[] selectedResults) {
        String filePath = "file.txt";

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            if (selectedResults != null) {
                for (String result : selectedResults) {
                    writer.write(result);
                    writer.newLine();
                    
                    //将选中结果下的所有元素写入文件
                    String[] elements = getElementsForResult(result);
                    for (String element : elements) {
                        writer.write(element);
                        writer.newLine();
                    }
                }
            }

            writer.close();
            fileWriter.close();

            System.out.println("下载成功");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取元素列表
    private String[] getElementsForResult(String result) {
        if ("路径1".equals(result)) {
            return new String[] {"元素1.1", "元素1.2", "元素1.3"};
        } 
        else if ("路径2".equals(result)) {
            return new String[] {"元素2.1", "元素2.2", "元素2.3"};
        } 
        else if ("路径3".equals(result)) {
            return new String[] {"元素3.1", "元素3.2", "元素3.3"};
        } 
        else {
            return new String[0];
        }
    }
}



