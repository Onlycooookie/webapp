package com.example.filescan.util;

import com.example.filescan.entity.CustomFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileReaderUtil {

    public static List<String> readWordDocument(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XWPFDocument document = new XWPFDocument(fis);
        List<String> paragraphs = new ArrayList<>();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            String text = paragraph.getText();
            if (!text.isEmpty()) {
                paragraphs.add(text);
            }
        }
        fis.close();
        return paragraphs;
    }

    public static List<String> readPdfDocument(String filePath) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        String[] paragraphs = text.split("\\r?\\n");
        document.close();
        List<String> nonEmptyParagraphs = new ArrayList<>();
        for (String paragraph : paragraphs) {
            if (!paragraph.trim().isEmpty()) {
                nonEmptyParagraphs.add(paragraph);
            }
        }
        return nonEmptyParagraphs;
    }

    public static List<String> readTextFile(String filePath) throws IOException {
        File file = new File(filePath);
        InputStream inputStream = Files.newInputStream(file.toPath());
        byte[] head = new byte[3];
        inputStream.read(head);
        String code = "GBK" ;
        if (head[0] == -1 && head[1] == -2) {
            code = "UTF-16" ;
        } else if (head[0] == -2 && head[1] == -1) {
            code = "Unicode" ;
        } else if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
            code = "UTF-8" ;
        } else {
            byte[] text = new byte[(int) file.length()];
            System.arraycopy(head, 0, text, 0, 3);
            inputStream.read(text, 3, text.length - 3);
            for (int i = 0; i < text.length; i++) {
                int a = text[i] & 0xFF;
                int b = text[i + 1] & 0xFF;
                if (a > 0x7F) {//排除开头的英文或者数字字符
                    if (0xE3 < a & a < 0xE9 && b > 0x7F && b < 0xC0) {//符合utf8
                        code = "UTF-8" ;
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        System.out.println(code);
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), code);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt;
        List<String> lines = new ArrayList<>();
        while ((lineTxt = bufferedReader.readLine()) != null) {
            //System.out.println(lineTxt);
            lines.add(lineTxt);
        }
        read.close();
        return lines;
    }

    public static List<CustomFile> scanDir(String directoryPath) throws IOException, InterruptedException {
        List<CustomFile> filePaths = new ArrayList<>();
        File directory = new File(directoryPath);
        // 创建文件过滤器，过滤PDF和Word文件
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                String fileName = file.getName().toLowerCase();
                return fileName.endsWith(".pdf") || fileName.endsWith(".doc") || fileName.endsWith(".docx")
                        || fileName.endsWith(".txt");
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // 递归遍历目录并获取满足过滤条件的文件路径
        scanDirectory(directory, fileFilter, filePaths, executorService);

        // 关闭线程池，等待所有线程完成任务
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        return filePaths;
    }

    private static void scanDirectory(File directory, FileFilter fileFilter, List<CustomFile> filePaths,
                                      ExecutorService executorService) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归遍历子目录
                    scanDirectory(file, fileFilter, filePaths, executorService);
                } else if (fileFilter.accept(file)) {
                    String filename = file.getName();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CustomFile customFile = new CustomFile();
                                customFile.setPath(file.getAbsolutePath());
                                if (filename.endsWith(".docx") || filename.endsWith(".doc")) {
                                    customFile.setParagraph(readWordDocument(customFile.getPath()));
                                } else if (filename.endsWith(".pdf")) {
                                    customFile.setParagraph(readPdfDocument(customFile.getPath()));
                                } else if (filename.endsWith(".txt")) {
                                    customFile.setParagraph(readTextFile(customFile.getPath()));
                                }
                                filePaths.add(customFile);
                            } catch (Exception e) {
//                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * 返回盘符集合
     *
     * @return
     */
    public static List<String> getAvailableDrivePaths() {
        List<String> drivePaths = new ArrayList<>();
        File[] roots = File.listRoots();
        for (File root : roots) {
            drivePaths.add(root.getAbsolutePath());
        }
        return drivePaths;
    }
}



//
package com.example.filescan.util;

import com.example.filescan.entity.CustomFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileReaderUtil {

    public static List<String> readWordDocument(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XWPFDocument document = new XWPFDocument(fis);
        List<String> paragraphs = new ArrayList<>();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            String text = paragraph.getText();
            if (!text.isEmpty()) {
                paragraphs.add(text);
            }
        }
        fis.close();
        return paragraphs;
    }

    public static List<String> readPdfDocument(String filePath) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        String[] paragraphs = text.split("\\r?\\n");
        document.close();
        List<String> nonEmptyParagraphs = new ArrayList<>();
        for (String paragraph : paragraphs) {
            if (!paragraph.trim().isEmpty()) {
                nonEmptyParagraphs.add(paragraph);
            }
        }
        return nonEmptyParagraphs;
    }

    public static List<String> readTextFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    public static List<CustomFile> scanDir(String directoryPath) throws IOException, InterruptedException {
        List<CustomFile> filePaths = new ArrayList<>();
        File directory = new File(directoryPath);
        // 创建文件过滤器，过滤PDF和Word文件
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                String fileName = file.getName().toLowerCase();
                return fileName.endsWith(".pdf") || fileName.endsWith(".doc") || fileName.endsWith(".docx")
                        || fileName.endsWith(".txt");
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(60);

        // 递归遍历目录并获取满足过滤条件的文件路径
        scanDirectory(directory, fileFilter, filePaths, executorService);

        // 关闭线程池，等待所有线程完成任务
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        return filePaths;
    }

    private static void scanDirectory(File directory, FileFilter fileFilter, List<CustomFile> filePaths,
                                      ExecutorService executorService) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归遍历子目录
                    scanDirectory(file, fileFilter, filePaths, executorService);
                } else if (fileFilter.accept(file)) {
                    String filename = file.getName();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CustomFile customFile = new CustomFile();
                                customFile.setPath(file.getAbsolutePath());
                                if (filename.endsWith(".docx") || filename.endsWith(".doc")) {
                                    customFile.setParagraph(readWordDocument(customFile.getPath()));
                                } else if (filename.endsWith(".pdf")) {
                                    customFile.setParagraph(readPdfDocument(customFile.getPath()));
                                } else if (filename.endsWith(".txt")) {
                                    customFile.setParagraph(readTextFile(customFile.getPath()));
                                }
                                filePaths.add(customFile);
                            } catch (Exception e) {
//                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * 返回盘符集合
     * @return
     */
    public static List<String> getAvailableDrivePaths() {
        List<String> drivePaths = new ArrayList<>();
        File[] roots = File.listRoots();
        for (File root : roots) {
            drivePaths.add(root.getAbsolutePath());
        }
        return drivePaths;
    }
}
