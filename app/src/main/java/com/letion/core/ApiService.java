package com.letion.core;

import com.letion.core.pojo.BookBean;
import com.letion.core.pojo.Top250Bean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/11 0011
 */
public interface ApiService {
    @GET("v2/book/1220562")
    Observable<BookBean> getBook();

    @GET("v2/movie/top250")
    Observable<Top250Bean> getTop250(@Query("count") int count);

    @GET("v2/book/1220562")
    Observable<String> getBookString();
}
