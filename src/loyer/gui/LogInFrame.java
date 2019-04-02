package loyer.gui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.jfree.ui.RefineryUtilities;

public abstract class LogInFrame {
  
  /**主页面*/
  protected JFrame frame;
  /**获取当前屏幕宽度*/
  protected static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
  /**获取当前屏幕高度*/
  protected static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
  /**标签面板*/
  protected JPanel panel1;
  /**机种显示面板*/
  protected JPanel panel2;
  /**按钮面板*/
  protected JPanel panel3;
  /**公司标签*/
  protected JLabel pksLabel;
  /**退出按钮*/
  protected JButton exitButt;
  /**登录按钮*/
  protected JButton logInButt;
  /**机种选择按钮*/
  protected JButton chooseButt;
  /**机种名显示文本框*/
  protected JTextField textField;
  /**机种计数器*/
  protected int typeCount = 1;
  /**是否登录成功标志位*/
  protected boolean isDataView = false;
  /**
   * Create the application.
   */
  public LogInFrame() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    
    try {
      //将界面风格设置成和系统一置
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
    }//*/
    
    frame = new JFrame();
    Image img = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/pic/Kyokuto.png"));
    frame.setIconImage(img);
    frame.getContentPane().setBackground(new Color(255, 255, 255));
    frame.setBounds(WIDTH / 3, HEIGHT / 3, WIDTH / 3, HEIGHT / 4);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setUndecorated(true);  //去掉窗口装饰
    frame.getContentPane().setLayout(new GridLayout(3, 1));
    
    //添加全局enter键监听
    Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
      
      @Override
      public void eventDispatched(AWTEvent event) {
        //如果有键按下，且按键是enter键，点击登录按钮
        if(((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED && ((KeyEvent)event).getKeyChar() == KeyEvent.VK_ENTER) {
          logInButt.doClick();
        }
      }
    }, AWTEvent.KEY_EVENT_MASK);
    
    panel1 = new JPanel();
    panel1.setBorder(new LineBorder(new Color(255, 255, 255), 5));
    panel1.setBackground(new Color(220, 220, 220));
    panel1.setOpaque(false);
    frame.getContentPane().add(panel1);
    panel1.setLayout(new BorderLayout(0, 0));
    
    pksLabel = new JLabel();
    pksLabel.setHorizontalAlignment(SwingConstants.LEFT);
    pksLabel.setForeground(new Color(0, 0, 0));
    pksLabel.setBackground(new Color(220, 220, 220));
    pksLabel.setFont(new Font("楷体", Font.BOLD | Font.ITALIC, 16));
    pksLabel.setIcon(new ImageIcon(this.getClass().getResource("/pic/pks.jpg")));
    panel1.add(pksLabel, BorderLayout.CENTER);
    
    panel2 = new JPanel();
    panel2.setOpaque(false);
    panel2.setBackground(new Color(220, 220, 220));
    panel2.setBorder(new LineBorder(new Color(245, 245, 245), 5, true));
    frame.getContentPane().add(panel2);
    panel2.setLayout(new BorderLayout(7, 0));
    
    chooseButt = new JButton("选择机种");
    chooseButt.addActionListener(e -> chooseEvent());
    chooseButt.setFont(new Font("宋体", Font.PLAIN, 14));
    chooseButt.setForeground(Color.BLACK);
    chooseButt.setBackground(new Color(255, 255, 255));
    panel2.add(chooseButt, BorderLayout.EAST);
    
    textField = new JTextField();
    textField.setEditable(false);
    textField.setHorizontalAlignment(SwingConstants.CENTER);
    textField.setFont(new Font("楷体", Font.BOLD, 22));
    //设置默认显示机种
    textField.setText("机种名");
    textField.setForeground(new Color(255, 0, 0));
    textField.setBackground(new Color(0, 0, 0));
    panel2.add(textField, BorderLayout.CENTER);
    textField.setColumns(10);
    
    panel3 = new JPanel();
    panel3.setBackground(new Color(245, 245, 245));
    panel3.setLayout(new GridLayout(1, 2, 20, 5));
    panel3.setOpaque(false);
    TitledBorder tb = new TitledBorder(new EtchedBorder(), "先选择机种，然后点击登录按钮", TitledBorder.CENTER, 
        TitledBorder.TOP, new Font("等线", Font.ITALIC, 13), Color.BLUE);
    panel3.setBorder(tb);
    frame.getContentPane().add(panel3);
    
    exitButt = new JButton("退出(Exit)");
    exitButt.setFont(new Font("宋体", Font.PLAIN, 13));
    exitButt.addActionListener(e -> System.exit(0));  //退出系统
    exitButt.setBackground(new Color(255, 255, 255));
    exitButt.setForeground(new Color(0, 0, 0));
    panel3.add(exitButt);
    
    logInButt = new JButton("登录(LogIn)");
    //登录事件
    logInButt.addActionListener(e -> logInEvent());
    logInButt.setForeground(new Color(0, 0, 0));
    logInButt.setFont(new Font("宋体", Font.PLAIN, 16));
    logInButt.setBackground(new Color(255, 255, 255));
    panel3.add(logInButt);
    
    frame.pack();
    RefineryUtilities.centerFrameOnScreen(frame);
  }
  /**
   * 登录事件
   */
  public abstract void logInEvent();
  /**
   * 选择机种事件
   */
  public abstract void chooseEvent();

}
