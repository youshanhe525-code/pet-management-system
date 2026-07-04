package pet_utf8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    public MainFrame() {
        setResizable(false);
        setTitle("宠物管理系统 - 主界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("欢迎使用宠物管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
        titleLabel.setForeground(new Color(60, 90, 150));
        titleLabel.setBounds(0, 0, 600, 100);
        contentPane.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 12, 12));
        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 150, 20, 150));

        // 宠物档案管理
        JButton petButton = new JButton("宠物档案管理");
        petButton.setBackground(Color.WHITE);
        petButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        petButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PetFrame().setVisible(true);
            }
        });
        buttonPanel.add(petButton);

        // 主人信息管理
        JButton ownerButton = new JButton("主人信息管理");
        ownerButton.setBackground(Color.WHITE);
        ownerButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        ownerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new OwnerFrame().setVisible(true);
            }
        });
        buttonPanel.add(ownerButton);

        // 打开聊天服务端
        JButton serverButton = new JButton("聊天（服务端）");
        serverButton.setBackground(Color.WHITE);
        serverButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        serverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ChatServer().setVisible(true);
            }
        });
        buttonPanel.add(serverButton);

        // 打开聊天客户端
        JButton clientButton = new JButton("聊天（客户端）");
        clientButton.setBackground(Color.WHITE);
        clientButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        clientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ChatClient().setVisible(true);
            }
        });
        buttonPanel.add(clientButton);

        // 退出系统
        JButton exitButton = new JButton("退出系统");
        exitButton.setBackground(Color.WHITE);
        exitButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(exitButton);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
    }
}