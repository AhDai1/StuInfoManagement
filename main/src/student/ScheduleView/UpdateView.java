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

public class UpdateView extends JFrame{
    private JPanel jpCenter, jpSouth;
    private JButton jbUpdate, jbExit;
    private JTextField schno, schname, schhour, schbook;

    public UpdateView() {
        init();
    }

    private void init() {
        setTitle(Final.SCHEDULE_UPDATETITLE);
        // center panel
        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(5, 2));
        jpCenter.add(new JLabel(Final.SCHEDULE_NO));
        schno = new JTextField();
        jpCenter.add(schno);
        jpCenter.add(new JLabel(Final.SCHEDULE_NAME));
        schname = new JTextField();
        jpCenter.add(schname);
        jpCenter.add(new JLabel(Final.SCHEDULE_HOUR));
        schhour = new JTextField();
        jpCenter.add(schhour);
        jpCenter.add(new JLabel(Final.SCHEDULE_BOOK));
        schbook = new JTextField();
        jpCenter.add(schbook);

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
                    Schedule sch = new Schedule();
                    buildStudent(sch);
                    boolean isSuccess = ((ScheduleDAO) BaseDAO.getDAO(DAO.ScheduleDAO)).update(sch);
                    if (isSuccess) {
                        setEmpty();
                        if (MainView.currPageNum < 0 || MainView.currPageNum > 99) {
                            MainView.currPageNum = 1;
                        }
                        String[][] result = ((ScheduleDAO) BaseDAO.getDAO(DAO.ScheduleDAO))
                                .list(MainView.currPageNum);
                        MainView.initJTable(MainView.jTable, result);
                        SuccessUpdate();
                    }else{
                        ErrorUpdate();
                        setEmpty();
                    }
                }else{
                    ErrorMessage();
                    setEmpty();
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
        if ("".equals(schno.getText())) {
            return result;
        } else {
            result = true;
        }
        return result;
    }

    private void buildStudent(Schedule sch) {
        sch.setSchNo(schno.getText());
        sch.setSchName(schname.getText());
        sch.setSchHour(schhour.getText());
        sch.setSchBook(schbook.getText());
    }

    private void setEmpty() {
        schno.setText("");
        schname.setText("");
        schhour.setText("");
        schbook.setText("");
    }
    private void ErrorMessage(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("更新失败，课程编号为空");
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
        JLabel jl = new JLabel("更新失败，课程编号不存在");
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

    private void SuccessUpdate(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("更新成功，请点击确定");
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
