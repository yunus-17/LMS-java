import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewBooks {

    // Database connection variables
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static String bookTable = "books"; // Table name

    public static void main(String[] args) {
        View(); // Start the UI
    }

    // Function to view books
    public static void View() {
        // Create the JFrame (Main Window)
        JFrame root = new JFrame("Library - View Books");
        root.setSize(600, 500);
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setLayout(null);

        // Canvas Background Setup
        JPanel canvas = new JPanel();
        canvas.setBackground(new Color(18, 164, 217));
        canvas.setBounds(0, 0, 600, 500);
        root.add(canvas);
        canvas.setLayout(null);

        // Heading Frame
        JPanel headingFrame = new JPanel();
        headingFrame.setBackground(new Color(255, 187, 0));
        headingFrame.setBounds(150, 50, 300, 60);
        canvas.add(headingFrame);

        JLabel headingLabel = new JLabel("View Books", JLabel.CENTER);
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Courier", Font.BOLD, 20));
        headingFrame.add(headingLabel);

        // Label Frame to display book details
        JPanel labelFrame = new JPanel();
        labelFrame.setBackground(Color.BLACK);
        labelFrame.setBounds(60, 150, 480, 250);
        labelFrame.setLayout(null);
        canvas.add(labelFrame);

        // Table headers
        JLabel header = new JLabel(String.format("%-10s%-40s%-30s%-20s", "BID", "Title", "Author", "Status"), JLabel.LEFT);
        header.setForeground(Color.WHITE);
        header.setBounds(10, 10, 460, 30);
        labelFrame.add(header);

        JLabel separator = new JLabel("------------------------------------------------------------------", JLabel.LEFT);
        separator.setForeground(Color.WHITE);
        separator.setBounds(10, 40, 460, 20);
        labelFrame.add(separator);

        // Connect to the database and fetch book data
        try {
            String myPass = "root";  // Replace with your MySQL password
            String myDatabase = "db";  // Replace with your database name
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + myDatabase, "root", myPass);
            stmt = con.createStatement();
            String getBooks = "SELECT * FROM " + bookTable;
            rs = stmt.executeQuery(getBooks);

            int y = 70; // Starting y position for book records

            // Display the book details
            while (rs.next()) {
                JLabel bookLabel = new JLabel(String.format("%-10s%-30s%-30s%-20s",
                        rs.getString("bid"), rs.getString("title"), rs.getString("author"), rs.getString("status")));
                bookLabel.setForeground(Color.WHITE);
                bookLabel.setBounds(10, y, 460, 20);
                labelFrame.add(bookLabel);
                y += 30; // Increment y position for the next book
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to fetch files from database: " + ex.getMessage());
        } finally {
            // Close database resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error closing resources: " + ex.getMessage());
            }
        }

        // Quit Button
        JButton quitBtn = new JButton("Quit");
        quitBtn.setBounds(240, 420, 100, 30);
        quitBtn.setBackground(new Color(247, 241, 227));
        canvas.add(quitBtn);

        // Quit Button Action
        quitBtn.addActionListener(e -> root.dispose());

        root.setVisible(true);
    }
}
