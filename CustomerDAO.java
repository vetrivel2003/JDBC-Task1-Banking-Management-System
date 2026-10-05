package task1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {

    public int createCustomer(Customer customer) {

        int customerId = 0;

        String query =
                "INSERT INTO customers(name, phone, email) VALUES (?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    query,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                customerId = rs.getInt(1);
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return customerId;
    }
}