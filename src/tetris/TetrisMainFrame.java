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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.StringTokenizer;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class TetrisMainFrame extends JFrame{

    private static final long serialVersionUID = 1L;
    public static boolean isApplet = false;
    public static final Color frameBackgroundColor = new Color(0, 0, 0);
    public static final Color textColor = new Color(200, 200, 200);
    public static final Color gameOverTextColor = new Color(255, 255, 255);
    public static final Color buttonTextColor = new Color(0, 0, 0);
    public static final Color playingPanelColor = new Color(128, 128, 128);
    public static final Color[] tetriminosColor = {new Color(0, 0, 0),
        new Color(0x00, 0xf0, 0xf0), //I
        new Color(0x00, 0x00, 0xf0), //J
        new Color(0xf0, 0xa0, 0x00), //L
        new Color(0xf0, 0xf0, 0x00), //O
        new Color(0x00, 0xf0, 0x00), //S
        new Color(0xa0, 0x00, 0xf0), //T
        new Color(0xf0, 0x00, 0x00)  //Z
    };
    private static TetrisMainFrame instance;

    private JPanel contentPanel = null;
    private JPanel playingPanel = null;
    private JPanel infoPanel = null;
    private JPanel nextTetriminosPanel = null;
    private JLabel levelLabel = null;
    private JLabel scoreLabel = null;
    private JLabel timeLabel = null;
    private JLabel hiscoreLabel = null;
    private JLabel linesLabel = null;
    private JButton newgameLabel = null;
    private JToggleButton pauseLabel = null;
    private BufferedImage bufferImage;
    private Graphics2D bufferGraphics2D;
    private int blockHeight = 0;
    private int blockWidth = 0;
    private int hiScore;
    private int gameTime;
    private ActionListener taskPerformer;
    private Timer timer;
    
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = TetrisMainFrame.getInstance().getSize();
        TetrisMainFrame.getInstance().setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        TetrisMainFrame.getInstance().setVisible(true);
        TetrisMainFrame.getInstance().repaintPlayingPanel();
    }
    
    private TetrisMainFrame() {
        super();
        initialize();
    }

    public static TetrisMainFrame getInstance() {
        if (instance == null) {
            instance = new TetrisMainFrame();
        }
        return instance;
    }
    
    private void initialize() {  
        this.setSize(305, 391);
        this.setMinimumSize(new Dimension(305, 391));
        this.setContentPane(getJContentPane());
        if (!isApplet){
            URL url = this.getClass().getResource("icon.png");
            ImageIcon image = new ImageIcon(url);
            setIconImage(image.getImage());
        }
        this.setTitle("Tetris");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (!isApplet){
                    int score = TetrisEngine.getInstance().getScore();
                    if (score > getHiScore()) {
                        setHiScore(score);
                    }
                    System.exit(0);
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int blockHeight = (contentPanel.getHeight() - 4) / 20;
                int blockWidth = (contentPanel.getWidth() - 116 - 4) / 10;
                if (blockWidth > blockHeight) {
                    blockWidth = blockHeight;
                }
                playingPanel.setSize(blockWidth * 10 + 6, contentPanel.getHeight());
                playingPanel.setPreferredSize(new Dimension(blockWidth * 10 + 6, contentPanel.getHeight()));

                playingPanel.repaint();
            }
        });

        taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (!TetrisEngine.getInstance().stop && !pauseLabel.isSelected()) {
                    gameTime++;
                    setTime(gameTime);
                }
            }
        };
        timer = new Timer(1000, taskPerformer);
        timer.setRepeats(true);
        timer.start();
        
        bufferImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        loadHiScore();
    }

    public int getHiScore() {
        return hiScore;
    }

    public void setHiScore(int hiScore) {
        this.hiScore = hiScore;
        hiscoreLabel.setText("Hi-score: " + hiScore);
        saveHiScore(hiScore);
    }

    public void repaintPlayingPanel() {
        playingPanel.repaint();
    }

    public void repaintNextTetriminosPanel() {
        nextTetriminosPanel.repaint();
    }

    public JPanel getJContentPane() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getPlayingPanel(), BorderLayout.WEST);
            contentPanel.add(getInfoPanel(), BorderLayout.CENTER);
            contentPanel.setBackground(frameBackgroundColor);
        }
        return contentPanel;
    }

    private JPanel getPlayingPanel() {
        if (playingPanel == null) {
            playingPanel = new JPanel(){
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    bufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
                    bufferGraphics2D = bufferImage.createGraphics();

                    RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    bufferGraphics2D.setRenderingHints(rh);

                    blockHeight = (this.getHeight() - 4) / 20;
                    blockWidth = (this.getWidth() - 4) / 10;

                    if (blockWidth>blockHeight) blockWidth = blockHeight;
                    else blockHeight = blockWidth;

                    int startX = 2;
                    int startY = 2;

                    bufferGraphics2D.setColor(TetrisMainFrame.frameBackgroundColor);
                    bufferGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());

                    bufferGraphics2D.setColor(TetrisMainFrame.playingPanelColor);
                    bufferGraphics2D.fillRect(startX+1, startY+1, blockWidth * 10, blockHeight * 20);

                    //draw exist blocks
                    for (int i = 0; i < 20; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (TetrisEngine.getInstance().playingField[i][j] != 0) {
                                int c = TetrisEngine.getInstance().playingField[i][j];
                                bufferGraphics2D.setColor(Color.BLACK);
                                bufferGraphics2D.drawRect(startX + j * blockWidth, startY + i * blockHeight, 
                                        blockWidth, blockHeight);
                                bufferGraphics2D.setColor(TetrisMainFrame.tetriminosColor[TetrisEngine.getInstance().playingField[i][j]]);
                                bufferGraphics2D.fillRect(startX + j * blockWidth + 1, startY + i * blockHeight + 1,
                                        blockWidth - 1, blockHeight - 1);
                            }
                        }
                    }

                    //draw current tetrimonos
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (TetrisEngine.getInstance().currentTetrimonosShape[i][j]) {
                                bufferGraphics2D.setColor(Color.BLACK);
                                bufferGraphics2D.drawRect(startX + (TetrisEngine.getInstance().currentTetrimonosX + j) * blockWidth, 
                                        startY + (TetrisEngine.getInstance().currentTetrimonosY + i) * blockHeight, 
                                        blockWidth, blockHeight);
                                bufferGraphics2D.setColor(TetrisMainFrame.tetriminosColor[TetrisEngine.getInstance().currentTetrimonos]);
                                bufferGraphics2D.fillRect(startX + (TetrisEngine.getInstance().currentTetrimonosX + j) * blockWidth + 1, 
                                        startY + (TetrisEngine.getInstance().currentTetrimonosY + i) * blockHeight + 1, 
                                        blockWidth - 1, blockHeight - 1);
                            }
                        }
                    }

                    bufferGraphics2D.setColor(TetrisMainFrame.frameBackgroundColor);
                    bufferGraphics2D.fillRect(0, 0, this.getWidth(), startY);
                    bufferGraphics2D.setColor(Color.BLACK);
                    bufferGraphics2D.drawRect(startX, startY, blockWidth * 10 + 1, blockHeight * 20 + 1);


                    if (TetrisEngine.getInstance().isGameOver()){
                        bufferGraphics2D.setColor(TetrisMainFrame.gameOverTextColor);
                        bufferGraphics2D.setFont(new Font("Verdana", Font.PLAIN, 46));
                        bufferGraphics2D.drawString("GAME", this.getWidth()/2-65, this.getHeight()/2-25);
                        bufferGraphics2D.drawString("OVER", this.getWidth()/2-61, this.getHeight()/2+25);
                    }

                    g.drawImage(bufferImage, 0, 0, null);
                    
                }
            };
            playingPanel.setSize(186, 400);
            playingPanel.setPreferredSize(new Dimension(186, 400));

            final String downPressed = "pressed DOWN";
            playingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(downPressed), downPressed);
            playingPanel.getActionMap().put(downPressed, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ignored) {
                    TetrisEngine.getInstance().moveDown();
                    repaint();
                }
            });

            final String upPressed = "pressed UP";
            playingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(upPressed), upPressed);
            playingPanel.getActionMap().put(upPressed, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ignored) {
                    TetrisEngine.getInstance().rotate();
                    repaint();
                }
            });

            final String leftPressed = "pressed LEFT";
            playingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(leftPressed), leftPressed);
            playingPanel.getActionMap().put(leftPressed, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ignored) {
                    TetrisEngine.getInstance().moveLeft();
                    repaint();
                }
            });

            final String rightPressed = "pressed RIGHT";
            playingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(rightPressed), rightPressed);
            playingPanel.getActionMap().put(rightPressed, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ignored) {
                    TetrisEngine.getInstance().moveRight();
                    repaint();
                }
            });

            final String sPressed = "pressed S";
            playingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(sPressed), sPressed);
            playingPanel.getActionMap().put(sPressed, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ignored) {
                    TetrisEngine.getInstance().moveDown();
                    repaint();
                }
            });

            final String wPressed = "pressed W";
            playingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(wPressed), wPressed);
            playingPanel.getActionMap().put(wPressed, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ignored) {
                    TetrisEngine.getInstance().rotate();
                    repaint();
                }
            });

            final String aPressed = "pressed A";
            playingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(aPressed), aPressed);
            playingPanel.getActionMap().put(aPressed, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ignored) {
                    TetrisEngine.getInstance().moveLeft();
                    repaint();
                }
            });

            final String dPressed = "pressed D";
            playingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(dPressed), dPressed);
            playingPanel.getActionMap().put(dPressed, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ignored) {
                    TetrisEngine.getInstance().moveRight();
                    repaint();
                }
            });
        }
        return playingPanel;
    }

    private JPanel getInfoPanel() {
        if (infoPanel == null) {
            linesLabel = new JLabel();
            linesLabel.setText("Lines: 0");
            linesLabel.setLocation(new Point(2, 105));
            linesLabel.setSize(new Dimension(110, 23));
            linesLabel.setForeground(textColor);
            hiscoreLabel = new JLabel();
            hiscoreLabel.setText("Hi-score:");
            hiscoreLabel.setSize(new Dimension(110, 23));
            hiscoreLabel.setLocation(new Point(2, 155));
            hiscoreLabel.setForeground(textColor);
            timeLabel = new JLabel();
            timeLabel.setText("Time: 0:00:00");
            timeLabel.setSize(new Dimension(110, 23));
            timeLabel.setLocation(new Point(2, 180));
            timeLabel.setForeground(textColor);
            scoreLabel = new JLabel();
            scoreLabel.setText("Score: 0");
            scoreLabel.setSize(new Dimension(110, 23));
            scoreLabel.setLocation(new Point(2, 130));
            scoreLabel.setForeground(textColor);
            levelLabel = new JLabel();
            levelLabel.setText("Level: 0");
            levelLabel.setSize(new Dimension(110, 23));
            levelLabel.setLocation(new Point(2, 80));
            levelLabel.setForeground(textColor);
            infoPanel = new JPanel();
            infoPanel.setLayout(null);
            infoPanel.setPreferredSize(new Dimension(116, 400));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.add(getNextTetriminosPanel(), null);
            infoPanel.add(levelLabel, null);
            infoPanel.add(scoreLabel, null);
            infoPanel.add(timeLabel, null);
            infoPanel.add(getJButtonNewGame(), null);
            infoPanel.add(getJToggleButtonPause(), null);
            infoPanel.add(hiscoreLabel, null);
            infoPanel.add(linesLabel, null);
            infoPanel.setBackground(frameBackgroundColor);
        }
        return infoPanel;
    }

    private JPanel getNextTetriminosPanel() {
        if (nextTetriminosPanel == null) {
            nextTetriminosPanel = new JPanel(){
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(TetrisMainFrame.frameBackgroundColor);
                    g.fillRect(0, 0, this.getWidth(), this.getHeight());

                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (TetrisEngine.tetriminosShapes[TetrisEngine.getInstance().nextTetrimonos][i][j]) {
                                g.setColor(Color.BLACK);
                                g.drawRect(j * 15, i * 15, 15, 15);
                                g.setColor(TetrisMainFrame.tetriminosColor[TetrisEngine.getInstance().nextTetrimonos]);
                                g.fillRect(j * 15 + 1, i * 15 + 1, 14, 14);
                            }
                        }
                    }
                }
            };
            nextTetriminosPanel.setLayout(new GridBagLayout());
            nextTetriminosPanel.setLocation(new Point(29, 10));
            nextTetriminosPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            nextTetriminosPanel.setSize(new Dimension(65, 65));
        }
        return nextTetriminosPanel;
    }

    private JButton getJButtonNewGame() {
        if (newgameLabel == null) {
            newgameLabel = new JButton();
            newgameLabel.setPreferredSize(new Dimension(110, 26));
            newgameLabel.setLocation(new Point(2, 205));
            newgameLabel.setSize(new Dimension(110, 30));
            newgameLabel.setText("New game");
            newgameLabel.setForeground(buttonTextColor);
            newgameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    pauseLabel.setSelected(false);
                    TetrisEngine.getInstance().pause = false;
                    TetrisEngine.getInstance().newGame();
                    gameTime = 0;
                }
            });
        }
        return newgameLabel;
    }

    private JToggleButton getJToggleButtonPause() {
        if (pauseLabel == null) {
            pauseLabel = new JToggleButton();
            pauseLabel.setText("Pause");
            pauseLabel.setSize(new Dimension(110, 30));
            pauseLabel.setLocation(new Point(2, 240));
            pauseLabel.setForeground(buttonTextColor);
            pauseLabel.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent e) {
                    TetrisEngine.getInstance().pause = pauseLabel.isSelected();
                }
            });
        }
        return pauseLabel;
    }

    public void setScoreLabels(int score, int lines, int level) {
        scoreLabel.setText("Score: " + score);
        linesLabel.setText("Lines: " + lines);
        levelLabel.setText("Level: " + level);
    }

    public void setTime(int secondsCount) {
        timeLabel.setText("Time: " + String.format("%01d:%02d:%02d", (secondsCount / (60 * 60)) % 24, 
                (secondsCount / (60)) % 60, (secondsCount) % 60));
    }

    public void loadHiScore() {
        if (!isApplet){
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
                        hiscoreLabel.setText("Hi-score: " + hiScore);
                    }
                }
            } catch (IOException e) {
            }
        }
    }

    public void saveHiScore(int hi_score) {
        if (!isApplet){
            File file = new File("tetris_hi_score");
            try {
                OutputStreamWriter f = new FileWriter(file);
                f.write("hi_score " + hi_score);
                f.close();
            } catch (IOException e) {
            }
        }
    }
}
