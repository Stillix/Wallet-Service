package by.dorogokupets.walletservice.infrastructure.menu.impl;

import by.dorogokupets.walletservice.infrastructure.menu.DisplayMenu;

public class DisplayMenuImpl implements DisplayMenu {

    @Override
    public void showMainMenu() {
        System.out.println("******����******");
        System.out.println("1. �����");
        System.out.println("2. ������������������");
        System.out.println("3. ����� �� ���������");
    }


    @Override
    public void showClientMenu() {
        System.out.println("******����******");
        System.out.println("1. �������� �������");
        System.out.println("2. ���������� �������");
        System.out.println("3. ������ �������");
        System.out.println("4. ������� ����������");
        System.out.println("5. �����");
    }
}
