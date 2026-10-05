package task1;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {

    // CREATE ACCOUNT
    public void createAccount(int customerId,
                              String accountNumber,
                              double balance) {

        String query =
                "INSERT INTO accounts(customer_id, account_number, balance) " +
                "VALUES (?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);
            ps.setString(2, accountNumber);
            ps.setDouble(3, balance);

            ps.executeUpdate();

            System.out.println("Account created successfully");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // DEPOSIT
    public void deposit(String accountNumber, double amount) {

        String query =
                "UPDATE accounts SET balance = balance + ? " +
                "WHERE account_number = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);

            int result = ps.executeUpdate();

            if (result > 0) {

                System.out.println("Deposit successful");

                TransactionDAO transactionDAO =
                        new TransactionDAO();

                transactionDAO.addTransaction(
                        accountNumber,
                        "DEPOSIT",
                        amount
                );

            } else {

                System.out.println("Account not found");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // WITHDRAW
    public void withdraw(String accountNumber, double amount) {

        String query =
                "UPDATE accounts SET balance = balance - ? " +
                "WHERE account_number = ? AND balance >= ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);
            ps.setDouble(3, amount);

            int result = ps.executeUpdate();

            if (result > 0) {

                System.out.println("Withdrawal successful");

                TransactionDAO transactionDAO =
                        new TransactionDAO();

                transactionDAO.addTransaction(
                        accountNumber,
                        "WITHDRAW",
                        amount
                );

            } else {

                System.out.println(
                        "Withdrawal failed. " +
                        "Insufficient balance or account not found."
                );
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // FUND TRANSFER
    public void transfer(String fromAccount,
                         String toAccount,
                         double amount) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            // Start transaction
            con.setAutoCommit(false);


            // 1. Deduct money from sender
            String withdrawQuery =
                    "UPDATE accounts " +
                    "SET balance = balance - ? " +
                    "WHERE account_number = ? AND balance >= ?";

            PreparedStatement ps1 =
                    con.prepareStatement(withdrawQuery);

            ps1.setDouble(1, amount);
            ps1.setString(2, fromAccount);
            ps1.setDouble(3, amount);

            int senderResult = ps1.executeUpdate();

            if (senderResult == 0) {

                throw new Exception(
                        "Sender account not found or insufficient balance"
                );
            }


            // 2. Add money to receiver
            String depositQuery =
                    "UPDATE accounts " +
                    "SET balance = balance + ? " +
                    "WHERE account_number = ?";

            PreparedStatement ps2 =
                    con.prepareStatement(depositQuery);

            ps2.setDouble(1, amount);
            ps2.setString(2, toAccount);

            int receiverResult = ps2.executeUpdate();

            if (receiverResult == 0) {

                throw new Exception(
                        "Receiver account not found"
                );
            }


            // 3. Sender transaction
            String transactionQuery =
                    "INSERT INTO transactions " +
                    "(account_id, transaction_type, amount) " +
                    "SELECT account_id, ?, ? " +
                    "FROM accounts WHERE account_number = ?";

            PreparedStatement ps3 =
                    con.prepareStatement(transactionQuery);

            ps3.setString(1, "TRANSFER_DEBIT");
            ps3.setDouble(2, amount);
            ps3.setString(3, fromAccount);

            ps3.executeUpdate();


            // 4. Receiver transaction
            ps3.setString(1, "TRANSFER_CREDIT");
            ps3.setDouble(2, amount);
            ps3.setString(3, toAccount);

            ps3.executeUpdate();


            // Everything successful
            con.commit();

            System.out.println("Transfer successful");


        } catch (Exception e) {

            try {

                if (con != null) {

                    con.rollback();

                    System.out.println(
                            "Transfer failed. Money restored."
                    );
                }

            } catch (Exception rollbackException) {

                rollbackException.printStackTrace();
            }

        } finally {

            try {

                if (con != null) {

                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // CHECK BALANCE
    public void checkBalance(String accountNumber) {

        String query =
                "SELECT account_number, balance " +
                "FROM accounts WHERE account_number = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, accountNumber);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println(
                        "Account Number: "
                        + rs.getString("account_number")
                );

                System.out.println(
                        "Balance: ₹"
                        + rs.getDouble("balance")
                );

            } else {

                System.out.println("Account not found");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}