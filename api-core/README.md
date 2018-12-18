# api-core说明
需要搭配业务类API使用，具体请去maven查看相关业务类api。


## apic-core 使用


### 引入gradle
```
compile 'com.weyee.sdk:api-core:1.0' // 接口核心库
```


### 初始化SDK
```
 // 添加配置
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setHost("http://openapi.yiminct.com/");
        // 设置终端应用加密拦截器
        apiConfig.setInterceptor(new MPosEncryptInterceptor());
        // 设置统一错误提示
        apiConfig.setErrorHintAble(new ErrorHintAble() {
            @Override
            public void onError(int code, String msg, Exception exception) {
                // api 业务特殊状态码处理
                if (code == ApiErrorCode.LOW_VERSION) {

                } else if (code == ApiErrorCode.SERVICE_EXPIRE) {

                } else if (code == ApiErrorCode.ACCOUNT_ERROR) {

                } else if (code == ApiErrorCode.TIME_ERROR) {

                }
            }

            @Override
            public boolean isShowDefaultHint() {
                return true;
            }
        });

        ApiFactory.init(getApplicationContext(), apiConfig);
```


### 接口调用
```
 ApiFactory.create(StockAPI.class)
                .getServerTime()
                .compose(ResponseTransformer.<Long>handleResult())
                .subscribe(new BaseObserver<Long>(this) {
                    @Override
                    public void onSuccess(Long aLong) {
                        tvResult.setText("接口请求结果：" + aLong);
                    }
                });
```


## 支持
Android 4.0+
<br/>


## 混淆
```
# -keep com.weyee.sdk.api.core.pojo.** {*;}

```
<br/>

