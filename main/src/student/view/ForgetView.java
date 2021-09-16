package student.view;

import student.DAO;
import student.Final;
import student.base.BaseDAO;
import student.dao.AdminDAO;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgetView extends JFrame {
    private JPanel jpCenter, jpSouth;
    private JTextField name, username;
    //private JPasswordField password;
    private JButton find;

    public ForgetView(){ init(); }

    public void init(){
        this.setTitle("Forget");

        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(3,2));
        jpCenter.add(new JLabel(Final.LOGIN_USERNAME));
        username = new JTextField();
        jpCenter.add(username);

        jpCenter.add(new JLabel(Final.REGISTER_NAME));
        name = new JTextField();
        jpCenter.add(name);

        jpCenter.add(new JLabel("---------------------------------------------"));
        jpCenter.add(new JLabel("---------------------------------------------"));

        jpSouth = new JPanel();
        jpSouth.setLayout(new GridLayout(1,2));
        find = new JButton(Final.PARAM_FIND);
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(check()){
                    boolean isSuccess = ((AdminDAO) BaseDAO.getDAO(DAO.AdMinDAO)).queryFind(
                            username.getText(), name.getText()
                    );
                    if(isSuccess){
                        setEmpty();
                        SuccessFind();
                    }else{
                        ErrorMessage();
                        setEmpty();
                    }
                }
                else{
                    ErrorMessage();
                }
            }
        });

        jpSouth.add(find);

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
    }

    private boolean check(){
        boolean result = false;
        if("".equals(name.getText()) || "".equals(username.getText()) ){
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
        JLabel jl = new JLabel("查找失败");
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
    private void SuccessFind(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.queryFind(name.getText(), username.getText());
        //String password = adminDAO.getPassword();
        JLabel jl = new JLabel("查找成功，你的密码是:"+((AdminDAO) BaseDAO.getDAO(DAO.AdMinDAO)).getPassword());
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
