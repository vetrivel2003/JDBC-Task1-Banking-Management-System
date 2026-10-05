package task1;

import java.util.Scanner;

public class BankingApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        while (true) {

            System.out.println("\n==============================");
            System.out.println("   BANKING MANAGEMENT SYSTEM");
            System.out.println("==============================");

            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Fund Transfer");
            System.out.println("5. Check Balance");
            System.out.println("6. Transaction History");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();


            switch (choice) {

                // CREATE ACCOUNT
                case 1:

                    sc.nextLine();

                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    Customer customer =
                            new Customer(name, phone, email);

                    int customerId =
                            customerDAO.createCustomer(customer);

                    if (customerId > 0) {

                        System.out.print(
                                "Enter account number: "
                        );

                        String accountNumber =
                                sc.nextLine();

                        System.out.print(
                                "Enter initial balance: "
                        );

                        double balance =
                                sc.nextDouble();

                        accountDAO.createAccount(
                                customerId,
                                accountNumber,
                                balance
                        );
                    }

                    break;


                // DEPOSIT
                case 2:

                    sc.nextLine();

                    System.out.print(
                            "Enter account number: "
                    );

                    String depositAccount =
                            sc.nextLine();

                    System.out.print(
                            "Enter amount: "
                    );

                    double depositAmount =
                            sc.nextDouble();

                    accountDAO.deposit(
                            depositAccount,
                            depositAmount
                    );

                    break;


                // WITHDRAW
                case 3:

                    sc.nextLine();

                    System.out.print(
                            "Enter account number: "
                    );

                    String withdrawAccount =
                            sc.nextLine();

                    System.out.print(
                            "Enter amount: "
                    );

                    double withdrawAmount =
                            sc.nextDouble();

                    accountDAO.withdraw(
                            withdrawAccount,
                            withdrawAmount
                    );

                    break;


                // FUND TRANSFER
                case 4:

                    sc.nextLine();

                    System.out.print(
                            "Enter sender account number: "
                    );

                    String sender =
                            sc.nextLine();

                    System.out.print(
                            "Enter receiver account number: "
                    );

                    String receiver =
                            sc.nextLine();

                    System.out.print(
                            "Enter amount: "
                    );

                    double transferAmount =
                            sc.nextDouble();

                    accountDAO.transfer(
                            sender,
                            receiver,
                            transferAmount
                    );

                    break;


                // CHECK BALANCE
                case 5:

                    sc.nextLine();

                    System.out.print(
                            "Enter account number: "
                    );

                    String balanceAccount =
                            sc.nextLine();

                    accountDAO.checkBalance(
                            balanceAccount
                    );

                    break;


                // TRANSACTION HISTORY
                case 6:

                    sc.nextLine();

                    System.out.print(
                            "Enter account number: "
                    );

                    String historyAccount =
                            sc.nextLine();

                    transactionDAO.showHistory(
                            historyAccount
                    );

                    break;


                // EXIT
                case 7:

                    System.out.println(
                            "Thank you for using Banking System."
                    );

                    sc.close();
                    System.exit(0);


                default:

                    System.out.println(
                            "Invalid choice"
                    );
            }
        }
    }
}