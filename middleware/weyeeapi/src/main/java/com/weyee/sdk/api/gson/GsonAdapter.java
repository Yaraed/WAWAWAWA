package com.weyee.sdk.api.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class GsonAdapter {
    public static Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter());

        try {
            Class builder = gsonBuilder.getClass();
            Field f;
            //通过反射得到构造器
            f = builder.getDeclaredField("instanceCreators");
            f.setAccessible(true);
            final Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBuilder);//得到此属性的值
            //注册反射对象的处理器
            gsonBuilder.registerTypeAdapterFactory(new ReflectiveTypeAdapterFactory(new ConstructorConstructor(val), FieldNamingPolicy.IDENTITY, Excluder.DEFAULT));
            //注册集合的处理器
            gsonBuilder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(val)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gsonBuilder.create();
    }
}
