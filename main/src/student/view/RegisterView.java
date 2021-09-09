package student.view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import student.Final;
import student.DAO;
import student.base.BaseDAO;
import student.dao.AdminDAO;
import student.dao.StuDAO;
import student.table.Stu;

/**
 * 说明：注册页面
 */
public class RegisterView extends JFrame{
    private JPanel jpCenter, jpSouth;
    private JTextField name, username;
    private JPasswordField password;
    private JButton register, reset;

    public RegisterView(){ init(); }

    public void init(){
        this.setTitle("Register");

        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(4,2));
        jpCenter.add(new JLabel(Final.REGISTER_NAME));
        name = new JTextField();
        jpCenter.add(name);

        jpCenter.add(new JLabel(Final.LOGIN_USERNAME));
        username = new JTextField();
        jpCenter.add(username);

        jpCenter.add(new JLabel(Final.LOGIN_PASSWORD));
        password = new JPasswordField();
        jpCenter.add(password);

        jpCenter.add(new JLabel("---------------------------------------------"));
        jpCenter.add(new JLabel("---------------------------------------------"));

        jpSouth = new JPanel();
        jpSouth.setLayout(new GridLayout(1,2));
        register = new JButton(Final.REGISTER);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(check()){
                    boolean isSuccess = ((AdminDAO) BaseDAO.getDAO(DAO.AdMinDAO)).queryRegister(
                            name.getText(), username.getText(), String.valueOf(password.getPassword())
                    );
                    if(isSuccess){
                        setEmpty();
                        SuccessRegister();
                    }else{
                        Error();
                        setEmpty();
                    }
                }
                else{
                    ErrorMessage();
                }
            }
        });

        jpSouth.add(register);

        reset = new JButton(Final.RESET);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name.setText("");
                username.setText("");
                password.setText("");
            }
        });
        jpSouth.add(reset);

        this.add(jpCenter, BorderLayout.CENTER);
        this.add(jpSouth, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(450, 250, 375, 200);
        this.setResizable(false);
        this.setVisible(true);

    }
    private void setEmpty() {
        name.setText("");
        username.setText("");
        password.setText("");
    }
    private boolean check(){
        boolean result = false;
        if("".equals(name.getText()) || "".equals(username.getText()) || "".equals(String.valueOf(password.getPassword()))){
            return result;
        }else {
            result = true;
        }
        return result;

    }
    private void ErrorMessage(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("注册失败，用户名或密码为空");
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
    private void SuccessRegister(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("注册成功，请重新登陆");
        JButton jb = new JButton("确定");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        dialog.add(jl);
        dialog.add(jb);
        dialog.setSize(200,100);
        dialog.setLocation(450,250);
        dialog.setVisible(true);
    }
    private void Error(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("用户名已存在，请重新输入");
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
