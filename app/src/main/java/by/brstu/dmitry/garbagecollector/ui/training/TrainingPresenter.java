package by.brstu.dmitry.garbagecollector.ui.training;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants.DataType;
import by.brstu.dmitry.garbagecollector.application.DisposingObserver;
import by.brstu.dmitry.garbagecollector.application.TrainingData;
import by.brstu.dmitry.garbagecollector.model.trainingScreen.ITraining;
import by.brstu.dmitry.garbagecollector.pojo.TrainingPart;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;
import by.brstu.dmitry.garbagecollector.ui.compass.CompassCustomView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


@InjectViewState
public class TrainingPresenter extends BaseMvpPresenter<TrainingView>
        implements CompassCustomView.RotationCallback, ITraining.Actions {

    private static final short MAX_TIMES = 2;

    @Inject
    ITraining interactor;

    static short times = 0;
    private int left = 0;
    private int right = 0;
    private int time = 4;


    private final MyArrayBlockingQueue<TrainingPart> trainingQueue = new MyArrayBlockingQueue<>();

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    public TrainingPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        interactor.setActions(this);
    }

    @Override
    public void rotationStart() {

    }

    @Override
    public void rotationEnd(long l, float angle) {
        if (angle != 0) {
            long timeDiff = System.currentTimeMillis() - startTime.get();
            TrainingPart t = trainingQueue.findLastEmptyRealRotation();

            if (t == null) {
                t = new TrainingPart();
                t.setAngle(angle);
                t.setTimeOfRotationReal(l);
                t.setTimeOfRotationInPresenter(timeDiff);

                trainingQueue.offer(t);
            } else {
                t.setAngle(angle);
                t.setTimeOfRotationReal(l);
                t.setTimeOfRotationInPresenter(timeDiff);
            }

            if (++times < MAX_TIMES) {
                getViewState().updateTrainingProgress((short) ((times / (float) MAX_TIMES) * 100));
                sendRequest(right, left, time);
            } else {
                if (times == MAX_TIMES) {
                    getViewState().updateTrainingProgress((short) ((times / (float) MAX_TIMES) * 100));
                    Log.i("Stop", trainingQueue.toString());
                    evaluate();
                }

            }
        }
    }

    private void evaluate() {
        TrainingData.setRaw(trainingQueue.toArray());
        TrainingData.process();
        getViewState().showLogData();
    }


    public void setListener(CompassCustomView compassView) {
        compassView.setRotationCallback(this);
    }

    public void startTraining() {
        while (trainingQueue.size() != 4) {
            trainingQueue.add(new TrainingPart());
        }

        sendRequest(255, 0, time);
    }

    private void sendRequest(int left, int right, int time) {
        startTime.set(System.currentTimeMillis());
        this.left = left;
        this.right = right;
        this.time++;
        interactor.moveRobot(left, right, time);
    }

    public void clean() {
        times = 0;
    }

    @Override
    public void onSuccess(Observable<ResponseBody> observable, @DataType int a) {
        if(a == DataType.MOVE_WHEELS) {
            observable.doOnError(err -> Log.e("Studying", "error in studying : "))
                    .retryWhen(o -> o.delay(100, TimeUnit.MILLISECONDS))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposingObserver<ResponseBody>() {
                        @Override
                        public void onSubscribe(final Disposable d) {
                            //addRapid(d);
                            //robotMovingDisposable = d;
                        }

                        @Override
                        public void onNext(ResponseBody next) {
                            long timeDiff = System.currentTimeMillis() - startTime.get();
                            TrainingPart t = trainingQueue.findLastEmptySent();
                            if (t != null) {
                                t.setLeftWheelSpeed(left);
                                t.setRightWheelSpeed(right);
                                t.setTimeOfRotationSent(time * 100);
                                t.setTimeOfResponse(timeDiff);
                            } else {
                                t = new TrainingPart();
                                t.setLeftWheelSpeed(left);
                                t.setRightWheelSpeed(right);
                                t.setTimeOfRotationSent(time * 100);
                                t.setTimeOfResponse(timeDiff);
                                trainingQueue.offer(t);
                            }
                        }
                    });
        }
    }

    @Override
    public void onError() {
    }

    class MyArrayBlockingQueue<T> extends LinkedBlockingQueue<T> {

        MyArrayBlockingQueue() {
            super();
        }

        MyArrayBlockingQueue(int capacity) {
            super(capacity);
        }

        public T findLastEmptyRealRotation() {
            for (T t : this) {
                if (((TrainingPart) t).getTimeOfRotationReal() == 0) {
                    return t;
                }
            }

            return null;
        }

        public T findLastEmptySent() {
            for (T t : this) {
                if (((TrainingPart) t).getTimeOfRotationSent() == 0) {
                    return t;
                }
            }

            return null;
        }

        public void set(T item, int index) {
            synchronized (this) {
                final MyArrayBlockingQueue<T> subQueue = new MyArrayBlockingQueue<>(10);
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
