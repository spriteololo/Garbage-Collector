package by.brstu.dmitry.garbagecollector.model;

import android.util.Pair;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.Constants.DataType;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.IManualControl;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BaseInteractor {

    public BaseInteractor() {
        startPermanentThread();
    }

    @Inject
    RequestInterface requestInterface;

    private final MyQueue<Pair<Observable<ResponseBody>, BaseActionsInterface>> moving = new MyQueue<>();
    private final MyQueue<Pair<Observable<ResponseBody>, BaseActionsInterface>> lidQueue = new MyQueue<>(1);
    private final MyQueue<Pair<Observable<ResponseBody>, BaseActionsInterface>> frontInfraQueue = new MyQueue<>();
    private final MyQueue<Pair<Observable<ResponseBody>, BaseActionsInterface>> backInfraQueue = new MyQueue<>();
    private final MyQueue<Pair<Observable<ResponseBody>, BaseActionsInterface>> otherActions = new MyQueue<>();

    private boolean lidOpen = false;

    public void move(Observable<ResponseBody> observable, BaseActionsInterface actions) {

        try {
            moving.put(new Pair<>(observable, actions));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getFrontInfra(Observable<ResponseBody> observable, BaseActionsInterface actions) {

        try {
            frontInfraQueue.put(new Pair<>(observable, actions));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getBackInfra(Observable<ResponseBody> observable, BaseActionsInterface actions) {

        try {
            backInfraQueue.put(new Pair<>(observable, actions));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final Thread a = new Thread(() ->
            Observable.interval(150, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribe(a -> next())
    );

    void next() {
        if (!moving.isEmpty()) {
            Pair runnable = moving.poll();
            ((BaseActionsInterface) runnable.second).onSuccess((Observable<ResponseBody>) runnable.first, DataType.MOVE_WHEELS);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } else {
            if (!lidQueue.isEmpty()) {
                Pair runnable = lidQueue.poll();
                ((BaseActionsInterface) runnable.second).onSuccess((Observable<ResponseBody>) runnable.first, lidOpen ? DataType.LID_OPEN : DataType.LID_CLOSED);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                if (!frontInfraQueue.isEmpty()) {
                    Pair runnable = frontInfraQueue.poll();
                    ((BaseActionsInterface) runnable.second).onSuccess((Observable<ResponseBody>) runnable.first, DataType.FRONT_INFRA);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                } else {
                    if (!backInfraQueue.isEmpty()) {
                        Pair runnable = backInfraQueue.poll();
                        ((BaseActionsInterface) runnable.second).onSuccess((Observable<ResponseBody>) runnable.first, DataType.BACK_INFRA);

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                    } else {
                        if (!otherActions.isEmpty()) {
                            Pair runnable = otherActions.poll();
                            ((BaseActionsInterface) runnable.second).onSuccess((Observable<ResponseBody>) runnable.first, DataType.BASE_DATA);

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }

                        } else {
                            try {
                                Thread.sleep(30);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
            }
        }
    }

    public void startPermanentThread() {
        a.start();
    }

    public void stopPermanentThread() {
        a.interrupt();
    }

    public void openLid(Observable<ResponseBody> observable, BaseActionsInterface actions) {
        try {
            lidQueue.clearAll();
            lidQueue.put(new Pair<>(observable, actions));
            lidOpen = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeLid(Observable<ResponseBody> observable, BaseActionsInterface actions) {
        try {
            lidQueue.clearAll();
            lidQueue.put(new Pair<>(observable, actions));
            lidOpen = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getBaseData(Observable<ResponseBody> observable, BaseActionsInterface actions) {
        try {
            otherActions.put(new Pair<>(observable, actions));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    class MyQueue<T> extends LinkedBlockingQueue<T> {

        MyQueue() {
            super();
        }

        MyQueue(int capacity) {
            super(capacity);
        }

        void clearAll() {
            for (int i = 0; i < this.size(); i++) {
                this.poll();
            }
        }

        public void set(T item, int index) {
            synchronized (this) {
                final MyQueue<T> subQueue = new MyQueue<>(10);
                for (int r = 0; r < this.size(); r++) {
                    if (r != index) {
                        subQueue.offer(this.poll());
                    } else {
                        r = this.size();
                    }
                }
                this.offer(item);
                for (int r = 0; r < subQueue.size(); r++) {
                    this.offer(subQueue.poll());
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder strb = new StringBuilder();
            strb.append("\n");
            for (T t : this) {
                strb.append(t.toString()).append('\n');
            }
            strb.append("\n=====================================END");
            return strb.toString();
        }
    }
}
