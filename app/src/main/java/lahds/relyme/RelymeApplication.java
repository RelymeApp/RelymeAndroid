package lahds.relyme;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import lahds.relyme.UserInterface.EmojiView.IOSProvider.IOSEmojiProvider;
import lahds.relyme.UserInterface.EmojiView.EmojiManager;
import lahds.relyme.Utilities.AndroidUtilities;

public class RelymeApplication extends Application {

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public static Context applicationContext;
    public static volatile Handler applicationHandler;

    @Override
    public void onCreate() {
        this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                intent.putExtra("error", getStackTrace(ex));

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 11111, intent, PendingIntent.FLAG_ONE_SHOT);


                AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(2);

                uncaughtExceptionHandler.uncaughtException(thread, ex);
            }
        });
        super.onCreate();
        EmojiManager.install(this,new IOSEmojiProvider(applicationContext));
        applicationContext = this;
        AndroidUtilities.fillStatusBarHeight(applicationContext);
        applicationHandler = new Handler(applicationContext.getMainLooper());
    }

    private String getStackTrace(Throwable th){
        final Writer result = new StringWriter();

        final PrintWriter printWriter = new PrintWriter(result);
        Throwable cause = th;

        while(cause != null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        final String stacktraceAsString = result.toString();
        printWriter.close();

        return stacktraceAsString;
    }

}