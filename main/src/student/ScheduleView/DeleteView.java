package student.ScheduleView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import student.Final;
import student.DAO;
import student.base.BaseDAO;
import student.dao.ScheduleDAO;
import student.table.Schedule;

public class DeleteView extends JFrame{
    private JPanel jpCenter, jpSouth;
    private JButton jbDelete, jbEXit;
    private JTextField schno;

    public DeleteView(){ init(); }

    public void init(){
        setTitle(Final.SCHEDULE_DELETETITLE);

        //center panel
        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(2, 2));
        jpCenter.add(new JLabel(Final.SCHEDULE_NO));
        schno = new JTextField();
        jpCenter.add(schno);

        jpCenter.add(new JLabel("------------------------------------------------"));
        jpCenter.add(new JLabel("------------------------------------------------"));

        //south panel
        jpSouth = new JPanel();
        jpSouth.setLayout(new GridLayout(1, 2));
        jbDelete = new JButton(Final.DELETE);
        jbDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check()) {
                    Schedule sch = new Schedule();
                    buildStudent(sch);
                    boolean isSuccess = ((ScheduleDAO) BaseDAO.getDAO(DAO.ScheduleDAO)).delete(sch);
                    if (isSuccess) {
                        setEmpty();
                        if (MainView.currPageNum < 0 || MainView.currPageNum > 99) {
                            MainView.currPageNum = 1;
                        }
                        String[][] result = ((ScheduleDAO) BaseDAO.getDAO(DAO.ScheduleDAO))
                                .list(MainView.currPageNum);
                        MainView.initJTable(MainView.jTable, result);
                        SuccessDelete();
                    }else{
                        ErrorDelete();
                        setEmpty();
                    }
                }else {
                    ErrorMessage();
                    setEmpty();
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
        this.add(jpSouth, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(470, 250, 400, 130);
        setResizable(false);
        setVisible(true);
    }

    private boolean check() {
        boolean result = false;
        if ("".equals(schno.getText())) {
            return result;
        } else {
            result = true;
        }
        return result;
    }

    private void buildStudent(Schedule sch) {
        sch.setSchNo(schno.getText());
    }

    private void setEmpty() {
        schno.setText("");
    }

    private void ErrorMessage(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("删除失败，课程编号为空");
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

    private void ErrorDelete(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("删除失败，课程编号不存在");
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

    private void SuccessDelete(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("删除成功，请点击确定");
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
