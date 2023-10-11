package by.dorogokupets.walletservice.infrastructure.menu.impl;

import by.dorogokupets.walletservice.infrastructure.menu.DisplayMenu;

public class DisplayMenuImpl implements DisplayMenu {

    @Override
    public void showMainMenu() {
        System.out.println("******Меню******");
        System.out.println("1. Войти");
        System.out.println("2. Зарегистрироваться");
        System.out.println("3. Выход из программы");
    }


    @Override
    public void showClientMenu() {
        System.out.println("******Меню******");
        System.out.println("1. Просмотр баланса");
        System.out.println("2. Пополнение средств");
        System.out.println("3. Снятие средств");
        System.out.println("4. История транзакций");
        System.out.println("5. Выход");
    }
}
