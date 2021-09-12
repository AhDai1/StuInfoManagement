package student.ScoreView;


import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import student.Final;
import student.DAO;
import student.ScoreView.AddView;
import student.ScoreView.DeleteView;
import student.ScoreView.UpdateView;
import student.base.BaseDAO;
import student.dao.ScoreDAO;

public class MainView extends JFrame{

    private final int maxPageNum = 99;
    private JPanel jpNorth, jpSouth, jpCenter;//面板
    private JButton jbFirst, jbLast, jbNext, jbPre, jbAdd, jbDelete, jbUpdate, jbFind;//按钮
    private JLabel currPageNumJLabel, QuerySchno, QueryStuno;//标签
    private JTextField jTestField1, jTestField2;//文本框
    public static JTable jTable;
    private JScrollPane jScrollPane;//滚动条
    private DefaultTableModel myTableModel;

    public static String[] column = {Final.SCORE_STUNO, Final.SCORE_SCHNO, Final.SCORE};
    public static int currPageNum = 1;

    public MainView() { init(); }

    private void init(){
        setTitle(Final.SCORE_TITLE);

        //north panel
        jpNorth = new JPanel();
        jpNorth.setLayout(new GridLayout(2, 4));

        QuerySchno = new JLabel();
        QuerySchno.setText(Final.SCORE_STUNO);
        QuerySchno.setHorizontalAlignment(JLabel.CENTER);
        jpNorth.add(QuerySchno);

        jTestField1 = new JTextField(Final.PARAM_FIND_CONDITION);
        jTestField1.addKeyListener(new MainView.FindListener());
        jpNorth.add(jTestField1);

        QueryStuno = new JLabel();
        QueryStuno.setText(Final.SCHEDULE_NO);
        QuerySchno.setHorizontalAlignment(JLabel.CENTER);
        jpNorth.add(QueryStuno);

        jTestField2 = new JTextField(Final.PARAM_FIND_CONDITION);
        jTestField2.addKeyListener(new MainView.FindListener());
        jpNorth.add(jTestField2);


        //query
        jbFind = new JButton(Final.PARAM_FIND);
        jbFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });
        jbFind.addKeyListener(new student.ScoreView.MainView.FindListener());
        jpNorth.add(jbFind);

        //add
        jbAdd = new JButton(Final.PARAM_ADD);
        jbAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddView();
            }
        });
        jpNorth.add(jbAdd);

        //delete
        jbDelete = new JButton(Final.PARAM_DELETE);
        jbDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteView();
            }
        });
        jpNorth.add(jbDelete);

        //update
        jbUpdate = new JButton(Final.PARAM_UPDATE);
        jbUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateView();
            }
        });
        jpNorth.add(jbUpdate);

        //center panel
        jpCenter = new JPanel();
        jpCenter.setLayout(new GridLayout(1,2));



        //init JTable
        String[][] result = ((ScoreDAO) BaseDAO.getDAO(DAO.ScoreDAO)).list(currPageNum);
        myTableModel = new DefaultTableModel(result, column);
        jTable = new JTable(myTableModel);
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();//渲染器
        cr.setHorizontalAlignment(JLabel.CENTER);//中心对齐
        jTable.setDefaultRenderer(Object.class, cr);
        initJTable(jTable, result);

        jScrollPane = new JScrollPane(jTable);
        jpCenter.add(jScrollPane);

        //south panel
        jpSouth = new JPanel();
        jpSouth.setLayout(new GridLayout(1,5));

        jbFirst = new JButton(Final.MAIN_FIRST);
        jbFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum = 1;
                String[][] result = ((ScoreDAO) BaseDAO.getDAO(DAO.ScoreDAO)).list(currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(Final.MAIN_DI + currPageNum + Final.MAIN_YE);
            }
        });
        jbPre = new JButton(Final.MAIN_PRE);
        jbPre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum--;
                if (currPageNum <= 0) {
                    currPageNum = 1;
                }
                String[][] result = ((ScoreDAO) BaseDAO.getDAO(DAO.ScoreDAO)).list(currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(Final.MAIN_DI + currPageNum + Final.MAIN_YE);
            }
        });

        jbNext = new JButton(Final.MAIN_NEXT);
        jbNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum++;
                if (currPageNum > maxPageNum) {
                    currPageNum = maxPageNum;
                }
                String[][] result = ((ScoreDAO) BaseDAO.getDAO(DAO.ScoreDAO)).list(currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(Final.MAIN_DI + currPageNum + Final.MAIN_YE);
            }
        });

        jbLast = new JButton(Final.MAIN_LAST);
        jbLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum = maxPageNum;
                String[][] result = ((ScoreDAO) BaseDAO.getDAO(DAO.ScoreDAO)).list(currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(Final.MAIN_DI + currPageNum + Final.MAIN_YE);
            }
        });

        currPageNumJLabel = new JLabel(Final.MAIN_DI + currPageNum + Final.MAIN_YE);
        currPageNumJLabel.setHorizontalAlignment(JLabel.CENTER);

        jpSouth.add(jbFirst);
        jpSouth.add(jbPre);
        jpSouth.add(currPageNumJLabel);
        jpSouth.add(jbNext);
        jpSouth.add(jbLast);

        this.add(jpNorth, BorderLayout.NORTH);
        this.add(jpCenter, BorderLayout.CENTER);
        this.add(jpSouth, BorderLayout.SOUTH);


        setBounds(400, 200, 380, 232);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void initJTable(JTable jTable, String[][] result) {
        ((DefaultTableModel) jTable.getModel()).setDataVector(result, column);
        jTable.setRowHeight(20);
        jTable.setEnabled(false);//不可编辑

        TableColumn secondColumn = jTable.getColumnModel().getColumn(0);
        secondColumn.setPreferredWidth(130);
        secondColumn.setMaxWidth(130);
        secondColumn.setMinWidth(130);

        TableColumn thirdColumn = jTable.getColumnModel().getColumn(1);
        thirdColumn.setPreferredWidth(130);
        thirdColumn.setMaxWidth(130);
        thirdColumn.setMinWidth(130);

        TableColumn fourthColumn = jTable.getColumnModel().getColumn(2);
        fourthColumn.setPreferredWidth(110);
        fourthColumn.setMaxWidth(110);
        fourthColumn.setMinWidth(110);



    }

    private class FindListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                find();
            }
        }
    }


    private void find(){
        currPageNum = 0;
        String stuno = jTestField1.getText();
        String schno = jTestField2.getText();
        /*if ("".equals(stuno) || stuno == null ) {
            initJTable(student.ScheduleView.MainView.jTable, null);
            currPageNumJLabel.setText(Final.MAIN_RESULT);
            return;
        }*/
        String[][] result = ((ScoreDAO) BaseDAO.getDAO(DAO.ScoreDAO)).queryScoreResult(stuno, schno);
        jTestField1.setText("");
        jTestField2.setText("");
        initJTable(student.ScoreView.MainView.jTable, result);
        currPageNumJLabel.setText(Final.MAIN_RESULT);
    }
}
