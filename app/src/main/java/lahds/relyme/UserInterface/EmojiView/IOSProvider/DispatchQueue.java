package lahds.relyme.UserInterface.EmojiView.IOSProvider;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.CountDownLatch;

class DispatchQueue extends Thread {

    private volatile Handler handler = null;
    private CountDownLatch syncLatch = new CountDownLatch(1);

    public DispatchQueue(final String threadName) {
        setName(threadName);
        start();
    }

    public void cancelRunnable(Runnable runnable) {
        try {
            syncLatch.await();
            handler.removeCallbacks(runnable);
        } catch (Exception ignore) {

        }
    }

    public void postRunnable(Runnable runnable) {
        postRunnable(runnable, 0);
    }

    public void postRunnable(Runnable runnable, long delay) {
        try {
            syncLatch.await();
        } catch (Exception ignore) {

        }
        if (delay <= 0) {
            handler.post(runnable);
        } else {
            handler.postDelayed(runnable, delay);
        }
    }

    public void cleanupQueue() {
        try {
            syncLatch.await();
            handler.removeCallbacksAndMessages(null);
        } catch (Exception ignore) {

        }
    }

    public void recycle() {
        handler.getLooper().quit();
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new Handler();
        syncLatch.countDown();
        Looper.loop();
    }
}
