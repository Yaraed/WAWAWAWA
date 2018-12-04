package com.weyee.sdk.toast;

/**
 * <p>
 * 默认样式接口
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/4 0004
 */
public interface IToastStyle {
    /**
     * 吐司的中心
     *
     * @return
     */
    int getGravity();

    /**
     * X轴偏移
     *
     * @return
     */
    int getXOffset();

    /**
     * Y轴偏移
     *
     * @return
     */
    int getYOffset();

    /**
     * 吐司Z轴坐标
     *
     * @return
     */
    int getZ();

    /**
     * 圆角大小
     *
     * @return
     */
    int getCornerRadius();

    /**
     * 背景颜色
     *
     * @return
     */
    int getBackgroundColor();

    /**
     * 文本颜色
     *
     * @return
     */
    int getTextColor();

    /**
     * 文本大小
     *
     * @return
     */
    float getTextSize();

    /**
     * 最大行数
     *
     * @return
     */
    int getMaxLines();

    /**
     * 左边内边距
     *
     * @return
     */
    int getPaddingLeft();

    /**
     * 顶部内边距
     *
     * @return
     */
    int getPaddingTop();

    /**
     * 右边内边距
     *
     * @return
     */
    int getPaddingRight();

    /**
     * 底部内边距
     *
     * @return
     */
    int getPaddingBottom();
}
