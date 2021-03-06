package com.bigkoo.pickerview.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.configure.PickerOptions;

import java.util.List;

/**
 * 条件选择器
 * Created by Sai on 15/11/22.
 */
public class OptionsPickerView<T> extends BasePickerView implements View.OnClickListener {

    private WheelOptions wheelOptions;

    private static final String TAG_CLEAR = "clear";
    private static final String TAG_CANCEL = "cancel";
    private static final String TAG_BOTTOM = "bottom";
    private LinearLayout progressBar;

    public OptionsPickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews(mPickerOptions.gravity);
        initAnim();
        initEvents();
        if (mPickerOptions.customListener == null) {
            LayoutInflater.from(context).inflate(mPickerOptions.layoutRes, contentContainer);

            //顶部标题
            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            RelativeLayout rv_top_bar = (RelativeLayout) findViewById(R.id.rv_topbar);

            //确定和取消按钮
            TextView btnClear = (TextView) findViewById(R.id.btnClear);
            ImageView btnCancel = (ImageView) findViewById(R.id.btnCancel);
            TextView btnBottom = (TextView) findViewById(R.id.btnBottom);

            btnClear.setTag(TAG_CLEAR);
            btnCancel.setTag(TAG_CANCEL);
            btnBottom.setTag(TAG_BOTTOM);

            btnClear.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            btnBottom.setOnClickListener(this);

            //设置文字
            btnClear.setText(mPickerOptions.textContentclear==null ? context.getResources().getString(R.string.view_pickerview_clear) : mPickerOptions.textContentclear);
            tvTitle.setText(TextUtils.isEmpty(mPickerOptions.textContentTitle) ?context.getResources().getString(R.string.view_area_picker_title): mPickerOptions.textContentTitle);//默认为空
            btnBottom.setText(TextUtils.isEmpty(mPickerOptions.textContentBottom) ? context.getResources().getString(R.string.view_pickerview_bottom) : mPickerOptions.textContentBottom);

            //设置color
            btnClear.setTextColor(mPickerOptions.textColorClear);
            tvTitle.setTextColor(mPickerOptions.textColorTitle);
            btnBottom.setTextColor(mPickerOptions.textColorBottom);
            rv_top_bar.setBackgroundColor(mPickerOptions.bgColorTitle);

            //设置文字大小
            btnClear.setTextSize(mPickerOptions.textSizeClear);
            tvTitle.setTextSize(mPickerOptions.textSizeTitle);
            btnBottom.setTextSize(mPickerOptions.textSizeBottom);
        } else {
            mPickerOptions.customListener.customLayout(LayoutInflater.from(context).inflate(mPickerOptions.layoutRes, contentContainer));
        }
        progressBar = (LinearLayout) findViewById(R.id.progressbar);
        // ----滚轮布局
        final LinearLayout optionsPicker = (LinearLayout) findViewById(R.id.optionspicker);
        optionsPicker.setBackgroundColor(mPickerOptions.bgColorWheel);

        wheelOptions = new WheelOptions(optionsPicker, mPickerOptions.linkage, mPickerOptions.isRestoreItem);
        if (mPickerOptions.optionsSelectChangeListener != null) {
            wheelOptions.setOptionsSelectChangeListener(mPickerOptions.optionsSelectChangeListener);
        }

        wheelOptions.setTextContentSize(mPickerOptions.textSizeContent);
        wheelOptions.setLabels(mPickerOptions.label1, mPickerOptions.label2, mPickerOptions.label3,mPickerOptions.label4);
        wheelOptions.setTextXOffset(mPickerOptions.x_offset_one, mPickerOptions.x_offset_two, mPickerOptions.x_offset_three,mPickerOptions.x_offset_four);
        wheelOptions.setCyclic(mPickerOptions.cyclic1, mPickerOptions.cyclic2, mPickerOptions.cyclic3,mPickerOptions.cyclic4);
        wheelOptions.setTypeface(mPickerOptions.font);

        setOutSideCancelable(mPickerOptions.cancelable);

        wheelOptions.setDividerColor(mPickerOptions.dividerColor);
        wheelOptions.setDividerType(mPickerOptions.dividerType);
        wheelOptions.setLineSpacingMultiplier(mPickerOptions.lineSpacingMultiplier);
        wheelOptions.setTextColorOut(mPickerOptions.textColorOut);
        wheelOptions.setTextColorCenter(mPickerOptions.textColorCenter);
        wheelOptions.isCenterLabel(mPickerOptions.isCenterLabel);
    }

    /**
     * 动态设置标题
     *
     * @param text 标题文本内容
     */
    public void setTitleText(String text) {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText(text);
        }
    }

    /**
     * 设置默认选中项
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        mPickerOptions.option1 = option1;
        reSetCurrentItems();
    }


    public void setSelectOptions(int option1, int option2) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        reSetCurrentItems();
    }

    public void setSelectOptions(int option1, int option2, int option3) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        mPickerOptions.option3 = option3;
        reSetCurrentItems();
    }
//不联动
    public void setSelectNOptions(int option1, int option2, int option3) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        mPickerOptions.option3 = option3;
        wheelOptions.setLinkage(false);
        reSetCurrentItems();
    }
    //前两个联动，后两个不联动
    public void setSelect2Options(int option1, int option2, int option3, int option4) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        mPickerOptions.option3 = option3;
        mPickerOptions.option4=option4;
        wheelOptions.setTwoLinkage(true);
        wheelOptions.setLinkage(false);
        reSetCurrentItems();
    }

    private void reSetCurrentItems() {
        if (wheelOptions != null) {
            wheelOptions.setCurrentItems(mPickerOptions.option1, mPickerOptions.option2, mPickerOptions.option3,mPickerOptions.option4);
        }
    }
    public void setPicker(List<T> optionsItems) {
        this.setPicker(optionsItems, null, null);
    }

    public void setPicker(List<T> options1Items, List<List<T>> options2Items) {
        this.setPicker(options1Items, options2Items, null);
    }

    public void setPicker(List<T> options1Items,
                          List<List<T>> options2Items,
                          List<List<List<T>>> options3Items) {

        wheelOptions.setPicker(options1Items, options2Items, options3Items);
        reSetCurrentItems();
    }


    //不联动情况下调用
    public void setNPicker(List<T> options1Items,
                           List<T> options2Items,
                           List<T> options3Items) {

        wheelOptions.setNPicker(options1Items, options2Items, options3Items);
        reSetCurrentItems();
    }
    //前两个联动，后两个不联动情况下调用
    public void set2Picker(List<T> options1Items,
                           List<List<T>> options2Items,
                           List<T> options3Items,
                           List<T> options4Items) {

        wheelOptions.set2Picker(options1Items, options2Items, options3Items,options4Items);
        reSetCurrentItems();
    }
    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_BOTTOM)) {
            returnData();
        }
        if (tag.equals(TAG_CLEAR)){
            clearSelectData();
        }
        dismiss();
    }
    public void clearSelectData() {
        if (mPickerOptions.onClearSelectDataListener!=null){
            mPickerOptions.onClearSelectDataListener.clearData();
        }
    }

    //抽离接口回调的方法
    public void returnData() {
        if (mPickerOptions.optionsSelectListener != null) {
            int[] optionsCurrentItems = wheelOptions.getCurrentItems();
            mPickerOptions.optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2],optionsCurrentItems[3], clickView);
        }
    }


    @Override
    public boolean isDialog() {
        return mPickerOptions.isDialog;
    }

    public void refreshUIAfterGetData(){
        if (progressBar!=null){
            progressBar.setVisibility(View.GONE);
        }
    }
}
