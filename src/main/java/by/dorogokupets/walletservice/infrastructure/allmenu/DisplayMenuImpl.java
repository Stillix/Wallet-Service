package by.dorogokupets.walletservice.infrastructure.allmenu;

public class DisplayMenuImpl implements DisplayMenu{

		@Override
		public void showMainMenu() {
				System.out.println("******Меню******");
				System.out.println("1. Войти");
				System.out.println("2. Зарегистрироваться");
		}


		@Override
		public void showClientMenu() {
				System.out.println("******Меню******");
				System.out.println("1. Просмотр баланса");
				System.out.println("2. Пополнение средств");
				System.out.println("3. Снятие средств");
				System.out.println("4. История транзакций");
		}
}
