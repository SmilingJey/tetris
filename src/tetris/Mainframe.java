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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class Mainframe extends JFrame implements Periodic {

    private static final long serialVersionUID = 1L;
    private static Mainframe instance;
    private JPanel jContentPane = null;
    private PlayingPanel playingPanel = null;
    private JPanel jPanel_info = null;
    private JPanel jNextTetriminosPanel = null;
    private JLabel jLabel = null;
    private JLabel jLabel_level = null;
    private JLabel jLabel_score = null;
    private JLabel jLabel_time = null;
    private JButton jButton_newgame = null;
    private JToggleButton jToggleButton_pause = null;
    private JLabel jLabel_record = null;
    private JLabel jLabel_lines = null;
    private int hiScore;
    private Timer timer;
    public int gameTime;

    private Mainframe() {
        super();
        initialize();
    }

    public static Mainframe getInstance() {
        if (instance == null) instance = new Mainframe();
        return instance;
    }
       
    private void initialize() {
        this.setSize(320, 400);
        this.setMinimumSize(new Dimension(320, 400));
        this.setContentPane(getJContentPane());
        URL url = this.getClass().getResource("res/icon.png");
        ImageIcon image = new ImageIcon(url);
        setIconImage(image.getImage());
        this.setTitle("Tetris");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                playingPanel.repaint();
            }
        });

        loadHiScore();
    }

    public int getHiScore() {
        return hiScore;
    }

    public void setHiScore(int hiScore) {
        this.hiScore = hiScore;
        jLabel_record.setText("Hi-score: " + hiScore);
        saveHiScore(hiScore);
    }

    public void repaintPlayingPanel() {
        playingPanel.repaint();
        jNextTetriminosPanel.repaint();
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getPlayingPanel(), BorderLayout.CENTER);
            jContentPane.add(getJPanel_info(), BorderLayout.EAST);
        }
        return jContentPane;
    }

    private JPanel getPlayingPanel() {
        if (playingPanel == null) {
            playingPanel = new PlayingPanel();
            playingPanel.setBackground(Color.white);
        }
        return playingPanel;
    }

    private JPanel getJPanel_info() {
        if (jPanel_info == null) {
            jLabel_lines = new JLabel();
            jLabel_lines.setText("Lines: 0");
            jLabel_lines.setLocation(new Point(2, 105));
            jLabel_lines.setSize(new Dimension(110, 23));
            jLabel_record = new JLabel();
            jLabel_record.setText("Hi-score:");
            jLabel_record.setSize(new Dimension(110, 23));
            jLabel_record.setLocation(new Point(2, 155));
            jLabel_time = new JLabel();
            jLabel_time.setText("Time: 0:00:00");
            jLabel_time.setSize(new Dimension(110, 23));
            jLabel_time.setLocation(new Point(2, 180));
            jLabel_score = new JLabel();
            jLabel_score.setText("Score: 0");
            jLabel_score.setSize(new Dimension(110, 23));
            jLabel_score.setLocation(new Point(2, 130));
            jLabel_level = new JLabel();
            jLabel_level.setText("Level: 0");
            jLabel_level.setSize(new Dimension(110, 23));
            jLabel_level.setLocation(new Point(2, 80));
            jLabel = new JLabel();
            jLabel.setSize(new Dimension(110, 23));
            jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel.setLocation(new Point(2, 355));
            jPanel_info = new JPanel();
            jPanel_info.setLayout(null);
            jPanel_info.setPreferredSize(new Dimension(116, 380));
            jPanel_info.setBackground(Color.WHITE);
            jPanel_info.add(getNextTetriminosPanel(), null);
            jPanel_info.add(jLabel, null);
            jPanel_info.add(jLabel_level, null);
            jPanel_info.add(jLabel_score, null);
            jPanel_info.add(jLabel_time, null);
            jPanel_info.add(getJButtonNewGame(), null);
            jPanel_info.add(getJToggleButtonPause(), null);
            jPanel_info.add(jLabel_record, null);
            jPanel_info.add(jLabel_lines, null);
        }
        return jPanel_info;
    }

    private JPanel getNextTetriminosPanel() {
        if (jNextTetriminosPanel == null) {
            jNextTetriminosPanel = new NextTetriminosPanel();
            jNextTetriminosPanel.setLayout(new GridBagLayout());
            jNextTetriminosPanel.setLocation(new Point(29, 10));
            jNextTetriminosPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            jNextTetriminosPanel.setSize(new Dimension(65, 65));
        }
        return jNextTetriminosPanel;
    }

    private JButton getJButtonNewGame() {
        if (jButton_newgame == null) {
            jButton_newgame = new JButton();
            jButton_newgame.setPreferredSize(new Dimension(110, 26));
            jButton_newgame.setLocation(new Point(2, 205));
            jButton_newgame.setSize(new Dimension(110, 30));
            jButton_newgame.setText("New game");
            jButton_newgame.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    TetrisEngine.getInstance().newGame();
                    if (timer != null) {
                        timer.interrupt();
                        timer = null;
                    }
                    gameTime = 0;
                    timer = new Timer(Mainframe.getInstance(), 1000);
                    timer.start();
                }
            });
        }
        return jButton_newgame;
    }

    private JToggleButton getJToggleButtonPause() {
        if (jToggleButton_pause == null) {
            jToggleButton_pause = new JToggleButton();
            jToggleButton_pause.setText("Pause");

            jToggleButton_pause.setSize(new Dimension(110, 30));
            jToggleButton_pause.setLocation(new Point(2, 240));
            jToggleButton_pause.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent e) {
                    TetrisEngine.getInstance().timer.pauseTimer(jToggleButton_pause.isSelected());
                }
            });
        }
        return jToggleButton_pause;
    }

    public void setScoreLabels(int score, int lines, int level) {
        jLabel_score.setText("Score: " + score);
        jLabel_lines.setText("Lines: " + lines);
        jLabel_level.setText("Level: " + level);
    }

    public void setTime(int secondsCount) {
        long second = (secondsCount) % 60;
        long minute = (secondsCount / (60)) % 60;
        long hour = (secondsCount / (60 * 60)) % 24;
        jLabel_time.setText("Time: " +  String.format("%01d:%02d:%02d", hour, minute, second));
    }

    public void loadHiScore() {
        File file = new File("tetris_hi_score");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();

            if (line != null) {
                StringTokenizer st = new StringTokenizer(line);
                if (st.countTokens() > 1) {
                    st.nextToken();
                    hiScore = Integer.parseInt(st.nextToken());
                    jLabel_record.setText("Hi-score: " + hiScore);
                }
            }
        } catch (IOException e) {
        }
    }

    public void saveHiScore(int hi_score) {
        File file = new File("tetris_hi_score");
        try {
            OutputStreamWriter f = new FileWriter(file);
            f.write("hi_score " + hi_score);
            f.close();
        } catch (IOException e) {
        }
    }

    public void doSomeThing() {
        if (!TetrisEngine.getInstance().stop && !jToggleButton_pause.isSelected()) {
            gameTime++;
            setTime(gameTime);
        }
    }
}
