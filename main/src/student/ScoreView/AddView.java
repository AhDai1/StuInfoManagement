package student.ScoreView;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import student.Final;
import student.DAO;
import student.base.BaseDAO;
import student.dao.ScoreDAO;
import student.table.Score;

public class AddView extends JFrame{

    private JPanel jpCenter,jpSouth;
    private JButton jbAdd, jbExit;
    private JTextField sno, schno, score;

    public AddView(){ init(); }

    public void init(){
        setTitle(Final.SCORE_ADDTITLE);
        //center
        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(4,2));

        jpCenter.add(new JLabel(Final.SCORE_STUNO));
        sno = new JTextField();
        jpCenter.add(sno);

        jpCenter.add(new JLabel(Final.SCORE_SCHNO));
        schno = new JTextField();
        jpCenter.add(schno);

        jpCenter.add(new JLabel(Final.SCORE));
        score = new JTextField();
        jpCenter.add(score);


        jpCenter.add(new JLabel("------------------------------------------------"));
        jpCenter.add(new JLabel("------------------------------------------------"));

        //south
        jpSouth = new JPanel();
        jpSouth.setLayout(new GridLayout(1,2));
        jbAdd = new JButton(Final.SCORE_ADD);
        jbAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(check()){
                    Score sc = new Score();
                    buildStudent(sc);
                    int isSuccess = ((ScoreDAO) BaseDAO.getDAO(DAO.ScoreDAO)).add(sc);
                    if(isSuccess == 0){
                        setEmpty();
                        if(MainView.currPageNum < 0 || MainView.currPageNum > 99){
                            MainView.currPageNum = 1;
                        }
                        String[][] result = ((ScoreDAO) BaseDAO.getDAO(DAO.ScoreDAO)).list(MainView.currPageNum);
                        MainView.initJTable(MainView.jTable, result);
                    }else{
                        ErrorAdd(isSuccess);
                        setEmpty();
                    }
                }
                else{
                    ErrorMessage();
                    setEmpty();
                }
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
        if ("".equals(schno.getText()) || "".equals(sno.getText())) {
            return result;
        } else {
            result = true;
        }
        return result;
    }

    private void buildStudent(Score sc) {
        sc.setStuNo(sno.getText());
        sc.setSchNo(schno.getText());
        sc.setStuScore(Integer.valueOf(score.getText()).intValue());
    }

    private void setEmpty() {
        sno.setText("");
        schno.setText("");
        score.setText("");
    }

    private void ErrorMessage(){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        JLabel jl = new JLabel("添加失败，学号或课程为空");
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

    private void ErrorAdd(int flag){
        JDialog dialog = new JDialog(this, true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        if(flag == 1) {
            JLabel jl = new JLabel("添加失败，学号或课程编号不存在");
            dialog.add(jl);
        }
        else if(flag == 2){
            JLabel jl = new JLabel("添加失败，该学生课程已有分数");
            dialog.add(jl);
        }
        JButton jb = new JButton("确定");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(jb);
        dialog.setSize(200,100);
        dialog.setLocation(450,250);
        dialog.setVisible(true);
    }
}
