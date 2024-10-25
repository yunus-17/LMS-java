import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ReturnBook {

    // Database connection variables
    private static Connection con;
    private static Statement stmt;
    private static PreparedStatement pstmt;
    private static ResultSet rs;
    private static String issueTable = "books_issued"; // Issue Table
    private static String bookTable = "books"; // Book Table
    private static JTextField bookInfo1; // Book ID input field

    public static void main(String[] args) {
        returnBook(); // Start the UI
    }

    public static void returnBook() {
        // Create the JFrame (Main Window)
        JFrame root = new JFrame("Library - Return Book");
        root.setSize(600, 500);
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setLayout(null);

        // Set Background Color
        JPanel canvas = new JPanel();
        canvas.setBackground(new Color(0, 107, 56));
        canvas.setBounds(0, 0, 600, 500);
        root.add(canvas);
        canvas.setLayout(null);

        // Heading Frame
        JPanel headingFrame = new JPanel();
        headingFrame.setBackground(new Color(255, 187, 0));
        headingFrame.setBounds(150, 50, 300, 60);
        canvas.add(headingFrame);

        JLabel headingLabel = new JLabel("Return Book", JLabel.CENTER);
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Courier", Font.BOLD, 20));
        headingFrame.add(headingLabel);

        // Label and input field for Book ID
        JLabel lb1 = new JLabel("Book ID:");
        lb1.setForeground(Color.WHITE);
        lb1.setBounds(150, 150, 100, 30);
        canvas.add(lb1);

        bookInfo1 = new JTextField();
        bookInfo1.setBounds(250, 150, 200, 30);
        canvas.add(bookInfo1);

        // Return Button
        JButton returnBtn = new JButton("Return");
        returnBtn.setBounds(200, 250, 100, 30);
        returnBtn.setBackground(new Color(209, 204, 192));
        canvas.add(returnBtn);

        // Quit Button
        JButton quitBtn = new JButton("Quit");
        quitBtn.setBounds(310, 250, 100, 30);
        quitBtn.setBackground(new Color(247, 241, 227));
        canvas.add(quitBtn);

        // Return Button Action
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnn(); // Call the function to return the book
            }
        });

        // Quit Button Action
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                root.dispose(); // Close the window
            }
        });

        root.setVisible(true);
    }

    // Function to return a book
    public static void returnn() {
        String bid = bookInfo1.getText();
        boolean status = false;
        try {
            // Establish the database connection
            String myPass = "root";  // Replace with your MySQL password
            String myDatabase = "db";  // Replace with your database name
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + myDatabase, "root", myPass);
            stmt = con.createStatement();

            // Fetch all book IDs from the issue table
            String extractBid = "SELECT bid FROM " + issueTable;
            rs = stmt.executeQuery(extractBid);
            boolean bookFound = false;
            while (rs.next()) {
                if (bid.equals(rs.getString("bid"))) {
                    bookFound = true;
                    break;
                }
            }

            if (bookFound) {
                // Check book status
                String checkAvail = "SELECT status FROM " + bookTable + " WHERE bid = ?";
                pstmt = con.prepareStatement(checkAvail);
                pstmt.setString(1, bid);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String bookStatus = rs.getString("status");
                    if (bookStatus.equals("issued")) {
                        status = true;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Book ID not present");
            }

            // If the book was found and its status is 'issued', return the book
            if (bookFound && status) {
                String deleteIssue = "DELETE FROM " + issueTable + " WHERE bid = ?";
                pstmt = con.prepareStatement(deleteIssue);
                pstmt.setString(1, bid);
                pstmt.executeUpdate();

                String updateStatus = "UPDATE " + bookTable + " SET status = 'avail' WHERE bid = ?";
                pstmt = con.prepareStatement(updateStatus);
                pstmt.setString(1, bid);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Book Returned Successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Please check the Book ID");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } finally {
            // Close all resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error closing resources: " + ex.getMessage());
            }
        }
    }
}
