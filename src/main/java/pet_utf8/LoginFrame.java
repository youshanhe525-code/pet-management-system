package pet_utf8;

import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame frame = new LoginFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginFrame() {
        setResizable(false);
        setTitle("宠物管理系统 - 登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 280);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel titleLabel = new JLabel("宠物管理系统");
        titleLabel.setBounds(110, 20, 200, 40);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        contentPane.add(titleLabel);

        JLabel userLabel = new JLabel("用户名：");
        userLabel.setBounds(60, 90, 80, 25);
        userLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        contentPane.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(140, 90, 180, 28);
        contentPane.add(usernameField);

        JLabel passLabel = new JLabel("密  码：");
        passLabel.setBounds(60, 135, 80, 25);
        passLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        contentPane.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 135, 180, 28);
        contentPane.add(passwordField);

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(140, 185, 80, 30);
        loginButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        contentPane.add(loginButton);

        JButton resetButton = new JButton("重置");
        resetButton.setBounds(240, 185, 80, 30);
        resetButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        contentPane.add(resetButton);
    }

    // 登录：用 MyBatis 查用户名密码
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, "用户名和密码不能为空！");
            return;
        }

        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.login(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "登录成功！");
                new MainFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "登录出错：" + e.getMessage());
        }
    }
}