package com.example.miha.sudocu.View.IView;

public interface IListOfCompleteGameFragment {
    void onSendGame(String msg);
    void onAfterAuthUser();
    void onErrorSendGame(String msg);
    void authUser();
}
