package com.weyee.posres;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;
import com.weyee.posres.weight.QMUISwipeBackActivityManager;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.unit.UnitsManager;
import me.jessyan.autosize.utils.LogUtils;

import java.util.Locale;

/**
 * <p> //对单位的自定义配置, 请在 App 启动时完成
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class AutoSizeConfig {
    /**
     * 注意!!! 布局时的实时预览在开发阶段是一个很重要的环节, 很多情况下 Android Studio 提供的默认预览设备并不能完全展示我们的设计图
     * 所以我们就需要自己创建模拟设备, 以下链接是给大家的福利, 按照链接中的操作可以让预览效果和设计图完全一致!
     *
     * @see <a href="https://github.com/JessYanCoding/AndroidAutoSize/blob/master/README-zh.md#preview">dp、pt、in、mm 这四种单位的模拟设备创建方法</a>
     * <p>
     * v0.9.0 以后, AndroidAutoSize 强势升级, 将这个方案做到极致, 现在支持5种单位 (dp、sp、pt、in、mm)
     * {@link UnitsManager} 可以让使用者随意配置自己想使用的单位类型
     * 其中 dp、sp 这两个是比较常见的单位, 作为 AndroidAutoSize 的主单位, 默认被 AndroidAutoSize 支持
     * pt、in、mm 这三个是比较少见的单位, 只可以选择其中的一个, 作为 AndroidAutoSize 的副单位, 与 dp、sp 一起被 AndroidAutoSize 支持
     * 副单位是用于规避修改 {@link DisplayMetrics#density} 所造成的对于其他使用 dp 布局的系统控件或三方库控件的不良影响
     * 您选择什么单位, 就在 layout 文件中用什么单位布局
     * <p>
     * 两个主单位和一个副单位, 可以随时使用 {@link UnitsManager} 的方法关闭和重新开启对它们的支持
     * 如果您想完全规避修改 {@link DisplayMetrics#density} 所造成的对于其他使用 dp 布局的系统控件或三方库控件的不良影响
     * 那请调用 {@link UnitsManager#setSupportDP}、{@link UnitsManager#setSupportSP} 都设置为 {@code false}
     * 停止对两个主单位的支持 (如果开启 sp, 对其他三方库控件影响不大, 也可以不关闭对 sp 的支持)
     * 并调用 {@link UnitsManager#setSupportSubunits} 从三个冷门单位中选择一个作为副单位
     * 三个单位的效果都是一样的, 按自己的喜好选择, 比如我就喜欢 mm, 翻译为中文是妹妹的意思
     * 然后在 layout 文件中只使用这个副单位进行布局, 这样就可以完全规避修改 {@link DisplayMetrics#density} 所造成的不良影响
     * 因为 dp、sp 这两个单位在其他系统控件或三方库控件中都非常常见, 但三个冷门单位却非常少见
     */
    public static void init(Application application) {
        //AndroidAutoSize 默认开启对 dp 的支持, 调用 UnitsManager.setSupportDP(false); 可以关闭对 dp 的支持
        //主单位 dp 和 副单位可以同时开启的原因是, 对于旧项目中已经使用了 dp 进行布局的页面的兼容
        //让开发者的旧项目可以渐进式的从 dp 切换到副单位, 即新页面用副单位进行布局, 然后抽时间逐渐的将旧页面的布局单位从 dp 改为副单位
        //最后将 dp 全部改为副单位后, 再使用 UnitsManager.setSupportDP(false); 将 dp 的支持关闭, 彻底隔离修改 density 所造成的不良影响
        //如果项目完全使用副单位, 则可以直接以像素为单位填写 AndroidManifest 中需要填写的设计图尺寸, 不需再把像素转化为 dp
        me.jessyan.autosize.AutoSizeConfig.getInstance()

                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                //如果没有这个需求建议不开启
                .setCustomFragment(false)

                //屏幕适配监听器
                .setOnAdaptListener(new onAdaptListener() {
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
                        //AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
                        //AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
                    }

                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
                    }
                })

                //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
                //.setLog(false)


                .getUnitsManager()
                .setSupportDP(true)

        //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
        //AutoSize 会将屏幕总高度减去状态栏高度来做适配
        //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
        //.setUseDeviceSize(true)

        //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
        //.setBaseOnWidth(false)

        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
        //.setAutoAdaptStrategy(new AutoAdaptStrategy())

        //当使用者想将旧项目从主单位过渡到副单位, 或从副单位过渡到主单位时
        //因为在使用主单位时, 建议在 AndroidManifest 中填写设计图的 dp 尺寸, 比如 360 * 640
        //而副单位有一个特性是可以直接在 AndroidManifest 中填写设计图的 px 尺寸, 比如 1080 * 1920
        //但在 AndroidManifest 中却只能填写一套设计图尺寸, 并且已经填写了主单位的设计图尺寸
        //所以当项目中同时存在副单位和主单位, 并且副单位的设计图尺寸与主单位的设计图尺寸不同时, 可以通过 UnitsManager#setDesignSize() 方法配置
        //如果副单位的设计图尺寸与主单位的设计图尺寸相同, 则不需要调用 UnitsManager#setDesignSize(), 框架会自动使用 AndroidManifest 中填写的设计图尺寸
        //.setDesignSize(2160, 3840)

        //AndroidAutoSize 默认开启对 sp 的支持, 调用 UnitsManager.setSupportSP(false); 可以关闭对 sp 的支持
        //如果关闭对 sp 的支持, 在布局时就应该使用副单位填写字体的尺寸
        //如果开启 sp, 对其他三方库控件影响不大, 也可以不关闭对 sp 的支持, 这里我就继续开启 sp, 请自行斟酌自己的项目是否需要关闭对 sp 的支持
        // .setSupportSP(false)

        //AndroidAutoSize 默认不支持副单位, 调用 UnitsManager#setSupportSubunits() 可选择一个自己心仪的副单位, 并开启对副单位的支持
        //只能在 pt、in、mm 这三个冷门单位中选择一个作为副单位, 三个单位的适配效果其实都是一样的, 您觉的哪个单位看起顺眼就用哪个
        //您选择什么单位就在 layout 文件中用什么单位进行布局, 我选择用 mm 为单位进行布局, 因为 mm 翻译为中文是妹妹的意思
        //如果大家生活中没有妹妹, 那我们就让项目中最不缺的就是妹妹!
        //.setSupportSubunits(Subunits.MM)
        ;

        QMUISwipeBackActivityManager.init(application);
    }
}
