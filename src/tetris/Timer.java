package tetris;

public class Timer extends Thread {

    public boolean stop = false;
    public boolean pause = false;
    public int tickCount = 0;
    public int period;
    private Periodic periodicObj;
    
    public Timer(Periodic periodicObj, int periodMS){
        super();
        period = periodMS;
        this.periodicObj = periodicObj;
    }
    
    public void run() {
        try {
            while (!stop) {
                sleep(period);
                if (!pause) {
                    tickCount++;
                    periodicObj.doSomeThing();
                }
            }
        } catch (InterruptedException e) {
        }
    }
}
