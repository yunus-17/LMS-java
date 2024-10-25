import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteBook {

    // Declare your database connection variables
    private static Connection con;
    private static Statement stmt;
    private static JTextField bookInfo1;

    public static void main(String[] args) {
        new DeleteBook();
    }

    public DeleteBook() {
        // Establishing MySQL connection
        try {
            String myPass = "root";  // replace with your MySQL password
            String myDatabase = "db";  // replace with your database name
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + myDatabase, "root", myPass);
            stmt = con.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Connection Failed: " + ex.getMessage());
            return;
        }

        // GUI setup using Swing
        JFrame root = new JFrame("Library - Delete Book");
        root.setSize(600, 500);
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setLayout(null);

        // Heading Frame
        JPanel headingFrame = new JPanel();
        headingFrame.setBounds(150, 50, 300, 50);
        headingFrame.setBackground(new Color(255, 187, 0));
        root.add(headingFrame);

        JLabel headingLabel = new JLabel("Delete Book");
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setBackground(Color.BLACK);
        headingLabel.setFont(new Font("Courier", Font.BOLD, 20));
        headingLabel.setOpaque(true);
        headingFrame.add(headingLabel);

        // Book ID Label and Text Field
        JLabel lb1 = new JLabel("Book ID: ");
        lb1.setBounds(100, 150, 100, 30);
        root.add(lb1);

        bookInfo1 = new JTextField();
        bookInfo1.setBounds(200, 150, 250, 30);
        root.add(bookInfo1);

        // Submit Button
        JButton submitBtn = new JButton("SUBMIT");
        submitBtn.setBounds(200, 250, 100, 30);
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });
        root.add(submitBtn);

        // Quit Button
        JButton quitBtn = new JButton("QUIT");
        quitBtn.setBounds(350, 250, 100, 30);
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                root.dispose();
            }
        });
        root.add(quitBtn);

        root.setVisible(true);
    }

    // Method to delete a book from the database
    private static void deleteBook() {
        String bid = bookInfo1.getText();

        String deleteBookSQL = "DELETE FROM books WHERE bid = '" + bid + "'";
        String deleteIssuedBookSQL = "DELETE FROM books_issued WHERE bid = '" + bid + "'";

        try {
            // Deleting from 'books' table
            stmt.executeUpdate(deleteBookSQL);
            con.commit();

            // Deleting from 'books_issued' table
            stmt.executeUpdate(deleteIssuedBookSQL);
            con.commit();

            JOptionPane.showMessageDialog(null, "Book Record Deleted Successfully");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Please check Book ID\nError: " + ex.getMessage());
        }

        // Clear the text field after deletion
        bookInfo1.setText("");
    }
}
