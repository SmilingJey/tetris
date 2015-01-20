package tetris;

public class Timer extends Thread {
    private int period;
    private final Periodic periodicObj;
    
    public Timer(Periodic periodicObj, int periodMS){
        super();
        period = periodMS;
        this.periodicObj = periodicObj;
    }
    
    public void setPeriod(int period){
        this.period = period;
    }

    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()){
                periodicObj.doSomeThing();
                Thread.sleep(period);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
