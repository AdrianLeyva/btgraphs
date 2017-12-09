package com.viintec.btgraphs.commons;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public abstract class BasePresenter<ConcreteView extends BaseView> {

    protected ConcreteView mView;

    public BasePresenter(ConcreteView mView) {
        this.mView = mView;
    }

    protected void notifyFailure(String message) {
        mView.setProgressIndicator(false);
        mView.showFailedMessage(message);
    }
}
