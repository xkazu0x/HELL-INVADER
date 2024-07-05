package main;

import java.util.Timer;
import java.util.TimerTask;

public class TimerAlive {
    public String time;
    private int i = 0;
    private Timer timer;

    public void createTimer() {
        i = 0;
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                time = getTime(i);
                i++;
            }
        };
        timer.schedule(task, 0, 10);
    }

    public void destroyTimer() {
        timer.cancel();
        timer.purge();
    }

    public String getTime(int sec) {
        int minutes = 0;
        int remainderOfMinutes = 0;
        int seconds = 0;
        int mili = 0;

        if (sec >= 3600) // if we have an hour or more
        {
            minutes = sec / 3600;
            remainderOfMinutes = sec % 3600;        // could be more or less than a min

            if (remainderOfMinutes >= 60)   //check if remainder is more or equal to a min
            {
                seconds = remainderOfMinutes / 60;
                mili = remainderOfMinutes % 60;
            }
            else
            {                       // if it's less than a min
                mili = remainderOfMinutes;
            }
        }
        else if(sec >= 60) {

            seconds = sec/60;
            mili = sec%60;
        }
        else if(sec < 60) {

            seconds = 0;
            mili = sec;
        }

        String strMins;
        String strSecs;
        String strMili;

        if(mili < 10)
            strMili = "0" + mili;
        else
            strMili = Integer.toString(mili);

        if(seconds < 10)
            strSecs = "0" + seconds;
        else
            strSecs = Integer.toString(seconds);

        if(minutes < 10)
            strMins = "0" + minutes;
        else
            strMins = Integer.toString(minutes);

        String time =  strMins + ":" + strSecs + ":" + strMili;
        return time;
    }
}
