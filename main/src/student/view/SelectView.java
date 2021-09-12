package student.view;


import student.Final;
import student.ScheduleView.MainView;
import student.run.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * 说明： 选择页面
 */
public class SelectView extends JFrame {
    JPanel jpCenter;
    JButton jbStuMessage, jbStuScore, jbSchedule;

    public SelectView() { init(); }

    private void init(){
        this.setTitle(Final.SELECT);

        jpCenter = new JPanel();
        //jpCenter.setLayout(new GridLayout(3,1, 5,5));
        //jpCenter.setSize(375,150);
        jpCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 100,10));
        jbStuMessage = new JButton(Final.SELECT_MESSAGE);
        jbStuMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new student.view.MainView();
            }
        });

        jbSchedule = new JButton(Final.SELECT_SCHEDULE);
        jbSchedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new student.ScheduleView.MainView();
            }
        });

        jbStuScore = new JButton(Final.SELECT_SCORE);
        jbStuScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new student.ScoreView.MainView();
            }
        });

        jpCenter.add(jbStuMessage);
        jpCenter.add(jbSchedule);
        jpCenter.add(jbStuScore);
        //jpCenter.setVisible(true);
        this.add(jpCenter);


        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(450, 250, 300, 150);
        this.setResizable(false);
        this.setVisible(true);
    }

}
