package student.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import student.Final;
import student.DAO;
import student.base.BaseDAO;
import student.dao.StuDAO;
import student.table.Stu;

/**
 * 说明：更新学生信息
 */
public class UpdateView extends JFrame{
    private JPanel jpCenter, jpSouth;
    private JButton jbUpdate, jbExit;
    private JTextField name, sno, faulty, hometown,  email, tel, sex;

    public UpdateView() {
        init();
    }

    private void init() {
        setTitle(Final.UPDATE_TITLE);
        // center panel
        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(9, 2));
        jpCenter.add(new JLabel(Final.STUDENT_NAME));
        name = new JTextField();
        jpCenter.add(name);
        jpCenter.add(new JLabel(Final.STUDENT_SNO));
        sno = new JTextField();
        jpCenter.add(sno);
        jpCenter.add(new JLabel(Final.STUDENT_SEX));
        sex = new JTextField();
        jpCenter.add(sex);
        jpCenter.add(new JLabel(Final.STUDENT_FACULTY));
        faulty = new JTextField();
        jpCenter.add(faulty);
        jpCenter.add(new JLabel(Final.STUDENT_HOMETOWN));
        hometown = new JTextField();
        jpCenter.add(hometown);

        jpCenter.add(new JLabel(Final.STUDENT_EMAIL));
        email = new JTextField();
        jpCenter.add(email);
        jpCenter.add(new JLabel(Final.STUDENT_TEL));
        tel = new JTextField();
        jpCenter.add(tel);
        jpCenter.add(new JLabel("------------------------------------------------"));
        jpCenter.add(new JLabel("------------------------------------------------"));

        // south panel
        jpSouth = new JPanel();
        jpSouth.setLayout(new GridLayout(1, 2));
        jbUpdate = new JButton(Final.UPDATE);
        jbUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check()) {
                    Stu stu = new Stu();
                    buildStudent(stu);
                    boolean isSuccess = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO)).update(stu);
                    if (isSuccess) {
                        setEmpty();
                        if (MainView.currPageNum < 0 || MainView.currPageNum > 99) {
                            MainView.currPageNum = 1;
                        }
                        String[][] result = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO))
                                .list(MainView.currPageNum);
                        MainView.initJTable(MainView.jTable, result);
                    }else{
                        ErrorUpdate();
                    }
                }else{
                    ErrorMessage();
                }
            }
        });
        jpSouth.add(jbUpdate);
        jbExit = new JButton(Final.EXIT);
        jbExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jpSouth.add(jbExit);

        this.add(jpCenter, BorderLayout.CENTER);
        this.add(jpSouth, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(470, 200, 400, 270);
        setResizable(false);
        setVisible(true);
    }
    
    private boolean check() {
        boolean result = false;
        if ("".equals(name.getText()) || "".equals(sno.getText())) {
            return result;
        } else {
            result = true;
        }
        return result;
    }

    private void buildStudent(Stu stu) {
        stu.setFaculty(faulty.getText());
        stu.setEmail(email.getText());
        stu.setHomeTown(hometown.getText());
        stu.setName(name.getText());
        stu.setSno(sno.getText());
        stu.setTel(tel.getText());
        stu.setSex(sex.getText());
    }

    private void setEmpty() {
        name.setText("");
        sno.setText("");
        faulty.setText("");
        sex.setText("");
        email.setText("");
        hometown.setText("");
        tel.setText("");
    }
    private void ErrorMessage(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("更新失败，学号或姓名为空");
        JButton jb = new JButton("确定");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(jl);
        dialog.add(jb);
        dialog.setSize(200,100);
        dialog.setLocation(450,250);
        dialog.setVisible(true);
    }

    private void ErrorUpdate(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("更新失败，学号不存在");
        JButton jb = new JButton("确定");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(jl);
        dialog.add(jb);
        dialog.setSize(200,100);
        dialog.setLocation(450,250);
        dialog.setVisible(true);
    }
}
