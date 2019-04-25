package com.aiitec.greedaostudydemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiitec.greedaostudydemo.util.LogUtil;

/**
 * @Author: ailibin
 * @Time: 2019/04/18
 * @Description: fragment基类
 * @Email: ailibin@qq.com
 */
abstract class BaseFragment extends Fragment {

    protected static final String TAG = "ailibin";

    private final String CLASS_NAME = this.getClass().getName();

    /**
     * attach a activity
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        LogUtil.e(TAG, CLASS_NAME + "---onAttach");
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        LogUtil.e(TAG, CLASS_NAME + "---onStart");
        super.onStart();
    }

    /**
     * to create a view
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG, CLASS_NAME + "---onCreate");
    }

    /**
     * to inflate a view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e(TAG, CLASS_NAME + "---onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * on view is already to create to something for init the view is visible
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtil.e(TAG, CLASS_NAME + "---onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        LogUtil.e(TAG, CLASS_NAME + "---onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtil.e(TAG, CLASS_NAME + "---onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        LogUtil.e(TAG, CLASS_NAME + "---onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        LogUtil.e(TAG, CLASS_NAME + "---onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        LogUtil.e(TAG, CLASS_NAME + "---onDestroy ");
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtil.e(TAG, CLASS_NAME + "---setUserVisibleHint ");
        super.setUserVisibleHint(isVisibleToUser);
    }

}
