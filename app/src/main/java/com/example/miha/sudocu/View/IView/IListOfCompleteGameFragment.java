package com.example.miha.sudocu.View.IView;

public interface IListOfCompleteGameFragment extends IListOfNotCompletedGameFragment {
    void onSendGame();
    void onAfterAuthUser();
    void onErrorSendGame();
    void authUser();
}
