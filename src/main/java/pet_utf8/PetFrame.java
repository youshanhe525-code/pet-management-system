package pet_utf8;

import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PetFrame extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField speciesField;
    private JTextField breedField;
    private JTextField sexField;
    private JTextField ageField;
    private JTextField statusField;

    public static void main(String[] args) {
        new PetFrame().setVisible(true);
    }

    public PetFrame() {
        setResizable(false);
        setTitle("宠物档案管理");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel l1 = new JLabel("名字：");
        l1.setBounds(20, 20, 50, 25);
        contentPane.add(l1);
        nameField = new JTextField();
        nameField.setBounds(70, 20, 100, 25);
        contentPane.add(nameField);

        JLabel l2 = new JLabel("种类：");
        l2.setBounds(190, 20, 50, 25);
        contentPane.add(l2);
        speciesField = new JTextField();
        speciesField.setBounds(240, 20, 100, 25);
        contentPane.add(speciesField);

        JLabel l3 = new JLabel("品种：");
        l3.setBounds(360, 20, 50, 25);
        contentPane.add(l3);
        breedField = new JTextField();
        breedField.setBounds(410, 20, 100, 25);
        contentPane.add(breedField);

        JLabel l4 = new JLabel("性别：");
        l4.setBounds(20, 55, 50, 25);
        contentPane.add(l4);
        sexField = new JTextField();
        sexField.setBounds(70, 55, 100, 25);
        contentPane.add(sexField);

        JLabel l5 = new JLabel("年龄：");
        l5.setBounds(190, 55, 50, 25);
        contentPane.add(l5);
        ageField = new JTextField();
        ageField.setBounds(240, 55, 100, 25);
        contentPane.add(ageField);

        JLabel l6 = new JLabel("状态：");
        l6.setBounds(360, 55, 50, 25);
        contentPane.add(l6);
        statusField = new JTextField();
        statusField.setBounds(410, 55, 100, 25);
        contentPane.add(statusField);

        JButton addButton = new JButton("添加");
        addButton.setBounds(20, 95, 80, 30);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { addPet(); }
        });
        contentPane.add(addButton);

        JButton updateButton = new JButton("修改");
        updateButton.setBounds(115, 95, 80, 30);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { updatePet(); }
        });
        contentPane.add(updateButton);

        JButton deleteButton = new JButton("删除");
        deleteButton.setBounds(210, 95, 80, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { deletePet(); }
        });
        contentPane.add(deleteButton);

        JButton queryButton = new JButton("查询全部");
        queryButton.setBounds(305, 95, 90, 30);
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { loadData(); }
        });
        contentPane.add(queryButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("编号");
        tableModel.addColumn("名字");
        tableModel.addColumn("种类");
        tableModel.addColumn("品种");
        tableModel.addColumn("性别");
        tableModel.addColumn("年龄");
        tableModel.addColumn("状态");

        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 140, 650, 300);
        contentPane.add(scrollPane);

        table.getSelectionModel().addListSelectionListener(e -> fillFields());

        loadData();
    }

    // 查询：用 MyBatis 查全部
    private void loadData() {
        tableModel.setRowCount(0);
        try (SqlSession session = MyBatisUtil.getSession()) {
            PetMapper mapper = session.getMapper(PetMapper.class);
            List<Pet> list = mapper.selectAll();
            for (Pet p : list) {
                Object[] row = {
                        p.getPid(), p.getPname(), p.getSpecies(), p.getBreed(),
                        p.getSex(), p.getAge(), p.getStatus()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 添加
    private void addPet() {
        String name = nameField.getText();
        if (name.equals("")) {
            JOptionPane.showMessageDialog(this, "名字不能为空！");
            return;
        }
        try (SqlSession session = MyBatisUtil.getSession()) {
            Pet p = new Pet();
            p.setPname(name);
            p.setSpecies(speciesField.getText());
            p.setBreed(breedField.getText());
            p.setSex(sexField.getText());
            p.setAge(Integer.parseInt(ageField.getText().equals("") ? "0" : ageField.getText()));
            p.setStatus(statusField.getText());
            session.getMapper(PetMapper.class).insert(p);
            JOptionPane.showMessageDialog(this, "添加成功！");
            loadData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "添加失败：" + e.getMessage());
        }
    }

    // 修改
    private void updatePet() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先在表格中选中要修改的宠物！");
            return;
        }
        int pid = (int) tableModel.getValueAt(selectedRow, 0);
        try (SqlSession session = MyBatisUtil.getSession()) {
            Pet p = new Pet();
            p.setPid(pid);
            p.setPname(nameField.getText());
            p.setSpecies(speciesField.getText());
            p.setBreed(breedField.getText());
            p.setSex(sexField.getText());
            p.setAge(Integer.parseInt(ageField.getText().equals("") ? "0" : ageField.getText()));
            p.setStatus(statusField.getText());
            session.getMapper(PetMapper.class).update(p);
            JOptionPane.showMessageDialog(this, "修改成功！");
            loadData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "修改失败：" + e.getMessage());
        }
    }

    // 删除
    private void deletePet() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先在表格中选中要删除的宠物！");
            return;
        }
        int pid = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除吗？");
        if (confirm != JOptionPane.YES_OPTION) return;

        try (SqlSession session = MyBatisUtil.getSession()) {
            session.getMapper(PetMapper.class).delete(pid);
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
        speciesField.setText(tableModel.getValueAt(row, 2).toString());
        breedField.setText(tableModel.getValueAt(row, 3).toString());
        sexField.setText(tableModel.getValueAt(row, 4).toString());
        ageField.setText(tableModel.getValueAt(row, 5).toString());
        statusField.setText(tableModel.getValueAt(row, 6).toString());
    }

    private void clearFields() {
        nameField.setText("");
        speciesField.setText("");
        breedField.setText("");
        sexField.setText("");
        ageField.setText("");
        statusField.setText("");
    }
}