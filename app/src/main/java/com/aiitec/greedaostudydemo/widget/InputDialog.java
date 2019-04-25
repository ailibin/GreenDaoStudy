package com.aiitec.greedaostudydemo.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiitec.greedaostudydemo.R;
import com.aiitec.greedaostudydemo.util.ScreenUtils;


public class InputDialog extends Dialog {
    public static final int THEME_DEFAULT = R.style.LoadingDialog;

    protected Context context;
    private View view;
    protected TextView tv_title, tv_content;
    protected TextView tv_dialog_confirm;
    protected TextView tv_dialog_cancel;
    private View view_line;
    private EditText et_input;
    private ImageView iv_delete;
    private int length;


    public int animStyle() {
        return R.style.dialogAnimationStyle;
    }


    private OnOptionListener onOptionConfirmClickListener;
    private OnOptionListener onOptionCancelClickListener;

    public void setConfirmBtnText(String confirmBtnText) {
        if (!TextUtils.isEmpty(confirmBtnText) && tv_dialog_confirm != null) {
            tv_dialog_confirm.setText(confirmBtnText);
        }
    }


    private int layoutId() {
        return R.layout.dialog_input_num;
    }


    public InputDialog(Context context) {
        super(context, THEME_DEFAULT);
        this.context = context;
        init(layoutId());
    }

    public InputDialog(Context context, int length) {
        super(context, THEME_DEFAULT);
        this.context = context;
        this.length = length;
        init(layoutId());
    }

    protected void init(int layoutId) {

        view = LayoutInflater.from(context).inflate(layoutId, null);
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL;
        window.setAttributes(setLayoutParams(params));
        if (animStyle() < 0) {
            setAnimationStyle(R.style.dialogAnimationStyle);
        } else {
            setAnimationStyle(animStyle());
        }
        findView(view);

    }


    public void setAnimationStyle(int animStyle) {
        getWindow().setWindowAnimations(animStyle);
    }

    /**
     * 初始化布局，可重写
     *
     * @param view
     */
    protected void findView(View view) {

        tv_title = view.findViewById(R.id.tv_dialog_title);
        et_input = view.findViewById(R.id.et_input);
        tv_dialog_confirm = view.findViewById(R.id.tv_dialog_confirm);
//        view_line = view.findViewById(R.id.view_line);
        iv_delete = view.findViewById(R.id.iv_delete);


        if (tv_dialog_confirm != null) {
            tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel();
                    if (onOptionConfirmClickListener != null) {
                        onOptionConfirmClickListener.onOption();
                    }
                }
            });
        }

        if (iv_delete != null) {
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //消失弹窗
                    cancel();
                }
            });
        }

    }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
        }
    }

    public int getInputContent() {
        return Integer.parseInt(et_input.getText().toString().trim());
    }

    public void setInputContent(String content) {
        if (et_input != null && !TextUtils.isEmpty(content)) {
            et_input.setText(content);
            et_input.setSelection(content.length());
        }
    }

    public WindowManager.LayoutParams setLayoutParams(
            WindowManager.LayoutParams lp) {
        // 设置宽度
        lp.width = (int) (ScreenUtils.getScreenWidth() * 0.7);
        return lp;
    }


    public void setTitle(String title) {
        if (tv_title != null && !TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (tv_title != null && !TextUtils.isEmpty(title)) {
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
        }
    }


    @SuppressLint("ResourceType")
    @Override
    public void setTitle(@StringRes int titleId) {
        if (tv_title != null && titleId > 0) {
            tv_title.setText(titleId);
            tv_title.setVisibility(View.VISIBLE);
        }
    }

    public void setContent(String content) {
        if (tv_content != null && !TextUtils.isEmpty(content)) {
            tv_content.setVisibility(View.VISIBLE);
            tv_content.setText(content);
        }
    }

    /**
     * 这里不要把dialog全部居中
     *
     * @param params
     */
    @Deprecated
    public void setGravity(WindowManager.LayoutParams params) {
        if (params == null) {
            params.gravity = Gravity.CENTER_VERTICAL;
        }
    }

    public void setGravity(int gravity) {
        getWindow().getAttributes().gravity = gravity;
    }

    /**
     * 隐藏部分控件
     *
     * @param id
     */
    public void goneView(int id) {
        if (view != null) {
            View goneView = view.findViewById(id);
            if (goneView != null) {
                goneView.setVisibility(View.GONE);
            }
        }
    }

    public void visibilityView(int id) {
        if (view != null) {
            View goneView = view.findViewById(id);
            if (goneView != null) {
                goneView.setVisibility(View.VISIBLE);
            }
        }
    }

    public View getView() {
        return view;
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }


    public void setOnConfirmClickListener(OnOptionListener listener) {
        onOptionConfirmClickListener = listener;
    }


    public void setOnCancelClickListener(OnOptionListener listener) {
        onOptionCancelClickListener = listener;
    }


    public interface OnOptionListener {
        void onOption();
    }

}