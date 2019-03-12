#### SharedPreferences
```
存在的问题：
    1. 线程安全，跨进程不安全
    2. 加载速度慢，未初始化完成时调用get/put方法会阻塞线程，直至初始化完成
    3. 全量写入，每次调用commit/apply方法时都会把整个文件的内容重新写入文件
    4. apply写文件异步执行，可能导致内容丢失，而commit是同步执行的，又可能阻塞线程
解决办法：
    1. SharedPreferences不要存储大文件数据，不存储json
    2. 使用[mmkv](https://github.com/Tencent/MMKV/blob/master/readme_cn.md)替代
```
