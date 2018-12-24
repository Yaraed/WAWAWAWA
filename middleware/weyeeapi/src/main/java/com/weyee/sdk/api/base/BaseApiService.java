package com.weyee.sdk.api.base;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.*;

import java.util.Map;

/**
 * <p>示例
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/10 0010
 */
public interface BaseApiService {
    @GET()
    Flowable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> maps);

    @FormUrlEncoded
    @POST()
    Flowable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> maps);

    @POST()
    Flowable<ResponseBody> json(@Url String url, @Body RequestBody jsonStr);

    @Multipart
    @POST()
    Flowable<ResponseBody> upLoadFile(@Url String url, @Part() RequestBody requestBody);

    @POST()
    Flowable<ResponseBody> uploadFiles(@Url String url, @Path("headers") Map<String, String> headers, @Part("filename") String description, @PartMap Map<String, RequestBody> maps);

    @Streaming
    @GET
    Flowable<ResponseBody> downloadFile(@Url String fileUrl);
}
