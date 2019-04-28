package com.weyee.sdk.api.base;

import android.annotation.SuppressLint;
import androidx.core.util.Preconditions;
import com.weyee.sdk.api.gson.GsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author wuqi by 2019/4/28.
 */
final class SimpleRetrofitClient implements BaseRetrofitClient<SimpleRetrofitClient> {

    private String baseUrl;//api base url 请求地址
    private Retrofit retrofit;
    private OkHttpClient client;

    @Override
    public SimpleRetrofitClient baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    @Override
    public SimpleRetrofitClient client(OkHttpClient okClient) {
        this.client = okClient;
        return this;
    }

    /**
     * 注意：baseurl/client必须调用
     *
     * @return this
     */
    @SuppressLint("RestrictedApi")
    @Override
    public SimpleRetrofitClient build() {
        Preconditions.checkNotNull(baseUrl, String.format("%s cannot be null", HttpUrl.class.getName()));
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonAdapter.buildGson()));
        retrofit = retrofitBuilder.client(client == null ? defaultOkHttpClient() : client).build();
        return this;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public <T> T create(Class<T> service) {
        Preconditions.checkNotNull(retrofit, String.format("%s cannot be null", Retrofit.class.getName()));
        return retrofit.create(service);
    }
}
