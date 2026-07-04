package pet_utf8;

import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OwnerFrame extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;

    public static void main(String[] args) {
        new OwnerFrame().setVisible(true);
    }

    public OwnerFrame() {
        setResizable(false);
        setTitle("主人信息管理");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel l1 = new JLabel("姓名：");
        l1.setBounds(20, 20, 50, 25);
        contentPane.add(l1);
        nameField = new JTextField();
        nameField.setBounds(70, 20, 120, 25);
        contentPane.add(nameField);

        JLabel l2 = new JLabel("电话：");
        l2.setBounds(210, 20, 50, 25);
        contentPane.add(l2);
        phoneField = new JTextField();
        phoneField.setBounds(260, 20, 120, 25);
        contentPane.add(phoneField);

        JLabel l3 = new JLabel("地址：");
        l3.setBounds(400, 20, 50, 25);
        contentPane.add(l3);
        addressField = new JTextField();
        addressField.setBounds(450, 20, 180, 25);
        contentPane.add(addressField);

        JButton addButton = new JButton("添加");
        addButton.setBounds(20, 60, 80, 30);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { addOwner(); }
        });
        contentPane.add(addButton);

        JButton updateButton = new JButton("修改");
        updateButton.setBounds(115, 60, 80, 30);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { updateOwner(); }
        });
        contentPane.add(updateButton);

        JButton deleteButton = new JButton("删除");
        deleteButton.setBounds(210, 60, 80, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { deleteOwner(); }
        });
        contentPane.add(deleteButton);

        JButton queryButton = new JButton("查询全部");
        queryButton.setBounds(305, 60, 90, 30);
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { loadData(); }
        });
        contentPane.add(queryButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("编号");
        tableModel.addColumn("姓名");
        tableModel.addColumn("电话");
        tableModel.addColumn("地址");

        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 105, 650, 335);
        contentPane.add(scrollPane);

        table.getSelectionModel().addListSelectionListener(e -> fillFields());

        loadData();
    }

    // 查询全部
    private void loadData() {
        tableModel.setRowCount(0);
        try (SqlSession session = MyBatisUtil.getSession()) {
            OwnerMapper mapper = session.getMapper(OwnerMapper.class);
            List<Owner> list = mapper.selectAll();
            for (Owner o : list) {
                Object[] row = {
                        o.getOid(), o.getOname(), o.getPhone(), o.getAddress()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 添加
    private void addOwner() {
        String name = nameField.getText();
        if (name.equals("")) {
            JOptionPane.showMessageDialog(this, "姓名不能为空！");
            return;
        }
        try (SqlSession session = MyBatisUtil.getSession()) {
            Owner o = new Owner();
            o.setOname(name);
            o.setPhone(phoneField.getText());
            o.setAddress(addressField.getText());
            session.getMapper(OwnerMapper.class).insert(o);
            JOptionPane.showMessageDialog(this, "添加成功！");
            loadData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "添加失败：" + e.getMessage());
        }
    }

    // 修改
    private void updateOwner() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选中要修改的主人！");
            return;
        }
        int oid = (int) tableModel.getValueAt(selectedRow, 0);
        try (SqlSession session = MyBatisUtil.getSession()) {
            Owner o = new Owner();
            o.setOid(oid);
            o.setOname(nameField.getText());
            o.setPhone(phoneField.getText());
            o.setAddress(addressField.getText());
            session.getMapper(OwnerMapper.class).update(o);
            JOptionPane.showMessageDialog(this, "修改成功！");
            loadData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "修改失败：" + e.getMessage());
        }
    }

    // 删除
    private void deleteOwner() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选中要删除的主人！");
            return;
        }
        int oid = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除吗？");
        if (confirm != JOptionPane.YES_OPTION) return;

        try (SqlSession session = MyBatisUtil.getSession()) {
            session.getMapper(OwnerMapper.class).delete(oid);
            JOptionPane.showMessageDialog(this, "删除成功！");
            loadData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "删除失败：" + e.getMessage());
        }
    }

    private void fillFields() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        nameField.setText(tableModel.getValueAt(row, 1).toString());
        phoneField.setText(tableModel.getValueAt(row, 2).toString());
        addressField.setText(tableModel.getValueAt(row, 3).toString());
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
    }
}