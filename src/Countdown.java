import java.util.Timer;
import java.util.TimerTask;

public class Countdown {

    int timer;

    public void counter (int countdown){
        this.timer =countdown;
        Timer TimerA = new Timer();
        TimerTask TaskA = new TimerTask() {
            @Override
            public void run() {
                if (timer >= 0){

                    timer--;
                }
                if (timer == -1){


                    TimerA.cancel();


                }
            }
        }    ;
        TimerA.schedule(TaskA,0,1000); // 1000 millisec -alltså en secund


    }

    public int remainingTime(){
        this.timer = timer;

return timer;

    }

public boolean timesLeft(){

        if (timer==-1){
            return false;

        }
        return true;

}

}
