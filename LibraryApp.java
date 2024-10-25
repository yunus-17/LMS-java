import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LibraryApp { // Changed class name to LibraryApp

    // Database connection variables
    private static Connection con;
    private static Statement stmt;

    public static void main(String[] args) {
        new LibraryApp();  // Changed to match the new class name
    }

    public LibraryApp() {  // Constructor matching the class name LibraryApp
        // Database connection
        try {
            String myPass = "root";  // Replace with your MySQL password
            String myDatabase = "db";  // Replace with your database name
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + myDatabase, "root", myPass);
            stmt = con.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Connection Failed: " + ex.getMessage());
            return;
        }

        // GUI setup
        JFrame root = new JFrame("Library");
        root.setSize(600, 500);
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setLayout(null);

        // Set background image
        try {
            ImageIcon background_image = new ImageIcon(ImageIO.read(new File("lib.jpg")));
            Image img = background_image.getImage().getScaledInstance(600, 500, Image.SCALE_SMOOTH);
            background_image = new ImageIcon(img);
            JLabel background = new JLabel(background_image);
            background.setBounds(0, 0, 600, 500);
            root.add(background);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Background image not found.");
        }

        // Heading Frame
        JPanel headingFrame = new JPanel();
        headingFrame.setBounds(120, 50, 360, 80);
        headingFrame.setBackground(new Color(255, 187, 0));
        root.add(headingFrame);

        JLabel headingLabel = new JLabel("<html>Welcome to <br> DataFlair Library</html>");
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setBackground(Color.BLACK);
        headingLabel.setFont(new Font("Courier", Font.BOLD, 15));
        headingLabel.setOpaque(true);
        headingFrame.add(headingLabel);

        // Button: Add Book
        JButton btn1 = new JButton("Add Book Details");
        btn1.setBounds(170, 200, 250, 40);
        btn1.setBackground(Color.BLACK);
        btn1.setForeground(Color.WHITE);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        root.add(btn1);

        // Button: Delete Book
        JButton btn2 = new JButton("Delete Book");
        btn2.setBounds(170, 250, 250, 40);
        btn2.setBackground(Color.BLACK);
        btn2.setForeground(Color.WHITE);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });
        root.add(btn2);

        // Button: View Book List
        JButton btn3 = new JButton("View Book List");
        btn3.setBounds(170, 300, 250, 40);
        btn3.setBackground(Color.BLACK);
        btn3.setForeground(Color.WHITE);
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBooks();
            }
        });
        root.add(btn3);

        // Button: Issue Book
        JButton btn4 = new JButton("Issue Book to Student");
        btn4.setBounds(170, 350, 250, 40);
        btn4.setBackground(Color.BLACK);
        btn4.setForeground(Color.WHITE);
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                issueBook();
            }
        });
        root.add(btn4);

        // Button: Return Book
        JButton btn5 = new JButton("Return Book");
        btn5.setBounds(170, 400, 250, 40);
        btn5.setBackground(Color.BLACK);
        btn5.setForeground(Color.WHITE);
        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
        root.add(btn5);

        root.setVisible(true);
    }

    // Function to open AddBook window
    private static void addBook() {
        // Add Book Logic
        JOptionPane.showMessageDialog(null, "Add Book functionality here.");
        // Implement AddBook function or open a new window for adding book details
    }

    // Function to open DeleteBook window
    private static void deleteBook() {
        // Delete Book Logic
        JOptionPane.showMessageDialog(null, "Delete Book functionality here.");
        // Implement DeleteBook function or open a new window for deleting book details
    }

    // Function to view the list of books
    private static void viewBooks() {
        // View Books Logic
        JOptionPane.showMessageDialog(null, "View Books functionality here.");
        // Implement ViewBooks function or open a new window for viewing the book list
    }

    // Function to issue a book to a student
    private static void issueBook() {
        // Issue Book Logic
        JOptionPane.showMessageDialog(null, "Issue Book functionality here.");
        // Implement IssueBook function or open a new window for issuing books to students
    }

    // Function to return a book
    private static void returnBook() {
        // Return Book Logic
        JOptionPane.showMessageDialog(null, "Return Book functionality here.");
        // Implement ReturnBook function or open a new window for returning books
    }
}
