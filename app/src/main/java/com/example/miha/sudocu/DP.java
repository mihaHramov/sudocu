package com.example.miha.sudocu;

import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.presenter.PresenterListOfCompleteGameFragment;
import com.example.miha.sudocu.presenter.PresenterListOfGameFragment;


public class DP {
    private static DP dp;
    private PresenterListOfCompleteGameFragment mPresenterListOfCompleteGameFragment;
    private PresenterListOfGameFragment mPresenterListOfGameFragment;
    private IPresenterGrid mPresenterOfGrid;

    public static DP get() {
        return dp;
    }

    public static void setDp(DP dp) {
        DP.dp = dp;
    }

    public PresenterListOfCompleteGameFragment getPresenterListOfCompleteGameFragment() {
        return mPresenterListOfCompleteGameFragment;
    }

    public PresenterListOfGameFragment getPresenterListOfGameFragment() {
        return mPresenterListOfGameFragment;
    }

    public DP setPresenterListOfCompleteGameFragment(PresenterListOfCompleteGameFragment presenter) {
        mPresenterListOfCompleteGameFragment = presenter;
        return this;
    }

    public DP setPresenterListOfGameFragment(PresenterListOfGameFragment presenter) {
        mPresenterListOfGameFragment = presenter;
        return this;
    }

    public DP setPresenterGrid(IPresenterGrid presenterGrid) {
        mPresenterOfGrid = presenterGrid;
        return this;
    }

    public IPresenterGrid getPresenterOfGrid() {
        return mPresenterOfGrid;
    }

}
