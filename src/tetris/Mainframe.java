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
import java.awt.Canvas;
import javax.swing.BorderFactory;
import java.awt.Point;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class Mainframe extends JFrame {

    private static final long serialVersionUID = 1L;
    public static Mainframe instanes;
    private JPanel jContentPane = null;
    private JPanel jPanel = null;
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

    /**
     * This is the default constructor
     */
    public Mainframe() {
        super();
        instanes = this;
        initialize();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(320, 400);
        this.setMinimumSize(new Dimension(320, 400));
        this.setContentPane(getJContentPane());
        URL url = this.getClass().getResource("res/icon.png");
        ImageIcon image = new ImageIcon(url);
        setIconImage(image.getImage());
        this.setTitle("JTetris");
        this.addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        Game_canvas.instanes.repaint();

    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanel(), BorderLayout.CENTER);
            jContentPane.add(getJPanel_info(), BorderLayout.EAST);
        }
        return jContentPane;
    }

    /**
     * This method initializes jPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new Game_canvas();
            jPanel.setBackground(Color.white);
        }
        return jPanel;
    }

    /**
     * This method initializes jPanel_info
     *
     * @return javax.swing.JPanel
     */
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
            //jLabel.setText("jedy(c)2009");
            jLabel.setSize(new Dimension(110, 23));
            jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel.setLocation(new Point(5, 355));
            jPanel_info = new JPanel();
            jPanel_info.setLayout(null);
            jPanel_info.setPreferredSize(new Dimension(120, 380));
            //jPanel_info.setBorder(BorderFactory.createLineBorder(Color.black, 1));
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

    /**
     * This method initializes jPanel1
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new Next_figura();
            jPanel1.setLayout(new GridBagLayout());
            jPanel1.setLocation(new Point(29, 10));
            jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            jPanel1.setSize(new Dimension(65, 65));
        }
        return jPanel1;
    }

    /**
     * This method initializes jButton_newgame
     *
     * @return javax.swing.JButton
     */
    private JButton getJButton_newgame() {
        if (jButton_newgame == null) {
            jButton_newgame = new JButton();
            jButton_newgame.setPreferredSize(new Dimension(110, 26));
            jButton_newgame.setLocation(new Point(5, 205));
            jButton_newgame.setSize(new Dimension(110, 30));
            jButton_newgame.setText("New game");
            jButton_newgame.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Game.instanes.new_game();
                }
            });
        }
        return jButton_newgame;
    }

    /**
     * This method initializes jToggleButton_pause
     *
     * @return javax.swing.JToggleButton
     */
    private JToggleButton getJToggleButton_pause() {
        if (jToggleButton_pause == null) {
            jToggleButton_pause = new JToggleButton();
            jToggleButton_pause.setText("Pause");

            jToggleButton_pause.setSize(new Dimension(110, 30));
            jToggleButton_pause.setLocation(new Point(5, 240));
            jToggleButton_pause.addChangeListener(new javax.swing.event.ChangeListener() {

                public void stateChanged(javax.swing.event.ChangeEvent e) {
                    Game.instanes.tic.pause = jToggleButton_pause.isSelected();
                    Game.instanes.time.pause = jToggleButton_pause.isSelected();
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

    public void set_time(int h, int m, int s) {
        String mm = (Integer.toString(m).length() == 1) ? "0" + Integer.toString(m) : Integer.toString(m);
        String ss = (Integer.toString(s).length() == 1) ? "0" + Integer.toString(s) : Integer.toString(s);
        jLabel_time.setText("Time: " + h + ":" + mm + ":" + ss);
    }

    public void set_record() {
        jLabel_record.setText("Record: " + Recordframe.instance.max_rec);
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"

