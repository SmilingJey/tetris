package tetris;

public class Timer extends Thread {
    private boolean pause = false;
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
    
    public void pauseTimer(boolean pause){
        this.pause = pause;
    }
    
    public boolean isPause(){
        return pause;
    }

    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()){
                if (!pause) periodicObj.doSomeThing();
                Thread.sleep(period);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
