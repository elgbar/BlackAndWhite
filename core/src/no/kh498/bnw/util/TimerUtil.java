package no.kh498.bnw.util;

import com.badlogic.gdx.utils.Timer;

/**
 * Near clone of {@link Timer}, but added support to use lambda (Runnable) instead of {@link Timer.Task}
 *
 * @author karl henrik
 */
@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public class TimerUtil {

    private static final Timer timer = new Timer();
    private static final int FOREVER = -2;

    /** Schedules a task to occur once as soon as possible, but not sooner than the start of the next frame. */
    public static Timer.Task postTask(final Runnable runnable) {
        return scheduleTask(runnable, 0, 0, 0);
    }

    /** Schedules a task to occur once after the specified delay. */
    public static Timer.Task scheduleTask(final Runnable runnable, final float delaySeconds) {
        return scheduleTask(runnable, delaySeconds, 0, 0);
    }

    /**
     * Schedules a task to occur once after the specified delay and then repeatedly at the specified interval until
     * cancelled.
     */
    public static Timer.Task scheduleTask(final Runnable runnable, final float delaySeconds,
                                          final float intervalSeconds) {
        return scheduleTask(runnable, delaySeconds, intervalSeconds, FOREVER);
    }

    /**
     * Schedules a task to occur once after the specified delay and then a number of additional times at the specified
     * interval.
     */
    public static Timer.Task scheduleTask(final Runnable runnable, final float delaySecond, final float intervalSeconds,
                                          final int repeatCount) {
        final Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        return timer.scheduleTask(task, delaySecond, intervalSeconds, repeatCount);
    }
}
