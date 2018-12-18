package com.weyee.sdk.api.core;

import com.weyee.sdk.api.core.exception.ApiException;
import com.weyee.sdk.api.core.exception.ExceptionHandle;
import com.weyee.sdk.api.core.pojo.HttpResponse;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>返回内容是否正常（status == 1 判断）。</p>
 *
 * @author moguangjian
 * @date 2018/6/21
 */
public class ResponseTransformer {


    /**
     * 统一线程处理，compose简化线程，支持背压
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理, 支持背压
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<HttpResponse<T>, T> handleFloableResult() {
        return new FlowableTransformer<HttpResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<HttpResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .flatMap(new FlowableFunction<T>())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<HttpResponse<T>, T> handleResult() {
        return new ObservableTransformer<HttpResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResponse<T>> observable) {

                return observable
                        //.onErrorResumeNext(new ErrorResumeFunction<T>())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new ResponseFunction<T>());
            }
        };
    }

    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends HttpResponse<T>>> {

        @Override
        public ObservableSource<? extends HttpResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(ExceptionHandle.handleException(throwable));
        }

    }

    /**
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<HttpResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(HttpResponse<T> tResponse) throws Exception {
            int code = tResponse.getStatus();
            String message = tResponse.getError().getErrormsg();
            if (code == 1) {
                return Observable.just(tResponse.getData());

            } else {
                String errorCode = tResponse.getError().getErrorno();
                return Observable.error(new ApiException(errorCode, message));
            }
        }
    }

    private static class FlowableResponseFunction<T> implements Function<HttpResponse<T>, Publisher<T>> {

        @Override
        public Publisher<T> apply(HttpResponse<T> tResponse) throws Exception {
            int code = tResponse.getStatus();
            String message = tResponse.getError().getErrormsg();
            if (code == 1) {
                return Flowable.just(tResponse.getData());

            } else {
                String errorCode = tResponse.getError().getErrorno();
                return Flowable.error(new ApiException(errorCode, message));
            }
        }
    }


    private static class FlowableFunction<T> implements Function<HttpResponse<T>, Flowable<T>> {

        @Override
        public Flowable<T> apply(HttpResponse<T> httpResponse) throws Exception {
            int code = httpResponse.getStatus();
            String msg = httpResponse.getError().getErrormsg();
            if (code == 1) {
                return Flowable.just(httpResponse.getData());
            } else {
                String errorCode = httpResponse.getError().getErrorno();
                return Flowable.error(new ApiException(errorCode, msg));
            }
        }
    }
}

