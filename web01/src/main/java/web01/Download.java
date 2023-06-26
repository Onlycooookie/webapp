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
                    
                    //��ѡ�н���µ�����Ԫ��д���ļ�
                    String[] elements = getElementsForResult(result);
                    for (String element : elements) {
                        writer.write(element);
                        writer.newLine();
                    }
                }
            }

            writer.close();
            fileWriter.close();

            System.out.println("���سɹ�");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //��ȡԪ���б�
    private String[] getElementsForResult(String result) {
        if ("·��1".equals(result)) {
            return new String[] {"Ԫ��1.1", "Ԫ��1.2", "Ԫ��1.3"};
        } 
        else if ("·��2".equals(result)) {
            return new String[] {"Ԫ��2.1", "Ԫ��2.2", "Ԫ��2.3"};
        } 
        else if ("·��3".equals(result)) {
            return new String[] {"Ԫ��3.1", "Ԫ��3.2", "Ԫ��3.3"};
        } 
        else {
            return new String[0];
        }
    }
}



