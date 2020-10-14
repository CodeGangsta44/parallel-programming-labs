package edu.kpi.ip71.dovhopoliuk.problem.task04.util;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockUtils {


    public static void signal(final ReentrantLock lock, final Condition condition) {

        try {

            condition.signal();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            lock.unlock();
        }

    }

    public static void signalWithLock(final ReentrantLock lock, final Condition condition) {

        lock.lock();

        try {

            condition.signal();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            lock.unlock();
        }

    }

    public static void wait(final ReentrantLock lock, final Condition condition) {

        try {

            condition.await();

        } catch (final InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            lock.unlock();
        }

    }

    public static void waitWithLock(final ReentrantLock lock, final Condition condition) {

        lock.lock();

        try {

            condition.await();

        } catch (final InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            lock.unlock();
        }

    }
}
