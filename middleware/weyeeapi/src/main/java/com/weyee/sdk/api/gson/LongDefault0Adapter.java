package com.weyee.sdk.api.gson;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * <p>定义为long类型,如果后台返回""或者null,则返回0
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
class LongDefault0Adapter implements JsonSerializer<Long>, JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            if ("".equals(json.getAsString()) || "null".equals(json.getAsString())) {
                return 0L;
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsLong();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
