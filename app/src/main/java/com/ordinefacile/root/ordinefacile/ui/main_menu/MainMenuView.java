package com.ordinefacile.root.ordinefacile.ui.main_menu;

/**
 * Created by root on 1/22/18.
 */

public interface MainMenuView   {

    void getStoreId();

    void goToMenu();

    void showSendingSms();

    void showErrorSending(String s);

    void callServiceGone();
}
