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

import student.Final;
import student.DAO;
import student.base.BaseDAO;
import student.dao.StuDAO;
import student.table.Stu;

/**
 * 说明：删除学生信息
 */
public class DeleteView extends JFrame{
    private JPanel jpCenter, jpSouth;
    private JButton jbDelete, jbEXit;
    private JTextField name, sno;
    
    public DeleteView(){ init(); }
    
    public void init(){
        setTitle(Final.DELETE_TITLE);

        //center panel
        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(3, 2));
        jpCenter.add(new JLabel(Final.STUDENT_NAME));
        name = new JTextField();
        jpCenter.add(name);
        jpCenter.add(new JLabel(Final.STUDENT_SNO));
        sno = new JTextField();
        jpCenter.add(sno);
        jpCenter.add(new JLabel("-------------------------------------------------"));
        jpCenter.add(new JLabel("-------------------------------------------------"));

        //south panel
        jpSouth = new JPanel();
        jpSouth.setLayout(new GridLayout(1, 2));
        jbDelete = new JButton(Final.DELETE);
        jbDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check()) {
                    Stu stu = new Stu();
                    buildStudent(stu);
                    boolean isSuccess = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO)).delete(stu);
                    if (isSuccess) {
                        setEmpty();
                        if (MainView.currPageNum < 0 || MainView.currPageNum > 99) {
                            MainView.currPageNum = 1;
                        }
                        String[][] result = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO))
                                .list(MainView.currPageNum);
                        MainView.initJTable(MainView.jTable, result);
                    }
                }
            }
        });
        jpSouth.add(jbDelete);
        jbEXit = new JButton(Final.EXIT);
        jbEXit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jpSouth.add(jbEXit);

        this.add(jpCenter, BorderLayout.CENTER);
        this.add(jpCenter, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(470, 250, 400, 130);
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
        stu.setName(name.getText());
        stu.setSno(sno.getText());
    }

    private void setEmpty() {
        name.setText("");
        sno.setText("");
    }
}
