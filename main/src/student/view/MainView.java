package student.view;

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
import student.dao.StuDAO;
/**
 * 说明：首页
 */
public class MainView extends JFrame{
    private final int maxPageNum = 99;
    private JPanel jpNorth, jpSouth, jpCenter;//面板
    private JButton jbFirst, jbLast, jbNext, jbPre, jbAdd, jbDelete, jbUpdate, jbFind;//按钮
    private JLabel currPageNumJLabel;//标签
    private JTextField jTestField;//文本框
    public static JTable jTable;
    private JScrollPane jScrollPane;//滚动条
    private DefaultTableModel myTableModel;

    public static String[] column = {Final.STUDENT_NAME, Final.STUDENT_SNO, Final.STUDENT_SEX,
        Final.STUDENT_FACULTY, Final.STUDENT_HOMETOWN, Final.STUDENT_EMAIL, Final.STUDENT_TEL };
    public static int currPageNum = 1;

    public MainView() { init(); }

    private void init(){
        setTitle(Final.MAIN_TITLE);

        //north panel
        jpNorth = new JPanel();
        jpNorth.setLayout(new GridLayout(1, 5));
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
        String[][] result = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO)).list(currPageNum);
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
                String[][] result = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO)).list(currPageNum);
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
                String[][] result = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO)).list(currPageNum);
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
                String[][] result = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO)).list(currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(Final.MAIN_DI + currPageNum + Final.MAIN_YE);
            }
        });

        jbLast = new JButton(Final.MAIN_LAST);
        jbLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum = maxPageNum;
                String[][] result = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO)).list(currPageNum);
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


        setBounds(400, 200, 750, 340);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void initJTable(JTable jTable, String[][] result) {
        ((DefaultTableModel) jTable.getModel()).setDataVector(result, column);
        jTable.setRowHeight(20);

        /*TableColumn firstColumn = jTable.getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(30);
        firstColumn.setMaxWidth(30);
        firstColumn.setMinWidth(30);*/

        TableColumn secondColumn = jTable.getColumnModel().getColumn(0);
        secondColumn.setPreferredWidth(60);
        secondColumn.setMaxWidth(60);
        secondColumn.setMinWidth(60);

        TableColumn thirdColumn = jTable.getColumnModel().getColumn(1);
        thirdColumn.setPreferredWidth(90);
        thirdColumn.setMaxWidth(90);
        thirdColumn.setMinWidth(90);

        TableColumn fourthColumn = jTable.getColumnModel().getColumn(2);
        fourthColumn.setPreferredWidth(30);
        fourthColumn.setMaxWidth(30);
        fourthColumn.setMinWidth(30);

        TableColumn fifthColumn = jTable.getColumnModel().getColumn(3);
        fifthColumn.setPreferredWidth(120);
        fifthColumn.setMaxWidth(120);
        fifthColumn.setMinWidth(120);

        TableColumn sixthColumn = jTable.getColumnModel().getColumn(4);
        sixthColumn.setPreferredWidth(160);
        sixthColumn.setMaxWidth(160);
        sixthColumn.setMinWidth(160);

        TableColumn seventhColumn = jTable.getColumnModel().getColumn(5);
        seventhColumn.setPreferredWidth(150);
        seventhColumn.setMaxWidth(150);
        seventhColumn.setMinWidth(150);

        /*TableColumn eighthColumn = jTable.getColumnModel().getColumn(6);
        eighthColumn.setPreferredWidth(100);
        eighthColumn.setMaxWidth(100);
        eighthColumn.setMinWidth(100);*/
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
        String[][] result = ((StuDAO) BaseDAO.getDAO(DAO.StuDAO)).queryName(param);
        jTestField.setText("");
        initJTable(MainView.jTable, result);
        currPageNumJLabel.setText(Final.MAIN_RESULT);
    }
}
