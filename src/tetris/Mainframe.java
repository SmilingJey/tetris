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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class Mainframe extends JFrame implements Periodic{
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
    private int HiScore;
    public int gameTime;
    
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
    
    public int getHiScore(){
        return HiScore;
    }
    
    public void setHiScore(int hi_score){
        HiScore = hi_score;
        setHiScoreLabel(hi_score);
        saveHiScore(hi_score);
    }

    public void redrawPlayingPanel(){
        playingPanel.draw_all();
        jNextTetriminosPanel.repaint();
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

        //playingPanel.draw_all();
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
            jLabel_record.setText("Hi-score:");
            jLabel_record.setSize(new Dimension(110, 23));
            jLabel_record.setLocation(new Point(5, 155));
            jLabel_time = new JLabel();
            jLabel_time.setText("Time:");
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
        if (jNextTetriminosPanel == null) {
            jNextTetriminosPanel = new NextTetriminosPanel();
            jNextTetriminosPanel.setLayout(new GridBagLayout());
            jNextTetriminosPanel.setLocation(new Point(29, 10));
            jNextTetriminosPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            jNextTetriminosPanel.setSize(new Dimension(65, 65));
        }
        return jNextTetriminosPanel;
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
                    TetrisEngine.getInstance().newGame();
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
                    TetrisEngine.getInstance().timer.pause = jToggleButton_pause.isSelected();
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
        String hour = Integer.toString(secondsCount/3600);
        String minutes = (Integer.toString(secondsCount%3600/60).length() == 1) ? "0" + 
                Integer.toString(secondsCount%3600/60) : Integer.toString(secondsCount%3600/60);
        String seconds = (Integer.toString(secondsCount%60).length() == 1) ? "0" + 
                Integer.toString(secondsCount%60) : Integer.toString(secondsCount%60);
        jLabel_time.setText("Time: " + hour + ":" + minutes + ":" + seconds);
    }

    public void setHiScoreLabel(int hi_score) {
        jLabel_record.setText("Hi-score: " + hi_score);
    }

    public int loadHiScore() {
        File file = new File("tetris_hi_score");
        try{
            BufferedReader reader = new BufferedReader(new FileReader (file));
            String line = reader.readLine();
            reader.close();
        
            if (line != null){
                StringTokenizer st = new StringTokenizer(line);
                if (st.countTokens()>1) {
                    st.nextToken();
                    return Integer.parseInt(st.nextToken());
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void saveHiScore(int hi_score){
        File file = new File("tetris_hi_score");
        try {
            OutputStreamWriter f = new FileWriter(file);
            f.write("hi_score " + hi_score);
            f.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void doSomeThing() {
        if (!TetrisEngine.getInstance().stop && !jToggleButton_pause.isSelected()){
            gameTime++;
            setTime(gameTime); 
        }
    }
}

