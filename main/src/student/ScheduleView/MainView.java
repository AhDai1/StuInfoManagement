package student.ScheduleView;

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
import student.base.BaseDAO;
import student.dao.ScheduleDAO;

public class MainView extends JFrame{
    private final int maxPageNum = 99;
    private JPanel jpNorth, jpSouth, jpCenter;//面板
    private JButton jbFirst, jbLast, jbNext, jbPre, jbAdd, jbDelete, jbUpdate, jbFind;//按钮
    private JLabel currPageNumJLabel, QuerySchno;//标签
    private JTextField jTestField;//文本框
    public static JTable jTable;
    private JScrollPane jScrollPane;//滚动条
    private DefaultTableModel myTableModel;

    public static String[] column = {Final.SCHEDULE_NO, Final.SCHEDULE_NAME, Final.SCHEDULE_HOUR,
        Final.SCHEDULE_BOOK};
    public static int currPageNum = 1;

    public MainView() { init(); }

    private void init(){
        setTitle(Final.SCHEDULE_TITLE);

        //north panel
        jpNorth = new JPanel();
        jpNorth.setLayout(new GridLayout(1, 5));

        QuerySchno = new JLabel();
        QuerySchno.setText(Final.SCHEDULE_NO);
        jpNorth.add(QuerySchno);

        jTestField = new JTextField(Final.PARAM_FIND_CONDITION);
        jTestField.addKeyListener(new FindListener());
        jpNorth.add(jTestField);

        //query
        jbFind = new JButton(Final.PARAM_FIND);
        jbFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });
        jbFind.addKeyListener(new FindListener());
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
        String[][] result = ((ScheduleDAO) BaseDAO.getDAO(DAO.ScheduleDAO)).list(currPageNum);
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
                String[][] result = ((ScheduleDAO) BaseDAO.getDAO(DAO.ScheduleDAO)).list(currPageNum);
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
                String[][] result = ((ScheduleDAO) BaseDAO.getDAO(DAO.ScheduleDAO)).list(currPageNum);
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
                String[][] result = ((ScheduleDAO) BaseDAO.getDAO(DAO.ScheduleDAO)).list(currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(Final.MAIN_DI + currPageNum + Final.MAIN_YE);
            }
        });

        jbLast = new JButton(Final.MAIN_LAST);
        jbLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum = maxPageNum;
                String[][] result = ((ScheduleDAO) BaseDAO.getDAO(DAO.ScheduleDAO)).list(currPageNum);
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


        setBounds(400, 200, 450, 232);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void initJTable(JTable jTable, String[][] result) {
        ((DefaultTableModel) jTable.getModel()).setDataVector(result, column);
        jTable.setRowHeight(20);
        jTable.setEnabled(false);//不可编辑

        TableColumn secondColumn = jTable.getColumnModel().getColumn(0);
        secondColumn.setPreferredWidth(60);
        secondColumn.setMaxWidth(60);
        secondColumn.setMinWidth(60);

        TableColumn thirdColumn = jTable.getColumnModel().getColumn(1);
        thirdColumn.setPreferredWidth(150);
        thirdColumn.setMaxWidth(150);
        thirdColumn.setMinWidth(150);

        TableColumn fourthColumn = jTable.getColumnModel().getColumn(2);
        fourthColumn.setPreferredWidth(60);
        fourthColumn.setMaxWidth(60);
        fourthColumn.setMinWidth(60);

        TableColumn fifthColumn = jTable.getColumnModel().getColumn(3);
        fifthColumn.setPreferredWidth(170);
        fifthColumn.setMaxWidth(170);
        fifthColumn.setMinWidth(170);


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
        String param = jTestField.getText();
        if ("".equals(param) || param == null) {
            initJTable(MainView.jTable, null);
            currPageNumJLabel.setText(Final.MAIN_RESULT);
            return;
        }
        String[][] result = ((ScheduleDAO) BaseDAO.getDAO(DAO.ScheduleDAO)).querySchnoResult(param);
        jTestField.setText("");
        initJTable(MainView.jTable, result);
        currPageNumJLabel.setText(Final.MAIN_RESULT);
    }

}
