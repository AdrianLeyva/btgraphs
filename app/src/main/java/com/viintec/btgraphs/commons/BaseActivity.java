package com.viintec.btgraphs.commons;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by adrianaldairleyvasanchez on 12/3/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    public static final String TAG = "TAG";
    protected ViewGroup mainLayout;
    protected ProgressBar mProgressBar;


    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showFailedMessage(String message) {

    }

    protected void setView(int contentViewID){
        setContentView(contentViewID);
        bindViews();
        setupData();
        setupPresenter();
        activateListeners();
    }
    protected abstract void bindViews();
    protected abstract void setupData();
    protected abstract void activateListeners();
    protected abstract void setupPresenter();
}
