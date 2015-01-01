package tetris;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JList;

public class Recordframe extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JList jList = null;
    public static Recordframe instance;
    public String name[] = new String[20];  //  @jve:decl-index=0:
    public int rec[] = new int[20];
    public int max_rec = 0;
    public int min_rec = 0;
    public String name_rec[] = new String[20];  //  @jve:decl-index=0:

    /**
     * This is the default constructor
     */
    public Recordframe(Frame owner) {
        super(owner);
        initialize();
        instance = this;
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(171, 235);
        this.load_record();
        this.setModal(true);
        this.setContentPane(getJContentPane());
        this.setTitle("Records");
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
            jContentPane.add(getJList(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    /**
     * This method initializes jList
     *
     * @return javax.swing.JList
     */
    private JList getJList() {
        if (jList == null) {
            jList = new JList(name_rec);
        }
        return jList;
    }

    public void load_record() {
        File file = new File("record");
        String encoding = "Cp1251";
        StringBuffer sb = new StringBuffer();
        try {
            InputStreamReader f = (encoding == null) ? new FileReader(file) : new InputStreamReader(
                    new FileInputStream(file), encoding);
            try {
                char[] buf = new char[32768];
                int len;
                while ((len = f.read(buf, 0, buf.length)) >= 0) {
                    sb.append(buf, 0, len);
                }
            } finally {
                try {
                    f.close();
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringTokenizer st = new StringTokenizer(sb.toString());
        int i = 0;
        while (st.hasMoreTokens() && i < 20) {
            name[i] = st.nextToken();
            rec[i] = Integer.parseInt(st.nextToken());
            i++;
        }

        sort_rec();
        min_rec = rec[19];
        max_rec = rec[0];
        for (int j = 0; j < 20; j++) {
            name_rec[j] = "" + name[j] + "     " + rec[j];
        }
    }

    public void sort_rec() {
        int j;
        int limit = 20;
        int st = -1;
        while (st < limit) {
            st++;
            limit--;
            boolean swapped = false;
            for (j = st; j < limit; j++) {
                if (rec[j] < rec[j + 1]) {
                    int T = rec[j];
                    rec[j] = rec[j + 1];
                    rec[j + 1] = T;

                    String Ts = name[j];
                    name[j] = name[j + 1];
                    name[j + 1] = Ts;

                    swapped = true;
                }
            }
            if (!swapped) {
                return;
            }
            swapped = false;
            for (j = limit; --j >= st;) {
                if (rec[j] < rec[j + 1]) {
                    int T = rec[j];
                    rec[j] = rec[j + 1];
                    rec[j + 1] = T;

                    String Ts = name[j];
                    name[j] = name[j + 1];
                    name[j + 1] = Ts;

                    swapped = true;
                }
            }
            if (!swapped) {
                return;
            }
        }
    }

    public void add_rec(String nname, int nrec) {
        if (nname.trim().isEmpty()) {
            nname = "noname";
        }
        name[19] = nname;
        rec[19] = nrec;
        sort_rec();
        min_rec = rec[19];
        max_rec = rec[0];
        Mainframe.getInstance().set_record();
        for (int j = 0; j < 20; j++) {
            name_rec[j] = "" + name[j] + "     " + rec[j];
        }
        jList = new JList(name_rec);
        save_record();
    }

    public void save_record() {
        File file = new File("record");
        String encoding = "Cp1251";
        try {
            OutputStreamWriter f = (encoding == null) ? new FileWriter(file) : new OutputStreamWriter(
                    new FileOutputStream(file), encoding);
            String s = "";
            for (int i = 0; i < 20; i++) {
                s = s + name[i] + " " + rec[i] + "\n";
            }
            f.write(s);
            f.close();
        } catch (IOException e) {
        }

    }
}
