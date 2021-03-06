package com.bigkoo.pickerview.configure;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;

import com.bigkoo.pickerview.listener.OnClearSelectDataListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.contrarywind.view.WheelView;

import java.util.Calendar;

/**
 * 配置类
 * Created by xiaosongzeem on 2018/3/8.
 */

public class PickerOptions {

    //常量
    private static final int PICKER_VIEW_BTN_COLOR_NORMAL = 0xFF108EE9;
    private static final int PICKER_VIEW_BTN_COLOR_BOTTOM = 0xFFFFFFFF;
    private static final int PICKER_VIEW_BG_COLOR_TITLE = 0xFFFAFAFA;
    private static final int PICKER_VIEW_COLOR_TITLE = 0xFF000000;
    private static final int PICKER_VIEW_BG_COLOR_DEFAULT = 0xFFFFFFFF;

    public static final int TYPE_PICKER_OPTIONS = 1;
    public static final int TYPE_PICKER_TIME = 2;


    public OnOptionsSelectListener optionsSelectListener;
    public OnTimeSelectListener timeSelectListener;

    public OnTimeSelectChangeListener timeSelectChangeListener;
    public OnOptionsSelectChangeListener optionsSelectChangeListener;
    public OnClearSelectDataListener onClearSelectDataListener;
    public CustomListener customListener;
    public boolean clickSureButtonShow =false;//点击确认按钮之后，取消显示时间选择对话框吗？


    //options picker
    public String label1, label2, label3,label4;//单位字符
    public int option1, option2, option3,option4;//默认选中项
    public int x_offset_one, x_offset_two, x_offset_three,x_offset_four;//x轴偏移量

    public boolean cyclic1 = false;//是否循环，默认否
    public boolean cyclic2 = false;
    public boolean cyclic3 = false;
    public boolean cyclic4 = false;

    public boolean linkage = true;
    public boolean isRestoreItem = false; //切换时，还原第一项


    //time picker
    public boolean[] type = new boolean[]{true, true, true, false, false, false};//显示类型，默认显示： 年月日

    public Calendar date=Calendar.getInstance();//当前选中时间
    public Calendar startDate;//开始时间
    public Calendar endDate;//终止时间
    public int startYear;//开始年份
    public int endYear;//结尾年份

    public boolean cyclic = false;//是否循环
    public boolean isLunarCalendar = false;//是否显示农历

    public String label_year, label_month, label_day, label_hours, label_minutes, label_seconds;//单位
    public int x_offset_year, x_offset_month, x_offset_day, x_offset_hours, x_offset_minutes, x_offset_seconds;//单位


    public PickerOptions(int buildType) {
        if (buildType == TYPE_PICKER_OPTIONS) {
            layoutRes = R.layout.pickerview_options;
        } else {
            layoutRes = R.layout.pickerview_time;
        }
    }

    //******* 公有字段  ******//
    public int layoutRes;
    public ViewGroup decorView;
    public int textGravity = Gravity.CENTER;
    public Context context;

    public String textContentclear;//清除按钮文字
    public String textContentCancel;//取消按钮文字
    public String textContentBottom;//底部按钮文字
    public String textContentTitle;//标题文字

    public int textColorClear = PICKER_VIEW_BTN_COLOR_NORMAL;//清除按钮颜色
    public int textColorBottom = PICKER_VIEW_BTN_COLOR_BOTTOM;//清除按钮颜色
    public int textColorTitle = PICKER_VIEW_COLOR_TITLE;//标题颜色

    public int bgColorWheel = PICKER_VIEW_BG_COLOR_DEFAULT;//滚轮背景颜色
    public int bgColorTitle = PICKER_VIEW_BG_COLOR_TITLE;//标题背景颜色

    public int textSizeClear = 17;//清除按钮大小
    public int textSizeTitle = 18;//标题文字大小
    public int textSizeBottom = 18;//清除按钮大小
    public int textSizeContent = 18;//内容文字大小

    public int textColorOut = 0xFFa8a8a8; //分割线以外的文字颜色
    public int textColorCenter = 0xFF2a2a2a; //分割线之间的文字颜色
    public int dividerColor = 0xFFd5d5d5; //分割线的颜色
    public int backgroundId = -1; //显示时的外部背景色颜色,默认是灰色

    public float lineSpacingMultiplier = 2f; // 条目间距倍数 默认1.6
    public boolean isDialog=true;//是否是对话框模式

    public boolean cancelable = true;//是否能取消
    public boolean isCenterLabel = false;//是否只显示中间的label,默认每个item都显示
    public Typeface font = Typeface.MONOSPACE;//字体样式
    public WheelView.DividerType dividerType = WheelView.DividerType.FILL;//分隔线类型
    public int gravity=Gravity.BOTTOM;//显示位置，默认显示在屏幕底部


}
