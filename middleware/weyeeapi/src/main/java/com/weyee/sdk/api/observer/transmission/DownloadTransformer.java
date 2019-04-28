package com.weyee.sdk.api.observer.transmission;

import androidx.annotation.NonNull;
import io.reactivex.ObservableTransformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>下载文件参数转换
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/12 0012
 */
public class DownloadTransformer {

    public static ObservableTransformer<okhttp3.ResponseBody, String> transformerFormParams(@NonNull File fileDir, @NonNull String fileName, ProgressListener progressListener) {
        return upstream -> upstream.map(responseBody -> {
            String destFileDir = fileDir + File.separator;

            long contentLength = responseBody.contentLength();
            InputStream is = null;
            byte[] buf = new byte[2048];
            int len;
            FileOutputStream fos = null;
            File file;
            try {
                is = responseBody.byteStream();

                long sum = 0;

                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                file = new File(dir, fileName);
                fos = new FileOutputStream(file);
                while ((len = is.read(buf)) != -1) {
                    sum += len;
                    fos.write(buf, 0, len);

                    final long finalSum = sum;

                    progressListener.onResponseProgress(finalSum, contentLength, (int) ((finalSum * 1.0f / contentLength) * 100), finalSum == contentLength, file.getAbsolutePath());
                }
                fos.flush();


            } finally {
                try {
                    responseBody.close();
                    if (is != null)
                        is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (fos != null)
                        fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return file == null ? null : file.getAbsolutePath();
        });
    }
}
