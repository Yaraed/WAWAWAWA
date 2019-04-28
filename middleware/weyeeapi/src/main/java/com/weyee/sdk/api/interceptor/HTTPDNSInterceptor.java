package com.weyee.sdk.api.interceptor;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import okhttp3.Dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * OKHttp 请求：将域名转换成IP进行请求，而默认的DNS解析器可能会
 * 1. 不稳定
 * DNS 劫持或者故障，导致服务不可用。
 * 2. 不准确
 * LocalDNS 调度，并不一定是就近原则，某些小运营商没有 DNS 服务器，直接调用其他运营商的 DNS 服务器，最终直接跨网传输。例如：用户侧是移动运营商，调度到了电信的 IP，造成访问慢，甚至访问受限等问题。
 * 3. 不及时
 * 运营商可能会修改 DNS 的 TTL(Time-To-Live，DNS 缓存时间)，导致 DNS 的修改，延迟生效。
 * <p>
 * DNS 拦截器，替代OkHttp 默认的DNS解析器
 *
 * @author wuqi by 2019/4/9.
 */
public class HTTPDNSInterceptor implements Dns {

    @Override
    public List<InetAddress> lookup(@NonNull String hostname) throws UnknownHostException {
        String ip = getIpByHost(hostname);
        if (!TextUtils.isEmpty(ip)) {
            //返回自己解析的地址列表
            return Arrays.asList(InetAddress.getAllByName(ip));
        } else {
            // 解析失败，使用系统解析
            return Dns.SYSTEM.lookup(hostname);
        }
    }

    private String getIpByHost(String hostname) {
        if ("api.douban.com".equals(hostname)){
            return "154.8.131.172";
        }else if("t.alipayobjects.com".equals(hostname)){
            return "182.140.246.235";
        }
        return null;
    }
}
