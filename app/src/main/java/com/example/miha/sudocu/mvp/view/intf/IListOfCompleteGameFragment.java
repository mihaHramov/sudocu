package com.example.miha.sudocu.mvp.view.intf;

public interface IListOfCompleteGameFragment extends IListOfNotCompletedGameFragment {

    void deleteGameFromList();

    void onSendGame();

    void onAfterAuthUser();

    void onErrorSendGame();

    void authUser();
}
