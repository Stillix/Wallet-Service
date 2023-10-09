package by.dorogokupets.walletservice.infrastructure.in;



import java.util.Scanner;

public class ConsoleInput {
		private final Scanner scanner = new Scanner(System.in);

		public String readString() {
				return scanner.nextLine();
		}

		public int readInt() {
				try {
						return Integer.parseInt(scanner.nextLine());
				} catch (NumberFormatException e) {
						return -1;
				}
		}
}
