package application.LAPR3.utils;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class DbConnectionTimeTask {
    private final Timer timer;
    private final CallableStatement callStmt;
    private final int[] result;

    public DbConnectionTimeTask(CallableStatement callStmt, int[] result) {
        this.callStmt = callStmt;
        this.result = result;
        this.timer = new Timer(true);
    }

    public void scheduleTimeout(long timeoutMillis) {
        timer.schedule(new TimeoutTask(), timeoutMillis);
    }

    public void cancelTask() {
        timer.cancel();
    }

    private class TimeoutTask extends TimerTask {
        @Override
        public void run() {
            if (callStmt != null) {
                try {
                    callStmt.cancel();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            result[0] = 2; // Timeout
        }
    }
}
