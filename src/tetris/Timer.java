package tetris;

public class Timer extends Thread {

    public boolean stop = false;
    public boolean pause = false;
    public int tickCount = 0;
    private int period;

    public Timer(int periodMS){
        super();
        period = periodMS;
    }
    
    public void run() {
        try {
            while (!stop) {
                sleep(period);
                if (!pause) {
                    tickCount++;
                    Mainframe.getInstance().setTime(tickCount);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
