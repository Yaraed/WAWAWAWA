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

package com.wuqi.a_http

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

/**
 *
 * @author wuqi by 2019/2/18.
 */

fun main(args: Array<String>?) {
    // 类Java写法
    if (args != null && !args.isEmpty()) {
        println("$args")
    } else {

    }
    // 另一种写法
    println(args?.isNotEmpty() ?: "参数为空")

    val list = listOf("Apple", "Google", "MicroSoft", "Amazon")

    // for each
    for (item in list) {
        println(item)
    }

    // for i
    for (index in list.indices) {
        println(list[index])
    }

    // while
    var index = 0
    while (index < list.size) {
        println(list[index])
        index++
    }

    // Range
    if (index in 1..100) {
        println(
            "1<$index<100"
        )
    }

    // 值范围遍历
    for (i in 0..100 step 2) {
        print("$i ")
    }
    println()
    for (i in 0 until 100 step 2) {
        print("$i ")
    }
    println()
    for (i in 50 downTo 0 step 3) {
        print("$i\t")
    }
    println()

    // when
    when {
        "Apple" !in list -> println("存在")
        "Amazon" in list -> println("不存在")
    }

    // list 操作符 filter
    val filterList = list.filter { it.length >= 6 }
    println(
        filterList
    )

    // 延时计算字段属性值
    val p: String?  by lazy {
        null
    }

    try {
        p?.let {
            println(it.spaceToCamelCase())
        } ?: throw RuntimeException("参数为空")
    } catch (e: RuntimeException) {
        println(e.message)
    }

    // 返回和跳转
    loop@ for (i in 0 until 5) {
        for (j in 0 until 5) {
            if (j == 3) {
                break@loop
            }
            println("$i=>$j")
        }
    }

    val user = User("zhangsan","123456")
    user.name = "lisi"

    println(user.name)

    val (name,pwd) = user

    println("$name,$pwd")

    val map  = HashMap<Long,User>()
    map[1L] = user
    map.map { println(it) }


    list.forEach { it ->
        run {
            println(it)
        }
    }

    thread {
        Thread.sleep(2000)
        println("world")
    }
    print("hello ")
    Thread.sleep(1000) // 时间长，必须保证JVM还在运行

    runBlocking {
        val job = launch {
            delay(1000)
            println("runBlock")
        }
        delay(500)
        job.cancel()
        job.join()
        println("main: Now I can quit.")
    }

}

// 扩展函数
fun String.spaceToCamelCase(): String {
    return this.toUpperCase()
}
