/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  imitations under the License.
 *
 */

package com.wuqi.a_http;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.weyee.sdk.multitype.Test;
import com.wuqi.a_http.test.LRUCache;
import com.wuqi.a_http.test.Name;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author wuqi by 2019/2/18.
 */
public class Main {
    @SuppressLint("DefaultLocale")
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException, ClassNotFoundException {
        loop:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println(String.format("i=>%d,j=>%d", i, j));
                if (j == 3) {
                    break loop;
                }
            }
        }

        Integer a = new Integer(123);
        Integer b = new Integer(123);
        System.out.println(a == b);

        Integer c = Integer.valueOf(127);
        Integer d = Integer.valueOf(127);
        System.out.println(c == d);

        Test.main(null);

        try {
            int x = 10 / 0;
        } catch (OutOfMemoryError | ActivityNotFoundException | ArithmeticException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        Field[] fields = com.wuqi.a_http.test.Test.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Name.class)) {
                Name name = field.getAnnotation(Name.class);
                System.out.println(name.value() + "=>" + name.name() + "=>" + name.desc());
            }
        }
        new CopyOnWriteArrayList<String>();

        FutureTask<String> futureTask = new FutureTask<>(() -> {
            Thread.sleep(1000);
            return "null";
        });

        new Thread(futureTask).start();
        System.out.println("执行完futureTask之前");

        System.out.println(futureTask.get());

        System.out.println("执行完futureTask之后");

        //io("/Users/wuqi/workspace/doc/me/docs/RESUME.md");
        //serializable();
        deserialize();

        // lru 简单实现
        LRUCache lruCache = new LRUCache();
        lruCache.put(1L, "Apple");
        lruCache.put(2L, "Google");
        lruCache.put(3L, "Amazon");

        System.out.println(lruCache);

        lruCache.get(1L);

        lruCache.put(4L, "MicroSoft");

        System.out.println(lruCache);


        //LooperThread thread = new LooperThread();
        //thread.start();

//        new Thread(()->{
//            while (true){
//                if (thread.mHandler != null){
//                    thread.mHandler.sendEmptyMessage(1);
//                }
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        //thread.mHandler.sendEmptyMessage(1);

        List<String> list4 = new ArrayList<>();
        list4.add("Apple");
        list4.add("Google");
        list4.add("MicroSoft");
        list4.add("Amazon");

        for (Iterator<String> it = list4.iterator();it.hasNext();) {
            if (it.next().equals("Amazon")){
                it.remove();
            }
        }

        System.out.println(list4);

        com.wuqi.a_http.test.Test test = new com.wuqi.a_http.test.Test();

        FutureTask<String> futureTask1 = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(System.currentTimeMillis());
                synchronized (test){
                    test.wait();
                }
                System.out.println(System.currentTimeMillis());
                return test.toString();
            }
        });

        new Thread(futureTask1).start();



        int i = 0;

        do {
            Thread.sleep(100);
            i++;
        } while (i <= 10);
        synchronized (test){
            test.notifyAll();
        }

        //futureTask1.cancel(true);

        try {
            System.out.println(futureTask1.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private static void io(String src) throws IOException {
        FileReader fileReader = new FileReader(src);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        bufferedReader.close();
    }

    private static void serializable() throws IOException {
        com.wuqi.a_http.test.Test test = new com.wuqi.a_http.test.Test();

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/Users/wuqi/workspace/AndroidStudioProjects/me/Seven/a_http/src/main/java/com/wuqi/a_http/test/class_main"));
        out.writeObject(test);

    }

    private static void deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("/Users/wuqi/workspace/AndroidStudioProjects/me/Seven/a_http/src/main/java/com/wuqi/a_http/test/class_main"));
        com.wuqi.a_http.test.Test test = (com.wuqi.a_http.test.Test) in.readObject();
        if (test != null && test.getClass().isAnnotationPresent(Name.class)) {
            Name name = test.getClass().getAnnotation(Name.class);
            if (name != null)
                System.out.println(name.name() + name.desc());
        }

        if (test != null) {
            System.out.println(test.toString());
        } else {
            System.out.println("反序列化的数据为空");
        }
    }

    static class LooperThread extends Thread {
        public Handler mHandler;

        private static volatile LooperThread mInstance;

        public void run() {
            Looper.prepare();

            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    System.out.println(msg);
                }
            };

            Looper.loop();

        }

        public static LooperThread getmInstance(){
            if (mInstance == null){
                synchronized (LooperThread.class){
                    if (mInstance == null){
                        mInstance = new LooperThread();
                    }
                }
            }
            return mInstance;
        }
    }
}
