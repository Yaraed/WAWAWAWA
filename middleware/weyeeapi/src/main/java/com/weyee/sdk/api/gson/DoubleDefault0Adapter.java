package com.weyee.sdk.api.gson;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * <p>定义为double类型,如果后台返回""或者null,则返回0.00
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if ("".equals(json.getAsString()) || "null".equals(json.getAsString())) {
                return 0.00;
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsDouble();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
