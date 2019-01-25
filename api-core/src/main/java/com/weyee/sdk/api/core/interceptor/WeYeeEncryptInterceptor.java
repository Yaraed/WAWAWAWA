package com.weyee.sdk.api.core.interceptor;

import android.os.Build;
import com.weyee.sdk.api.core.ApiConfig;
import com.weyee.sdk.api.core.util.RandomHelper;
import com.weyee.sdk.api.core.util.ShaHelper;
import com.weyee.sdk.log.LogUtils;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 接口加密器。
 *
 * @author LJJ
 * @date 2017/11/21
 */

public abstract class WeYeeEncryptInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String TAG = WeYeeEncryptInterceptor.class.getSimpleName();

    /**
     * 参数预处理。可以在这里添加全局接口参数。
     *
     * @param map
     */
    protected abstract void prepareParams(Map<String, Object> map);

    /**
     * appKey。
     */
    protected abstract String getAppKey();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = encrypt(request);
        return chain.proceed(request);
    }

    /**
     * 加密
     */
    private Request encrypt(Request request) {
        Headers paramsHeads = request.headers();
        HashMap<String, Object> params = new HashMap<>(1);

        for (int i = 0, count = paramsHeads.size(); i < count; i++) {
            String name = paramsHeads.name(i);
            params.put(name, paramsHeads.value(i));
        }

        RequestBody requestBody = request.body();
        //请求体
        if (requestBody instanceof FormBody) {
            FormBody formBody = (FormBody) requestBody;
            for (int i = 0; i < formBody.size(); i++) {
                params.put(formBody.encodedName(i), formBody.value(i));
            }
        }

        requestHttpPrepare(params);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                //图片 "*/image"
                if (value instanceof File) {
                    builder.addFormDataPart(entry.getKey(), ((File) value).getName(),
                            RequestBody.create(MediaType.parse("image/png"), (File) value));
                    continue;
                }
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
        }

        printlnUrl(request, params);
        return request.newBuilder().post(builder.build()).build();
    }

    private void printlnUrl(Request request, HashMap<String, Object> params) {
        if (!ApiConfig.isDebug) {
            return;
        }

        StringBuilder builderStr = new StringBuilder();
        builderStr.append(request.url());
        builderStr.append("?");

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                //图片 "*/image"
                if (value instanceof File) {
                    continue;
                }
                builderStr.append(entry.getKey());
                builderStr.append("=");
                builderStr.append(entry.getValue().toString());
                builderStr.append("&");
            }
        }

        LogUtils.i(builderStr.toString());
    }

    /**
     * 唯衣自定义加密
     */
    private void requestHttpPrepare(Map<String, Object> params) {
        prepareParams(params);
        params.put("os", "Android" + Build.VERSION.RELEASE);
        params.remove("signature");
        params.put("nonce", RandomHelper.getRandomString(32));
        params.put("signature", getSignature(getAppKey(), params));
    }

    private static String getSignature(String appKey, Map<String, Object> params) {
        // 拿key
        Set<Map.Entry<String, Object>> set = params.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = set.iterator();

        String[] keys = new String[params.size()];
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            keys[i] = entry.getKey();
            i++;
        }

        Arrays.sort(keys);
        // 拼values
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keys) {
            try {
                String value = "";
                if (null != params.get(key)) {
                    value = params.get(key).toString();
                    if (params.get(key) instanceof File) {
                        value = "";
                        continue;
                    }
                }
                stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.append(appKey);
        String replace = stringBuilder.toString().replaceAll("\\u002A", "%2A");

        String token = ShaHelper.encodeSha1(replace);
        return token;
    }


}
