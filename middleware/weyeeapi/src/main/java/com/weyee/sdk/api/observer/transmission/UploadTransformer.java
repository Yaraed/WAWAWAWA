package com.weyee.sdk.api.observer.transmission;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>上传文件参数转换
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/12 0012
 */
public class UploadTransformer {
    public static List<MultipartBody.Part> transformerFormParams(Map<String, String> paramsMap, List<String> filePaths) {
        return transformerFormParams("upload_files", paramsMap, filePaths);
    }

    public static List<MultipartBody.Part> transformerFormParams(String fileName, Map<String, String> paramsMap, List<String> filePaths) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (null != paramsMap) {
            for (String key : paramsMap.keySet()) {
                if (paramsMap.get(key) != null) {
                    builder.addFormDataPart(key, Objects.requireNonNull(paramsMap.get(key)));
                }
            }
        }

        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            //"fileName"+i 后台接收图片流的参数名
            builder.addFormDataPart(fileName, file.getName(), imageBody);
        }

        return builder.build().parts();
    }
}
