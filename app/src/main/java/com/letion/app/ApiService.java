package com.letion.app;

import com.letion.app.pojo.Top250Bean;
import com.letion.app.pojo.BookBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/11 0011
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("v2/book/1220562")
    Observable<BookBean> getBook(@FieldMap Map<String, Object> maps);

    @GET("v2/movie/top250")
    Observable<Top250Bean> getTop250(@Query("count") int count);

    @GET("v2/book/1220562")
    Observable<String> getBookString();

    /**
     * 上传多个文件
     *
     * @param uploadUrl 地址
     * @param files     文件
     * @return ResponseBody
     */
    @Multipart
    @POST
    Observable<String> uploadFiles(@Url String uploadUrl, @Part List<MultipartBody.Part> files);

    /**
     * 大文件官方建议用 @Streaming 来进行注解，不然会出现IO异常，小文件可以忽略不注入
     *
     * @param fileUrl 地址
     * @return ResponseBody
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
