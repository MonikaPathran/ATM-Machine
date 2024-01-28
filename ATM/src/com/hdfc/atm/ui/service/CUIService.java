package com.hdfc.atm.ui.service;

import java.util.Scanner;

import com.hdfc.atm.iservice.IAccountService;
import com.hdfc.atm.service.AccountService;
import com.hdfc.atm.ui.iservice.ICUIService;
import com.hdfc.atm.iservice.IAuthenticationService;
import com.hdfc.atm.service.AuthenticationService;

public class CUIService implements ICUIService {
	IAuthenticationService authenticationService = new AuthenticationService();
	Scanner scanner = new Scanner(System.in);

	@Override
	public void showCUI() {
		while (true) {
			showMenu();
		}
	}

	private void showMenu() {
		System.out.println("\n1.Deposit\n2.Withdraw\n3.Balance\n4.PIN Change\n5.EXIT");
		promptUserChoice();
	}

	private void promptUserChoice() {

		System.out.println("Enter choice : ");
		Integer choice = scanner.nextInt();
		operations(choice);
	}

	private void operations(Integer choice) {
		IAccountService accountService = new AccountService();
		Integer amount = null;
		switch (choice) {

		case 1:
			amount = readAmount();
			if (validateAmount(amount)) {
				if (accountService.deposit(amount)) {
					System.out.println("Amount deposited");
				} else {
					System.out.println("Failed to deposit the amount");
				}
			} else {
				System.out.println("Invalid amount entered");
			}
			break;
		case 2:
			if (authorizeUser()) {
				amount = readAmount();
				if (validateAmount(amount)) {
					if (accountService.getBalance() >= amount) {
						if (accountService.withdraw(amount)) {

							System.out.println("Amount deducted.");
						} else {
							System.out.println("Failed to deduct the amount.");
						}
					} else {
						System.out.println("Insufficient amount");
					}
				} else {
					System.out.println("Invalid amount entered.");
				}
			}
			break;
		case 3:
			if (authorizeUser()) {
				System.out.println("Balanace :" + accountService.getBalance());
			}
			break;
		case 4:
			if (authorizeUser()) {
				Integer newPin = readPIN();
				if (validatePIN(newPin)) {
					if (authenticationService.resetPin(newPin)) {
						System.out.println("PIN Chnaged");
					} else {
						System.out.println("PIN Changed is failed.");
					}
				} else {
					System.out.println("Invalid Pin entered.");
				}

			}
			break;
		case 5:
			System.exit(0);
		default:
			System.out.println("Invalid choice.");
			break;

		}
	}

	private Integer readAmount() {
		System.out.println("enter amount: ");
		return scanner.nextInt();

	}

	private boolean validateAmount(Integer amount) {
		return ((amount > 0) && (amount % 100 == 0)) ? true : false;
	}

	private Integer readPIN() {
		System.out.println("enter PIN: ");
		return scanner.nextInt();

	}

	private boolean validatePIN(Integer amount) {
		return ((amount > 999) && ((amount <= 9999)) ? true : false);
	}

	private boolean authorizeUser() {

		boolean isAuthorized = false;

		Integer count = 1;
		Integer pin = null;
		while (count <= 3) {
			pin = readPIN();
			if (validatePIN(pin)) {
				if (authenticationService.authentication(pin)) {
					isAuthorized = true;
					break;
				} else {
					count++;
					if (count >= 3) {
						System.out.println("Unauthorized user. Terminating the program");
					}
				}
			} else {
				System.out.println("Invalid PIN entered");
			}
		}

		return isAuthorized;
	}

}
