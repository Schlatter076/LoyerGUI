package loyer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PiePlot;

import javax.swing.JSeparator;
import javax.swing.JToggleButton;

/**
 * 自定义测试系统显示界面
 * @author Loyer
 * @coding UTF-8
 */
abstract public class LoyerFrame {

  /**顶级框架*/
  protected JFrame frame;
  /** 获取当前屏幕宽度 */
  protected static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
  /** 获取当前屏幕高度 */
  protected static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
  /**菜单条*/
  protected JMenuBar menuBar;
  /**文件菜单*/
  protected JMenu fileMenu;
  /**打开菜单*/
  protected JMenuItem openItem;
  /**退出菜单*/
  protected JMenuItem exitItem;
  /**编辑菜单*/
  protected JMenu editMenu;
  /**打开调试工具菜单*/
  protected JMenuItem usartItem;
  /**查看测试结果菜单*/
  protected JMenuItem resultItem;
  /**查看不良报告菜单*/
  protected JMenuItem ngReportItem;
  /**工具栏*/
  protected JToolBar toolBar;
  /**退出按钮*/
  protected JButton exitButt;
  /**打开调试工具按钮*/
  protected JButton usartButt;
  /**查看测试结果按钮*/
  protected JButton resultButt;
  /**查看不良报告按钮*/
  protected JButton ngReportButt;
  /***/
  protected JSplitPane splitPane;
  /**联系我们菜单*/
  protected JMenuItem contactItem;
  /**关于菜单*/
  protected JMenuItem aboutItem;
  /**串口工具栏*/
  protected JToolBar comToolBar;
  /**串口1按钮*/
  protected JToggleButton com1Butt;
  /**串口2按钮*/
  protected JToggleButton com2Butt;
  /**串口3按钮*/
  protected JToggleButton com3Butt;
  /**串口4按钮*/
  protected JToggleButton com4Butt;
  /**串口5按钮*/
  protected JToggleButton com5Butt;
  /**串口6按钮*/
  protected JToggleButton com6Butt;
  /** 测试数据显示面板 */
  protected JScrollPane dataPanel;
  /**左页面*/
  protected JSplitPane leftSplitPane;
  /**右页面*/
  protected JPanel rightPanel;
  /**进度条*/
  protected JProgressBar progressBar;
  /**进度条值*/
  protected int progressValue = 0;
  /**测试总数文本框*/
  protected JTextField totalField;
  /**测试ok文本框*/
  protected JTextField okField;
  /**测试ng文本框*/
  protected JTextField ngField;
  /**测试时间文本框*/
  protected JTextField timeField;
  /**运行状态文本框*/
  protected JTextField statuField;
  /**产品型号文本框*/
  protected JTextField productField;
  /** 产品型号 */
  protected String PRODUCT_NAME = "机种名";
  /**测试总数计数器*/
  protected int totalCount = 0;
  /**测试ok计数器*/
  protected int okCount = 0;
  /**测试ng计数器*/
  protected int ngCount = 0;
  /**测试时间计数器*/
  protected int timeCount = 0;
  /** 饼图面板 */
  protected ChartPanel chartPanel;
  /**饼图定义类*/
  protected MyPieChart myPie;
  protected PiePlot piePlot;
  /** 产品扫描区域 */
  protected JTextField scanField;
  /**捺印复选框*/
  protected JCheckBox nayinButt;
  /**点测复选框*/
  protected JCheckBox spotButt;
  /** 左下侧日志显示区域 */
  protected JTextArea editArea;
  /** 格式化日期显示 */
  protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
  /** 换行符 */
  protected static final String SEPARATOR = System.getProperty("line.separator");
  /**帮助菜单*/
  protected JMenu helpMenu;
  /**保留页面*/
  protected JScrollPane persistScroll;
  /**自定义绿色*/
  protected static final Color GREEN = new Color(0, 204, 51);
  
  /**构造器*/
  public LoyerFrame() {
    initialize();
  }

  private void initialize() {
    
    try {
      // 将界面风格设置成和系统一置
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
    } // */

    frame = new JFrame("测试系统");
    frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/pic/Kyokuto.png")));
    // 界面初始大小
    frame.setBounds(WIDTH / 10, HEIGHT / 10, WIDTH * 8 / 10, HEIGHT * 8 / 10);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    // 设置菜单
    menuBar = new JMenuBar();
    fileMenu = new JMenu("文件(F)");
    openItem = new JMenuItem("打开(O)");
    exitItem = new JMenuItem("退出(X)");
    editMenu = new JMenu("编辑(E)");
    usartItem = new JMenuItem("打开调试工具");
    resultItem = new JMenuItem("查看测试结果");
    ngReportItem = new JMenuItem("查看不良报告");
    helpMenu = new JMenu("帮助(H)");
    contactItem = new JMenuItem("联系我(Contact us)");
    aboutItem = new JMenuItem("关于(About)");
    helpMenu.add(contactItem);
    helpMenu.add(aboutItem);
    menuBar.add(fileMenu);
    fileMenu.add(openItem);
    fileMenu.add(exitItem);
    menuBar.add(editMenu);
    editMenu.add(usartItem);
    editMenu.add(resultItem);
    editMenu.add(ngReportItem);
    menuBar.add(helpMenu);    
    frame.setJMenuBar(menuBar);

    // 设置工具栏
    toolBar = new JToolBar("工具栏");
    exitButt = new JButton("退出系统(X)");
    exitButt.setFont(new Font("宋体", Font.PLAIN, 13));
    usartButt = new JButton("打开调试工具(UT)");
    usartButt.setFont(new Font("宋体", Font.PLAIN, 13));
    resultButt = new JButton("查看测试结果(RS)");
    resultButt.setFont(new Font("宋体", Font.PLAIN, 13));
    ngReportButt = new JButton("查看不良报告(RT)");
    ngReportButt.setFont(new Font("宋体", Font.PLAIN, 13));
    nayinButt = new JCheckBox("捺印");
    nayinButt.setFont(new Font("宋体", Font.PLAIN, 12));
    nayinButt.setSelected(true);
    spotButt = new JCheckBox("点测");
    spotButt.setFont(new Font("宋体", Font.PLAIN, 12));

    toolBar.add(exitButt);
    toolBar.addSeparator();
    toolBar.add(usartButt);
    toolBar.addSeparator();
    toolBar.add(resultButt);
    toolBar.addSeparator();
    toolBar.add(ngReportButt);
    toolBar.addSeparator();
    toolBar.add(nayinButt);
    toolBar.addSeparator();
    toolBar.add(spotButt);
    toolBar.setBackground(Color.GRAY);
    frame.getContentPane().add(toolBar, BorderLayout.NORTH);
    toolBar.add(new JSeparator());
    comToolBar = new JToolBar("窗口工具栏");
    toolBar.add(comToolBar);
    com1Butt = new JToggleButton("COM1");
    com1Butt.setFont(new Font("宋体", Font.PLAIN, 12));
    comToolBar.add(com1Butt);
    comToolBar.addSeparator();
    com2Butt = new JToggleButton("COM2");
    com2Butt.setFont(new Font("宋体", Font.PLAIN, 12));
    comToolBar.add(com2Butt);
    comToolBar.addSeparator();
    com3Butt = new JToggleButton("COM3");
    com3Butt.setFont(new Font("宋体", Font.PLAIN, 12));
    comToolBar.add(com3Butt);
    comToolBar.addSeparator();
    com4Butt = new JToggleButton("COM4");
    com4Butt.setFont(new Font("宋体", Font.PLAIN, 12));
    comToolBar.add(com4Butt);
    comToolBar.addSeparator();
    com5Butt = new JToggleButton("COM5");
    com5Butt.setFont(new Font("宋体", Font.PLAIN, 12));
    comToolBar.add(com5Butt);
    comToolBar.addSeparator();
    com6Butt = new JToggleButton("COM6");
    com6Butt.setFont(new Font("宋体", Font.PLAIN, 12));
    comToolBar.add(com6Butt);

    dataPanel = new JScrollPane();
    dataPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "测试数据:", TitledBorder.CENTER, TitledBorder.TOP,
        new Font("楷体", Font.PLAIN, 16), Color.BLACK));
    dataPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    dataPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    totalField = new JTextField(totalCount + "");
    totalField.setEditable(false);
    totalField.setHorizontalAlignment(SwingConstants.CENTER);
    totalField.setFont(new Font("宋体", Font.PLAIN, 30));
    totalField.setBackground(new Color(245, 245, 245));
    okField = new JTextField(okCount + "");
    okField.setEditable(false);
    okField.setHorizontalAlignment(SwingConstants.CENTER);
    okField.setFont(new Font("宋体", Font.PLAIN, 30));
    okField.setForeground(GREEN);
    okField.setBackground(new Color(245, 245, 245));
    ngField = new JTextField(ngCount + "");
    ngField.setEditable(false);
    ngField.setHorizontalAlignment(SwingConstants.CENTER);
    ngField.setFont(new Font("宋体", Font.PLAIN, 30));
    ngField.setForeground(Color.RED);
    ngField.setBackground(new Color(245, 245, 245));
    timeField = new JTextField(timeCount + "");
    timeField.setEditable(false);
    timeField.setBackground(Color.BLACK);
    timeField.setForeground(Color.RED);
    timeField.setHorizontalAlignment(SwingConstants.CENTER);
    timeField.setFont(new Font("宋体", Font.PLAIN, 30));
    statuField = new JTextField();
    statuField.setEditable(false);
    statuField.setText("STOP");
    statuField.setBackground(Color.ORANGE);
    statuField.setHorizontalAlignment(SwingConstants.CENTER);
    statuField.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 30));

    MyPanel statuPanel = new MyPanel("运行状态", statuField);
    MyPanel totalPanel = new MyPanel("测试总数", totalField);
    MyPanel okPanel = new MyPanel("良品数", okField);
    MyPanel ngPanel = new MyPanel("NG数", ngField);
    MyPanel timePanel = new MyPanel("测试时间(S)", timeField);
    JPanel panel = new JPanel();

    panel.setBorder(new LineBorder(Color.GRAY, 3));
    panel.setLayout(new GridLayout(5, 1, 5, 5));
    panel.add(statuPanel);
    panel.add(totalPanel);
    panel.add(okPanel);
    panel.add(ngPanel);
    panel.add(timePanel);
    // 饼图添加面板
    JPanel jp = new JPanel();
    myPie = new MyPieChart(okCount, ngCount);
    chartPanel = myPie.getChartPanel();
    chartPanel.setPreferredSize(new Dimension(120, 400));
    piePlot = myPie.getPiePlot();
    piePlot.setSectionPaint("良品", GREEN);
    piePlot.setSectionPaint("不良", Color.RED);
    jp.setLayout(new BorderLayout());
    jp.setBorder(new TitledBorder(new EtchedBorder(), "不良对照", TitledBorder.CENTER, TitledBorder.TOP,
        new Font("楷体", Font.PLAIN, 16), Color.BLUE));

    jp.add(chartPanel, BorderLayout.CENTER);

    JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, jp);
    pane.setResizeWeight(0.1D);

    rightPanel = new JPanel();
    rightPanel.setBorder(new TitledBorder(new EtchedBorder(), "测试结果页面", TitledBorder.CENTER, TitledBorder.BOTTOM,
        new Font("楷体", Font.PLAIN, 16), Color.BLUE));
    rightPanel.setLayout(new BorderLayout());
    rightPanel.add(pane, BorderLayout.CENTER);

    progressBar = new JProgressBar(JProgressBar.VERTICAL);
    progressBar.setForeground(GREEN);

    scanField = new JTextField("产品编号");
    scanField.setBackground(Color.BLACK);
    scanField.setHorizontalAlignment(SwingConstants.CENTER);
    scanField.setForeground(Color.RED);
    scanField.setFont(new Font("宋体", Font.BOLD, 30));
    scanField.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), "扫描结果:", TitledBorder.CENTER, TitledBorder.TOP,
        new Font("楷体", Font.PLAIN, 16), Color.WHITE));

    productField = new JTextField();
    productField.setBackground(new Color(240, 240, 240));
    productField.setForeground(Color.BLUE);
    productField.setFont(new Font("宋体", Font.BOLD, 40));
    productField.setText(PRODUCT_NAME);
    productField.setHorizontalAlignment(SwingConstants.CENTER);
    productField.setEditable(false);
    productField.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 2), "当前测试机种:", TitledBorder.LEFT,
        TitledBorder.TOP, new Font("楷体", Font.PLAIN, 16), Color.BLACK));

    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 3), "测试步骤及内容", TitledBorder.CENTER,
        TitledBorder.TOP, new Font("楷体", Font.PLAIN, 16), Color.BLACK));
    
    persistScroll = new JScrollPane();
    persistScroll.setPreferredSize(new Dimension(100, 200));
    persistScroll.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), " ", TitledBorder.LEFT, TitledBorder.TOP,
        new Font("楷体", Font.PLAIN, 16), Color.BLACK));
    //persistScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    persistScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    tablePanel.add(dataPanel, BorderLayout.CENTER);
    tablePanel.add(progressBar, BorderLayout.EAST);
    tablePanel.add(scanField, BorderLayout.SOUTH);
    tablePanel.add(productField, BorderLayout.NORTH);
    tablePanel.add(persistScroll, BorderLayout.WEST);

    editArea = new JTextArea();
    editArea.setFont(new Font("楷体", Font.PLAIN, 13));

    JScrollPane editScroll = new JScrollPane(editArea);
    editScroll.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "软件日志:", TitledBorder.LEFT, TitledBorder.TOP,
        new Font("宋体", Font.PLAIN, 13), Color.BLACK));

    leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, editScroll);
    leftSplitPane.setResizeWeight(0.85D);
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSplitPane, rightPanel);
    splitPane.setResizeWeight(0.75D);
    frame.getContentPane().add(splitPane, BorderLayout.CENTER);

    JLabel pksLabel = new JLabel(" *版权所有：广州番禺旭东阪田电子有限公司(Kyokuto)");
    pksLabel.setForeground(Color.GRAY);
    pksLabel.setFont(new Font("楷体", Font.PLAIN, 13));
    pksLabel.setHorizontalAlignment(SwingConstants.LEFT);
    frame.getContentPane().add(pksLabel, BorderLayout.SOUTH);
    /*============================================================================================*/
    // 窗口"X"关闭事件
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        close();
      }
    });
    // 退出菜单事件
    exitItem.addActionListener(e -> close());
    // 退出系统按钮事件
    exitButt.addActionListener(e -> close());
    // 捺印事件
    nayinButt.addActionListener(e -> nayinMethod());
    usartButt.addActionListener(e -> usartMethod());
    usartItem.addActionListener(e -> usartMethod());
    resultButt.addActionListener(e -> resultView());
    resultItem.addActionListener(e -> resultView());
    ngReportButt.addActionListener(e -> reportView());
    ngReportItem.addActionListener(e -> reportView());
    //打开菜单事件
    openItem.addActionListener(e -> {
      logBySelf(e.getActionCommand());
      FileDialog openDialog = new FileDialog(frame, "打开文件", FileDialog.LOAD);
      openDialog.setDirectory(".");
      openDialog.setVisible(true);
    });
  }

  /**
   * 添加运行时日志
   * 
   * @param info
   */
  public void logBySelf(String info) {
    editArea.append(dateFormat.format(new Date()) + "::" + info + SEPARATOR);
  }
  /**
   * 保存日志到本地
   * @param directory 路径
   */
  public void log2txt(String directory) {
    String infos = editArea.getText();
    FileWriter writer = null;
    File path = new File(directory);
    if (!path.isDirectory()) {
      path.mkdirs();
    }
    File file = new File(path, PRODUCT_NAME + System.currentTimeMillis() + ".log");
    try {
      if (!file.exists()) {
        file.createNewFile();
      }
      writer = new FileWriter(file);
      writer.write(infos);
      writer.flush();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "日志文件创建失败：" + e.getLocalizedMessage());
    } finally {
      try {
        writer.close();
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "日志文件创建失败：" + e.getLocalizedMessage());
      }
    }
  }

  /**
   * 计算测试时间显示
   * 
   * @param num
   */
  public String calculate(int num) {

    int thousand = (num / 1000);
    int hundred = (num % 1000 / 100);
    int ten = (num % 100 / 10);

    return "" + thousand + "." + hundred + ten;
  }

  /**
   * 刷新饼图的方法
   * 
   * @param ok
   * @param ng
   */
  public void setPieChart(int ok, int ng) {
    piePlot.setDataset(MyPieChart.getDataSet(ok, ng));
  }
  /**
   * 验证管理员密码是否正确
   * 
   * @param command
   *          密码
   * @return 布尔值
   */
  abstract public boolean pwdIsPassed(String command);
  /**
   * 打开串口调试工具方法
   */
  abstract public void usartMethod();
  /**查看测试结果方法*/
  abstract public void resultView();
  /**查看不良报告方法*/
  abstract public void reportView();
  /**捺印事件*/
  abstract public void nayinMethod();
  /**
   * 系统退出
   */
  abstract public void close();
  /**
   * 测试数据及测试时间显示面板
   * 
   * @author hw076
   *
   */
  class MyPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public MyPanel(String title, JTextField field) {
      TitledBorder tb = new TitledBorder(new EtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP,
          new Font("等线", Font.ITALIC, 13), Color.BLUE);
      setBorder(tb);
      setLayout(new BorderLayout());
      add(field, BorderLayout.CENTER);
    }
  }

}
