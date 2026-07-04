package pet_utf8;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer extends JFrame {

    private JTextField inputField;   // 输入框
    private JTextArea chatArea;      // 聊天记录显示区
    private PrintWriter writer;      // 用来发消息
    private BufferedReader reader;   // 用来收消息

    public static void main(String[] args) {
        new ChatServer().setVisible(true);
    }

    public ChatServer() {
        setTitle("Server（服务端）");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
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

        // 启动服务器（在新线程里，避免卡住界面）
        new Thread(this::startServer).start();
    }

    // 开启服务器，等客户端连接
    private void startServer() {
        try {
            chatArea.append("服务器已启动，等待客户端连接...\n");
            ServerSocket serverSocket = new ServerSocket(8888); // 守着 8888 端口
            Socket socket = serverSocket.accept(); // 等到有人连进来
            chatArea.append("客户端已连接！\n");

            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

            // 循环不停地读对方发来的消息
            String line;
            while ((line = reader.readLine()) != null) {
                chatArea.append("Client: " + line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 发消息
    private void sendMessage() {
        String text = inputField.getText();
        if (text.equals("") || writer == null) return;
        writer.println(text);
        chatArea.append("Server: " + text + "\n");
        inputField.setText("");
    }
}