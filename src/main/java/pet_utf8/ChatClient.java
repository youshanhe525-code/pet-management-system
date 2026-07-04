package pet_utf8;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends JFrame {

    private JTextField inputField;
    private JTextArea chatArea;
    private PrintWriter writer;
    private BufferedReader reader;

    public static void main(String[] args) {
        new ChatClient().setVisible(true);
    }

    public ChatClient() {
        setTitle("Client（客户端）");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(600, 100, 450, 350);
        setLayout(null);

        inputField = new JTextField();
        inputField.setBounds(20, 20, 250, 30);
        add(inputField);

        JButton sendButton = new JButton("Send");
        sendButton.setBounds(300, 20, 100, 30);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { sendMessage(); }
        });
        add(sendButton);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setBounds(20, 70, 380, 220);
        add(scroll);

        // 连接服务器（在新线程里）
        new Thread(this::connectServer).start();
    }

    // 主动连接服务器
    private void connectServer() {
        try {
            chatArea.append("正在连接服务器...\n");
            Socket socket = new Socket("localhost", 8888); // 连本机的 8888 端口
            chatArea.append("已连接到服务器！\n");

            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                chatArea.append("Server: " + line + "\n");
            }
        } catch (Exception e) {
            chatArea.append("连接失败：" + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String text = inputField.getText();
        if (text.equals("") || writer == null) return;
        writer.println(text);
        chatArea.append("Client: " + text + "\n");
        inputField.setText("");
    }
}