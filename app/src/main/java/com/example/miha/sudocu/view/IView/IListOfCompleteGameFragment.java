package com.example.miha.sudocu.view.IView;

public interface IListOfCompleteGameFragment extends IListOfNotCompletedGameFragment {
    void onSendGame();
    void onAfterAuthUser();
    void onErrorSendGame();
    void authUser();
}
