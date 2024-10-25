import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class IssueBook {

    // Declare the database connection variables
    private static Connection con;
    private static Statement stmt;
    private static JTextField inf1, inf2;
    private static ArrayList<String> allBid = new ArrayList<>();  // List to store Book IDs

    public static void main(String[] args) {
        new IssueBook();
    }

    public IssueBook() {
        // Connect to the database
        try {
            String myPass = "root";  // Replace with your MySQL password
            String myDatabase = "db";  // Replace with your database name
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + myDatabase, "root", myPass);
            stmt = con.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Connection Failed: " + ex.getMessage());
            return;
        }

        // GUI setup using Swing
        JFrame root = new JFrame("Library - Issue Book");
        root.setSize(600, 500);
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setLayout(null);

        // Header
        JPanel headingFrame = new JPanel();
        headingFrame.setBounds(150, 50, 300, 50);
        headingFrame.setBackground(new Color(255, 187, 0));
        root.add(headingFrame);

        JLabel headingLabel = new JLabel("Issue Book");
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setBackground(Color.BLACK);
        headingLabel.setFont(new Font("Courier", Font.BOLD, 20));
        headingLabel.setOpaque(true);
        headingFrame.add(headingLabel);

        // Book ID
        JLabel lb1 = new JLabel("Book ID: ");
        lb1.setBounds(100, 150, 100, 30);
        root.add(lb1);

        inf1 = new JTextField();
        inf1.setBounds(200, 150, 250, 30);
        root.add(inf1);

        // Issued To (Student name)
        JLabel lb2 = new JLabel("Issued To: ");
        lb2.setBounds(100, 200, 100, 30);
        root.add(lb2);

        inf2 = new JTextField();
        inf2.setBounds(200, 200, 250, 30);
        root.add(inf2);

        // Issue Button
        JButton issueBtn = new JButton("Issue");
        issueBtn.setBounds(200, 300, 100, 30);
        issueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                issueBook();
            }
        });
        root.add(issueBtn);

        // Quit Button
        JButton quitBtn = new JButton("Quit");
        quitBtn.setBounds(350, 300, 100, 30);
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                root.dispose();
            }
        });
        root.add(quitBtn);

        root.setVisible(true);
    }

    // Method to issue a book
    private static void issueBook() {
        String bid = inf1.getText();
        String issueto = inf2.getText();

        // Fetch all book IDs
        String extractBid = "SELECT bid FROM books";
        try {
            ResultSet rs = stmt.executeQuery(extractBid);
            while (rs.next()) {
                allBid.add(rs.getString("bid"));
            }

            if (allBid.contains(bid)) {
                // Check availability status of the book
                String checkAvail = "SELECT status FROM books WHERE bid = '" + bid + "'";
                rs = stmt.executeQuery(checkAvail);
                if (rs.next()) {
                    String status = rs.getString("status");
                    if (status.equals("avail")) {
                        // Issue the book if available
                        String issueSql = "INSERT INTO books_issued (bid, issueto) VALUES ('" + bid + "', '" + issueto + "')";
                        String updateStatus = "UPDATE books SET status = 'issued' WHERE bid = '" + bid + "'";
                        stmt.executeUpdate(issueSql);
                        stmt.executeUpdate(updateStatus);
                        con.commit();
                        JOptionPane.showMessageDialog(null, "Book Issued Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book Already Issued");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Book ID not present");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

        // Clear inputs and reset
        inf1.setText("");
        inf2.setText("");
        allBid.clear();
    }
}
