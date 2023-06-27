package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileSearchUI {
    private JFrame frame;
    private JTextField folderTextField;
    private JTextField keywordTextField;
    private JTextArea resultTextArea;

    public FileSearchUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblFolderPath = new JLabel("目录路径:");
        lblFolderPath.setBounds(10, 20, 80, 14);
        frame.getContentPane().add(lblFolderPath);

        JLabel lblKeyword = new JLabel("关键字:");
        lblKeyword.setBounds(10, 50, 80, 14);
        frame.getContentPane().add(lblKeyword);

        folderTextField = new JTextField();
        folderTextField.setBounds(100, 20, 250, 20);
        frame.getContentPane().add(folderTextField);
        folderTextField.setColumns(10);

        keywordTextField = new JTextField();
        keywordTextField.setBounds(100, 50, 250, 20);
        frame.getContentPane().add(keywordTextField);
        keywordTextField.setColumns(10);

        JButton btnSearch = new JButton("搜索");
        btnSearch.setBounds(360, 20, 80, 50);
        frame.getContentPane().add(btnSearch);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setBounds(10, 80, 460, 170);
        frame.getContentPane().add(scrollPane);

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String folderPath = folderTextField.getText();
                String keyword = keywordTextField.getText();

                FileSearch searchApp = new FileSearch();
                String results = searchApp.searchFiles(folderPath, keyword);
                resultTextArea.setText(results);
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        FileSearchUI appUI = new FileSearchUI();
        appUI.show();
    }
}
