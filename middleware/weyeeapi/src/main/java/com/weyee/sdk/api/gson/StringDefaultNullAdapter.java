package com.weyee.sdk.api.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * <p>定义为String类型,如果后台返回其他类型，统一处理为null
 *
 * @author wuqi
 * @describe ...
 * @date 2019/1/7 0007
 */
final class StringDefaultNullAdapter extends TypeAdapter<String> {
    @Override
    public void write(JsonWriter jsonWriter, String s) throws IOException {
        jsonWriter.value(s);
    }

    @Override
    public String read(JsonReader jsonReader) throws IOException {
        JsonToken peek = jsonReader.peek();
        if (peek == JsonToken.NULL) {
            jsonReader.nextNull();
            return "";
        }
        if (peek == JsonToken.BOOLEAN) {
            return Boolean.toString(jsonReader.nextBoolean());
        }
        if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
            Utils.readObject(jsonReader);
            return "";
        }
        //增加判断是错误的name的类型（应该是object）,移动in的下标到结束，移动下标的代码在下方
        if (jsonReader.peek() == JsonToken.NAME) {
            jsonReader.nextName();
            return "";
        }
        //增加判断是错误的ARRAY的类型（应该是object）,移动in的下标到结束，移动下标的代码在下方
        if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
            Utils.readArray(jsonReader);
            return "";
        }

        return jsonReader.nextString();
    }
}
