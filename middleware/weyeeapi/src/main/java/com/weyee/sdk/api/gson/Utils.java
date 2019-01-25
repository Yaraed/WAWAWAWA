package com.weyee.sdk.api.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2019/1/7 0007
 */
final class Utils {
    /**
     * 解析jsonArray
     *
     * @param in json数据
     *
     * @throws IOException
     */
    static void readArray(JsonReader in)
            throws IOException {
        in.beginArray();
        readJson(in);
        in.endArray();
    }

    /**
     * 解析jsonObject
     *
     * @param in json数据
     *
     * @throws IOException
     */
    static void readObject(JsonReader in)
            throws IOException {
        in.beginObject();
        readJson(in);
        in.endObject();
    }

    /**
     * 解析整个json数据
     *
     * @param in json数据
     *
     * @throws IOException
     */
    private static void readJson(JsonReader in)
            throws IOException {
        while (in.hasNext()) {
            if (in.peek() == JsonToken.BEGIN_ARRAY) {
                readArray(in);
            } else if (in.peek() == JsonToken.NUMBER) {
                in.nextDouble();
            } else if (in.peek() == JsonToken.STRING) {
                in.nextString();
            } else if (in.peek() == JsonToken.NULL) {
                in.nextNull();
            } else if (in.peek() == JsonToken.NAME) {
                in.nextName();
            } else if (in.peek() == JsonToken.BOOLEAN) {
                in.nextBoolean();
            } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                readObject(in);
            }
        }
    }
}
