package com.bcq.refresh;

import com.bcq.refresh.progress.Style;

/**
 * 以下属性需自定义是，覆盖即可
 * header的默认高度：70dp
 * <dimen name="re_header_height">70dp</dimen>
 * 指示器的默认大小：25dp
 * <dimen name="re_progress_size">25dp</dimen>
 * 默认文字size，大：14sp：小：12sp
 * <dimen name="re_text_size">14sp</dimen>
 * <dimen name="re_text_size_smal">12sp</dimen>
 * 默认边距：10dp
 * <dimen name="re_textandiconmargin">10dp</dimen>
 * 指示器和文字默认颜色
 * <color name="re_color_default">#FF02479C</color>
 * header和footer的默认颜色：透明
 * <color name="re_color_bg">@android:color/transparent</color>
 */
public class RefreshHelper {
    private static Style defaultStyle;

    /**
     * 设置默认刷新动画的style
     *
     * @param defaultStyle
     */
    public static void setDefaultStyle(Style defaultStyle) {
        RefreshHelper.defaultStyle = defaultStyle;
    }

    public static Style getStyle() {
        if (null == defaultStyle) {
            defaultStyle = Style.BallSpinFadeLoader;
        }
        return defaultStyle;
    }
}
