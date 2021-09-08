package student.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import student.Final;
import student.DAO;
import student.base.BaseDAO;
import student.dao.StuDAO;
import student.run.Main;
import student.table.Stu;

/**
 * 说明：添加模块
 */
public class AddView extends JFrame{
    private JPanel jpCenter,jpSouth;
    private JButton jbAdd, jbExit;
    private JTextField name, sno, faulty, hometown, email, tel, sex;

    public AddView(){ init(); }

    public void init(){
        setTitle(Final.ADD_TITLE);

        //center
        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(9,2));

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

        //south
        jpSouth = new JPanel();
        jpSouth.setLayout(new GridLayout(1,2));
        jbAdd = new JButton(Final.ADD_ADD);
        jbAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(check()){
                    Stu stu = new Stu();
                    buildStudent(stu);
                    boolean isSuccess = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO)).add(stu);
                    if(isSuccess){
                        setEmpty();
                        if(MainView.currPageNum < 0 || MainView.currPageNum > 99){
                            MainView.currPageNum = 1;
                        }
                        String[][] result = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO)).list(MainView.currPageNum);
                        MainView.initJTable(MainView.jTable, result);
                    }
                }
                //TODO
            }
        });
        jpSouth.add(jbAdd);

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
        setBounds(470,200,400,270);
        setResizable(false);
        setVisible(true);

    }

    private boolean check() {
        boolean result = false;
        if ("".equals(name.getText()) || "".equals(sno.getText()) || "".equals(faulty.getText())
                || "".equals(sex.getText()) ||  "".equals(tel.getText()) ||
                "".equals(email.getText()) || "".equals(hometown.getText())) {
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
}
