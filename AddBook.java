import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddBook {

    private JFrame frame;
    private JTextField bookIDField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField statusField;
    private Connection con;
    private Statement stmt;

    public AddBook() {
        initialize();
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            // Change URL, username, and password according to your MySQL setup
            String url =  "jdbc:mysql://localhost:3306/library_db"; // Ensure this is correct\n"; // Your database
            String user = "root";
            String password = ""; // Leave empty if no password for root user

            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            System.out.println("Connection successful!"); // Print success message
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Connection Failed: " + ex.getMessage());
        }
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Add a New Book");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel.setBounds(160, 11, 150, 20);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblBookID = new JLabel("Book ID");
        lblBookID.setBounds(50, 50, 100, 20);
        frame.getContentPane().add(lblBookID);

        bookIDField = new JTextField();
        bookIDField.setBounds(150, 50, 200, 20);
        frame.getContentPane().add(bookIDField);

        JLabel lblTitle = new JLabel("Title");
        lblTitle.setBounds(50, 90, 100, 20);
        frame.getContentPane().add(lblTitle);

        titleField = new JTextField();
        titleField.setBounds(150, 90, 200, 20);
        frame.getContentPane().add(titleField);

        JLabel lblAuthor = new JLabel("Author");
        lblAuthor.setBounds(50, 130, 100, 20);
        frame.getContentPane().add(lblAuthor);

        authorField = new JTextField();
        authorField.setBounds(150, 130, 200, 20);
        frame.getContentPane().add(authorField);

        JLabel lblStatus = new JLabel("Status (Avail/Issued)");
        lblStatus.setBounds(50, 170, 120, 20);
        frame.getContentPane().add(lblStatus);

        statusField = new JTextField();
        statusField.setBounds(180, 170, 170, 20);
        frame.getContentPane().add(statusField);

        JButton addButton = new JButton("Add Book");
        addButton.setBounds(150, 210, 100, 25);
        frame.getContentPane().add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBookToDatabase();
            }
        });

        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(270, 210, 100, 25);
        frame.getContentPane().add(quitButton);
        quitButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    private void addBookToDatabase() {
        String bookID = bookIDField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String status = statusField.getText();

        try {
            String query = "INSERT INTO books (book_id, title, author, status) VALUES ('" + bookID + "', '" + title + "', '" + author + "', '" + status + "')";
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Book added successfully");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to add book: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddBook()); // Ensure thread safety
    }
}
