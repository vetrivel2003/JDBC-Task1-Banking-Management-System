package task1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionDAO {

    // ADD TRANSACTION
    public void addTransaction(String accountNumber,
                                String type,
                                double amount) {

        String query =
                "INSERT INTO transactions " +
                "(account_id, transaction_type, amount) " +
                "SELECT account_id, ?, ? " +
                "FROM accounts WHERE account_number = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, type);
            ps.setDouble(2, amount);
            ps.setString(3, accountNumber);

            ps.executeUpdate();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // TRANSACTION HISTORY
    public void showHistory(String accountNumber) {

        String query =
                "SELECT t.transaction_id, " +
                "t.transaction_type, " +
                "t.amount, " +
                "t.transaction_date " +
                "FROM transactions t " +
                "JOIN accounts a " +
                "ON t.account_id = a.account_id " +
                "WHERE a.account_number = ? " +
                "ORDER BY t.transaction_date DESC";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, accountNumber);

            ResultSet rs = ps.executeQuery();

            System.out.println(
                    "\n===== TRANSACTION HISTORY ====="
            );

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "Transaction ID: "
                        + rs.getInt("transaction_id")
                );

                System.out.println(
                        "Type: "
                        + rs.getString("transaction_type")
                );

                System.out.println(
                        "Amount: ₹"
                        + rs.getDouble("amount")
                );

                System.out.println(
                        "Date: "
                        + rs.getTimestamp("transaction_date")
                );

                System.out.println("----------------------------");
            }

            if (!found) {
                System.out.println("No transactions found.");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}