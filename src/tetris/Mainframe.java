package tetris;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Point;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class Mainframe extends JFrame {
    private static final long serialVersionUID = 1L;
    private static Mainframe instance;
    private JPanel jContentPane = null;
    private PlayingPanel playingPanel = null;
    private JPanel jPanel_info = null;
    private JPanel jPanel1 = null;
    private JLabel jLabel = null;
    private JLabel jLabel_level = null;
    private JLabel jLabel_score = null;
    private JLabel jLabel_time = null;
    private JButton jButton_newgame = null;
    private JToggleButton jToggleButton_pause = null;
    private JLabel jLabel_record = null;
    private JLabel jLabel_lines = null;
    
    private Mainframe() {
        super();
        initialize();
    }

    public static Mainframe getInstance() {
        if (instance == null) {
            instance = new Mainframe();
        }
        return instance;
    }

    public void redrawPlayingPanel(){
        playingPanel.draw_all();
    }
    
    private void initialize() {
        this.setSize(320, 400);
        this.setMinimumSize(new Dimension(320, 400));
        this.setContentPane(getJContentPane());
        URL url = this.getClass().getResource("res/icon2.png");
        ImageIcon image = new ImageIcon(url);
        setIconImage(image.getImage());
        this.setTitle("Tetris");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        playingPanel.draw_all();
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanel(), BorderLayout.CENTER);
            jContentPane.add(getJPanel_info(), BorderLayout.EAST);
        }
        return jContentPane;
    }

    private JPanel getJPanel() {
        if (playingPanel == null) {
            playingPanel = new PlayingPanel();
            playingPanel.setBackground(Color.white);
        }
        return playingPanel;
    }

    private JPanel getJPanel_info() {
        if (jPanel_info == null) {
            jLabel_lines = new JLabel();
            jLabel_lines.setText("Lines:");
            jLabel_lines.setLocation(new Point(5, 130));
            jLabel_lines.setSize(new Dimension(110, 23));
            jLabel_record = new JLabel();
            jLabel_record.setText("Record:");
            jLabel_record.setSize(new Dimension(110, 23));
            jLabel_record.setLocation(new Point(5, 155));
            jLabel_record.addMouseListener(new java.awt.event.MouseAdapter() {

                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Recordframe.instance.setVisible(true);
                }
            });
            jLabel_time = new JLabel();
            jLabel_time.setText("Game time:");
            jLabel_time.setSize(new Dimension(110, 23));
            jLabel_time.setLocation(new Point(5, 180));
            jLabel_score = new JLabel();
            jLabel_score.setText("Score:");
            jLabel_score.setSize(new Dimension(110, 23));
            jLabel_score.setLocation(new Point(5, 105));
            jLabel_level = new JLabel();
            jLabel_level.setText("Level:");
            jLabel_level.setSize(new Dimension(110, 23));
            jLabel_level.setLocation(new Point(5, 80));
            jLabel = new JLabel();
            jLabel.setSize(new Dimension(110, 23));
            jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel.setLocation(new Point(5, 355));
            jPanel_info = new JPanel();
            jPanel_info.setLayout(null);
            jPanel_info.setPreferredSize(new Dimension(120, 380));
            jPanel_info.setBackground(Color.WHITE);
            jPanel_info.add(getJPanel1(), null);
            jPanel_info.add(jLabel, null);
            jPanel_info.add(jLabel_level, null);
            jPanel_info.add(jLabel_score, null);
            jPanel_info.add(jLabel_time, null);
            jPanel_info.add(getJButton_newgame(), null);
            jPanel_info.add(getJToggleButton_pause(), null);
            jPanel_info.add(jLabel_record, null);
            jPanel_info.add(jLabel_lines, null);
        }
        return jPanel_info;
    }

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new NextTetriminosPanel();
            jPanel1.setLayout(new GridBagLayout());
            jPanel1.setLocation(new Point(29, 10));
            jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            jPanel1.setSize(new Dimension(65, 65));
        }
        return jPanel1;
    }

    private JButton getJButton_newgame() {
        if (jButton_newgame == null) {
            jButton_newgame = new JButton();
            jButton_newgame.setPreferredSize(new Dimension(110, 26));
            jButton_newgame.setLocation(new Point(5, 205));
            jButton_newgame.setSize(new Dimension(110, 30));
            jButton_newgame.setText("New game");
            jButton_newgame.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Game.getInstance().new_game();
                }
            });
        }
        return jButton_newgame;
    }

    private JToggleButton getJToggleButton_pause() {
        if (jToggleButton_pause == null) {
            jToggleButton_pause = new JToggleButton();
            jToggleButton_pause.setText("Pause");

            jToggleButton_pause.setSize(new Dimension(110, 30));
            jToggleButton_pause.setLocation(new Point(5, 240));
            jToggleButton_pause.addChangeListener(new javax.swing.event.ChangeListener() {

                public void stateChanged(javax.swing.event.ChangeEvent e) {
                    Game.getInstance().tic.pause = jToggleButton_pause.isSelected();
                    Game.getInstance().time.pause = jToggleButton_pause.isSelected();
                }
            });
        }
        return jToggleButton_pause;
    }

    public void set_labels(int score, int lines, int level) {
        jLabel_score.setText("Score: " + score);
        jLabel_lines.setText("Lines: " + lines);
        jLabel_level.setText("Level: " + level);
    }

    public void setTime(int secondsCount) {
        String hour = Integer.toString(secondsCount/3600);
        String minutes = (Integer.toString(secondsCount%3600/60).length() == 1) ? "0" + 
                Integer.toString(secondsCount%3600/60) : Integer.toString(secondsCount%3600/60);
        String seconds = (Integer.toString(secondsCount%60).length() == 1) ? "0" + 
                Integer.toString(secondsCount%60) : Integer.toString(secondsCount%60);
        jLabel_time.setText("Time: " + hour + ":" + minutes + ":" + seconds);
    }

    public void set_record() {
        jLabel_record.setText("Record: " + Recordframe.instance.max_rec);
    }
}

