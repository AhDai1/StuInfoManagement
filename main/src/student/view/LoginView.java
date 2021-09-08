package student.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;

import student.Final;
import student.DAO;
import student.base.BaseDAO;
import student.dao.AdminDAO;
import student.run.Main;

/**
 * 说明：登陆界面
 */
public class LoginView extends JFrame{
    private JPanel jpCenter, jpSouth;
    private JTextField username;
    private JPasswordField password;
    private JButton login, reset;

    public LoginView() { init(); }

    private void init(){
        this.setTitle("Login");

        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(3,2));
        jpCenter.add(new JLabel((Final.LOGIN_USERNAME)));
        username = new JTextField();
        jpCenter.add(username);
        jpCenter.add(new JLabel(Final.LOGIN_PASSWORD));
        password = new JPasswordField();

        //key listener
        password.addKeyListener(new LoginListener());
        jpCenter.add(password);
        jpCenter.add(new JLabel("---------------------------------------------"));
        jpCenter.add(new JLabel("---------------------------------------------"));

        jpSouth = new JPanel();
        jpSouth.setLayout(new GridLayout(1,2));
        login = new JButton(Final.LOGIN);
        login.addActionListener(new ActionListener() {//按钮登录
            @Override
            public void actionPerformed(ActionEvent e) {
                check();
            }
        });
        jpSouth.add(login);
        reset = new JButton(Final.RESET);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username.setText("");
                password.setText("");
            }
        });
        jpSouth.add(reset);

        this.add(jpCenter, BorderLayout.CENTER);
        this.add(jpSouth, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(450, 250, 375, 140);
        this.setResizable(false);
        this.setVisible(true);
    }
    private class LoginListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){//回车登录
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                check();
            }
        }
    }
    private void check(){
        AdminDAO adminDao = (AdminDAO) BaseDAO.getDAO(DAO.AdMinDAO);
        //dispose();
        //new MainView();
        if(adminDao.queryLogin(username.getText(), String.valueOf(password.getPassword()))){
            dispose();//关闭窗体
            new MainView();
        }else{
            Error();
            username.setText("");
            password.setText("");
        }
    }
    private void Error(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("账户或密码错误，请重新输入");
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

