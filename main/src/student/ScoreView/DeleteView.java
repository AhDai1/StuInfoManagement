package student.ScoreView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import student.Final;
import student.DAO;
import student.base.BaseDAO;
import student.dao.ScoreDAO;
import student.table.Score;


public class DeleteView extends JFrame{
    private JPanel jpCenter, jpSouth;
    private JButton jbDelete, jbEXit;
    private JTextField sno, schno;

    public DeleteView(){ init(); }

    public void init(){
        setTitle(Final.SCORE_DELETETITLE);

        //center panel
        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(3, 2));
        jpCenter.add(new JLabel(Final.SCORE_STUNO));
        sno = new JTextField();
        jpCenter.add(sno);

        jpCenter.add(new JLabel(Final.SCORE_SCHNO));
        schno = new JTextField();
        jpCenter.add(schno);

        jpCenter.add(new JLabel("------------------------------------"));
        jpCenter.add(new JLabel("------------------------------------"));

        //south panel
        jpSouth = new JPanel();
        jpSouth.setLayout(new GridLayout(1, 2));
        jbDelete = new JButton(Final.DELETE);
        jbDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check()) {
                    Score sc = new Score();
                    buildStudent(sc);
                    boolean isSuccess = ((ScoreDAO) BaseDAO.getDAO(DAO.ScoreDAO)).delete(sc);
                    if (isSuccess) {
                        setEmpty();
                        if (MainView.currPageNum < 0 || MainView.currPageNum > 99) {
                            MainView.currPageNum = 1;
                        }
                        String[][] result = ((ScoreDAO) BaseDAO.getDAO(DAO.ScoreDAO))
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
        setBounds(470, 250, 305, 130);
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

    private void buildStudent(Score sc) {
        sc.setStuNo(sno.getText());
        sc.setSchNo(schno.getText());
    }

    private void setEmpty() {
        sno.setText("");
        schno.setText("");
    }

    private void ErrorMessage(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("删除失败，学号或课程编号为空");
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
        JLabel jl = new JLabel("删除失败，该学生课程分数不存在");
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
