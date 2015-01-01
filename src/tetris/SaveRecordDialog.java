package tetris;

import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class SaveRecordDialog extends JDialog {

    public static SaveRecordDialog instance = null;
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JButton jButton_save = null;
    private JTextField jTextField = null;
    private JLabel jLabel = null;

    /**
     * @param owner
     */
    public SaveRecordDialog(Frame owner) {
        super(owner);
        initialize();
        instance = this;
    }

    public void show(String title, boolean modal) {
        this.setTitle(title);
        this.setModal(modal);
        setVisible(true);
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(330, 94);
        this.setContentPane(getJContentPane());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                setVisible(false);
            }
        });
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(3, 5, 308, 19));
            jLabel.setText("Enter the name for entering into the table of records");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getJButton_save(), null);
            jContentPane.add(getJTextField(), null);
            jContentPane.add(jLabel, null);
        }
        return jContentPane;
    }

    /**
     * This method initializes jButton_save
     *
     * @return javax.swing.JButton
     */
    private JButton getJButton_save() {
        if (jButton_save == null) {
            jButton_save = new JButton();
            jButton_save.setText("Save");
            jButton_save.setSize(new Dimension(78, 25));
            jButton_save.setLocation(new Point(222, 27));
            jButton_save.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Recordframe.instance.add_rec(jTextField.getText() != null ? jTextField.getText() : "",
                            Game.instanes.score);
                    setVisible(false);
                }
            });
        }
        return jButton_save;
    }

    /**
     * This method initializes jTextField
     *
     * @return javax.swing.JTextField
     */
    private JTextField getJTextField() {
        if (jTextField == null) {
            jTextField = new JTextField();
            jTextField.setPreferredSize(new Dimension(4, 30));
            jTextField.setSize(new Dimension(173, 25));
            jTextField.setLocation(new Point(38, 26));
        }
        return jTextField;
    }

}  //  @jve:decl-index=0:visual-constraint="16,16"
