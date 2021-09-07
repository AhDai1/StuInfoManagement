package student.view;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

    public static String[] column = {"id", Final.STUDENT_NAME, Final.STUDENT_SNO, Final.STUDENT_SEX,
        Final.STUDENT_FACULTY, Final.STUDENT_HOMETOWN, Final.STUDENT_EMAIL, Final.STUDENT_TEL };
    public static int currPageNum = 1;

    public MainView() { init(); }

    private void init(){
        setTitle(Final.MAIN_TITLE);


    }

}
