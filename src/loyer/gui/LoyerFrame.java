package loyer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PiePlot;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import loyer.exception.NoSuchPort;
import loyer.exception.NotASerialPort;
import loyer.exception.PortInUse;
import loyer.exception.SerialPortParamFail;
import loyer.exception.TooManyListeners;
import loyer.serial.SerialPortTools;

import javax.swing.JSeparator;
import javax.swing.JToggleButton;

/**
 * 自定义测试系统显示界面
 * 
 * @author Loyer
 * @coding UTF-8
 */
abstract public class LoyerFrame {

  /** 顶级框架 */
  protected JFrame frame;
  /** 获取当前屏幕宽度 */
  protected static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
  /** 获取当前屏幕高度 */
  protected static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
  /** 菜单条 */
  protected JMenuBar menuBar;
  /** 文件菜单 */
  protected JMenu fileMenu;
  /** 打开菜单 */
  protected JMenuItem openItem;
  /** 退出菜单 */
  protected JMenuItem exitItem;
  /** 编辑菜单 */
  protected JMenu editMenu;
  /** 打开调试工具菜单 */
  protected JMenuItem usartItem;
  /** 查看测试结果菜单 */
  protected JMenuItem resultItem;
  /** 查看不良报告菜单 */
  protected JMenuItem ngReportItem;
  /** 工具栏 */
  protected JToolBar toolBar;
  /** 退出按钮 */
  protected JButton exitButt;
  /** 打开调试工具按钮 */
  protected JButton usartButt;
  /** 查看测试结果按钮 */
  protected JButton resultButt;
  /** 查看不良报告按钮 */
  protected JButton ngReportButt;
  /***/
  protected JSplitPane splitPane;
  /** 联系我们菜单 */
  protected JMenuItem contactItem;
  /** 关于菜单 */
  protected JMenuItem aboutItem;
  /** 串口工具栏 */
  protected JToolBar comToolBar;
  /** 串口按钮 */
  protected JToggleButton comButt[] = new JToggleButton[7];
  /** 预定义7个串口 */
  protected SerialPort[] COM = new SerialPort[7];
  /** 串口名 */
  protected String[] comName = new String[7];
  /** 波特率 */
  protected int[] baudrate = new int[7];
  /** 数据位 */
  protected int[] dataBit = new int[7];
  /** 停止位 */
  protected int[] stopBit = new int[7];
  /** 校验位 */
  protected int[] parity = new int[7];
  /** 串口列表 */
  protected ArrayList<String> portList;
  /** 追溯串口按钮 */
  protected JToggleButton traceBackButt;
  /** 测试线程运行状态指示 */
  protected JRadioButton testThreadButt;
  /** 测试数据显示面板 */
  protected JScrollPane dataPanel;
  /** 左页面 */
  protected JSplitPane leftSplitPane;
  /** 右页面 */
  protected JPanel rightPanel;
  /** 进度条 */
  protected JProgressBar progressBar;
  /** 进度条值 */
  protected int progressValue = 0;
  /** 测试总数文本框 */
  protected JTextField totalField;
  /** 测试ok文本框 */
  protected JTextField okField;
  /** 测试ng文本框 */
  protected JTextField ngField;
  /** 测试时间文本框 */
  protected JTextField timeField;
  /** 运行状态文本框 */
  protected JTextField statuField;
  /** 产品型号文本框 */
  protected JTextField productField;
  /** 产品型号 */
  protected String PRODUCT_NAME = "机种名";
  /** 测试总数计数器 */
  protected int totalCount = 0;
  /** 测试ok计数器 */
  protected int okCount = 0;
  /** 测试ng计数器 */
  protected int ngCount = 0;
  /** 测试时间计数器 */
  protected int timeCount = 0;
  /** 饼图面板 */
  protected ChartPanel chartPanel;
  /** 饼图定义类 */
  protected MyPieChart myPie;
  protected PiePlot piePlot;
  /** 产品扫描区域 */
  protected JTextField scanField;
  /** 捺印复选框 */
  protected JCheckBox nayinButt;
  /** 点测复选框 */
  protected JCheckBox spotButt;
  /** 左下侧日志显示区域 */
  protected JTextArea editArea;
  /** 格式化日期显示 */
  protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
  /** 换行符 */
  protected static final String SEPARATOR = System.getProperty("line.separator");
  /** 帮助菜单 */
  protected JMenu helpMenu;
  /** 保留页面 */
  protected JScrollPane persistScroll;
  /** 自定义绿色 */
  protected static final Color GREEN = new Color(0, 204, 51);

  /** 构造器 */
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
    for (int i = 0; i < 7; i++) {
      comButt[i] = new JToggleButton("COM" + (i + 1));
      comButt[i].setFont(new Font("宋体", Font.PLAIN, 12));
      comToolBar.add(comButt[i]);
      if (i < 6) {
        comToolBar.addSeparator();
      }
      comButt[i].addActionListener(new COMListener(i));
    }
    // ===========================
    toolBar.addSeparator();
    JComboBox<String> retrospectiveBox = new JComboBox<>();
    for (int i = 1; i <= 20; i++) {
      retrospectiveBox.addItem("追溯串口:COM" + i);
    }
    retrospectiveBox.setSelectedIndex(6);
    retrospectiveBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (statuField.getText().equals("测试中...")) {
          JOptionPane.showMessageDialog(null, "测试进行中，不可操作！");
          return;
        } else if (e.getStateChange() == ItemEvent.SELECTED) {
          String[] s = ((String) retrospectiveBox.getSelectedItem()).split(":");
          comName[6] = s[1];
          comButt[6].setText(comName[6]);
          if (COM[6] != null) {
            COM[6].close();
            COM[6] = null;
          }
          initCOM(6);
        }
      }
    });
    toolBar.add(retrospectiveBox);

    toolBar.addSeparator();
    testThreadButt = new JRadioButton();
    testThreadButt.setBackground(Color.YELLOW);
    toolBar.add(testThreadButt);

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
    persistScroll.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "保留页", TitledBorder.LEFT, TitledBorder.TOP,
        new Font("宋体", Font.PLAIN, 13), Color.BLACK));
    // persistScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    persistScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    JSplitPane centerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, persistScroll, dataPanel);
    centerSplitPane.setResizeWeight(0.1D);

    tablePanel.add(centerSplitPane, BorderLayout.CENTER);
    tablePanel.add(progressBar, BorderLayout.EAST);
    tablePanel.add(scanField, BorderLayout.SOUTH);
    tablePanel.add(productField, BorderLayout.NORTH);
    // tablePanel.add(persistScroll, BorderLayout.WEST);

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
    /*
     * =============================================================================
     * ===============
     */
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
    // 打开菜单事件
    openItem.addActionListener(e -> {
      logBySelf(e.getActionCommand());
      FileDialog openDialog = new FileDialog(frame, "打开文件", FileDialog.LOAD);
      openDialog.setDirectory(".");
      openDialog.setVisible(true);
    });

    Document dt = statuField.getDocument();
    dt.addDocumentListener(new DocumentListener() {

      @Override
      public void removeUpdate(DocumentEvent e) {
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        if (statuField.getText().equals("PASS")) {
          statuField.setBackground(GREEN);
        } else if (statuField.getText().equals("NG")) {
          statuField.setBackground(Color.RED);
        } else {
          statuField.setBackground(Color.ORANGE);
        }
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
      }
    });

    loadProperties();
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
   * 
   * @param directory
   *          路径
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
   * 导入配置文件
   */
  public void loadProperties() {
    portList = SerialPortTools.findPort();
    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream(new File("SerialPorts.properties")));
      for (int i = 0; i < 7; i++) {
        comName[i] = prop.getProperty("com" + (i + 1) + "Name");
        baudrate[i] = Integer.parseInt(prop.getProperty("com" + (i + 1) + "Baudrate"));
        dataBit[i] = Integer.parseInt(prop.getProperty("com" + (i + 1) + "DataBits"));
        stopBit[i] = Integer.parseInt(prop.getProperty("com" + (i + 1) + "StopBits"));
        parity[i] = Integer.parseInt(prop.getProperty("com" + (i + 1) + "Parity"));
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "配置文件导入失败，请检查后重试！");
    }
  }

  /**
   * 关闭所有串口
   */
  public void closeAllPorts() {
    for (int i = 0; i < 7; i++) {
      if (COM[i] != null) {
        COM[i].close();
        COM[i] = null;
        comButt[i].setSelected(false);
      }
    }
  }

  /**
   * 关闭指定串口
   * 
   * @param port
   */
  public void closePort(int port) {
    if (COM[port] != null) {
      COM[port].close();
      COM[port] = null;
      comButt[port].setSelected(false);
    }
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
  public abstract void usartMethod();

  /** 查看测试结果方法 */
  public abstract void resultView();

  /** 查看不良报告方法 */
  public abstract void reportView();

  /** 捺印事件 */
  public abstract void nayinMethod();

  /**
   * 初始化串口
   * 
   * @param num
   */
  public void initCOM(int num) {
    if (portList.contains(comName[num]) && COM[num] == null) {
      try {
        COM[num] = SerialPortTools.getPort(comName[num], baudrate[num], dataBit[num], stopBit[num], parity[num]);
      } catch (SerialPortParamFail | NotASerialPort | NoSuchPort | PortInUse e) {
        JOptionPane.showMessageDialog(null, comName[num] + e.toString());
      }
      comButt[num].setSelected(true);
      try {
        SerialPortTools.add(COM[num], event -> {
          switch (event.getEventType()) {
          case SerialPortEvent.BI: // 10 通讯中断
          case SerialPortEvent.OE: // 7 溢位（溢出）错误
          case SerialPortEvent.FE: // 9 帧错误
          case SerialPortEvent.PE: // 8 奇偶校验错误
          case SerialPortEvent.CD: // 6 载波检测
          case SerialPortEvent.CTS: // 3 清除待发送数据
          case SerialPortEvent.DSR: // 4 待发送数据准备好了
          case SerialPortEvent.RI: // 5 振铃指示
          case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
            // JOptionPane.showMessageDialog(null, comName[num] + event.getSource());
            break;
          case SerialPortEvent.DATA_AVAILABLE: {
            switch (num) {
            case 0:
              COM1DataArrived();
              break;
            case 1:
              COM2DataArrived();
              break;
            case 2:
              COM3DataArrived();
              break;
            case 3:
              COM4DataArrived();
              break;
            case 4:
              COM5DataArrived();
              break;
            case 5:
              COM6DataArrived();
              break;
            case 6:
              COM7DataArrived();
              break;
            default:
              break;
            }
          }
            break;
          }
        });
      } catch (TooManyListeners e) {
        JOptionPane.showMessageDialog(null, comName[num] + e.toString());
      }
    } else {
      JOptionPane.showMessageDialog(null, "未发现" + comName[num]);
      comButt[num].setSelected(false);
    }
  }

  /**
   * 串口1数据到达
   * 
   * @param port
   *          代表对应串口
   */
  public abstract void COM1DataArrived();

  /**
   * 串口2数据到达
   * 
   * @param port
   *          代表对应串口
   */
  public abstract void COM2DataArrived();

  /**
   * 串口3数据到达
   * 
   * @param port
   *          代表对应串口
   */
  public abstract void COM3DataArrived();

  /**
   * 串口4数据到达
   * 
   * @param port
   *          代表对应串口
   */
  public abstract void COM4DataArrived();

  /**
   * 串口5数据到达
   * 
   * @param port
   *          代表对应串口
   */
  public abstract void COM5DataArrived();

  /**
   * 串口6数据到达
   * 
   * @param port
   *          代表对应串口
   */
  public abstract void COM6DataArrived();

  /**
   * 串口7数据到达
   * 
   * @param port
   *          代表对应串口
   */
  public abstract void COM7DataArrived();

  /**
   * 系统退出
   */
  public abstract void close();

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

  class COMListener implements ActionListener {

    private int num;

    public COMListener(int num) {
      super();
      this.num = num;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (COM[num] == null) {
        initCOM(num);
      } else
        comButt[num].setSelected(true);
    }
  }

}
