import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Book {
    private String title;
    private String author;
    private String category;
    private boolean available;

    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class ELibrarySystem {
    private Map<String, User> users;
    private List<Book> books;

    public ELibrarySystem() {
        this.users = new HashMap<>();
        this.books = new ArrayList<>();
    }

    public void addUser(String username, String password) {
        users.put(username, new User(username, password));
    }

    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void addBook(String title, String author, String category) {
        books.add(new Book(title, author, category));
    }

    public void displayBooks() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book.getTitle() + " by " + book.getAuthor() + " - " + book.getCategory());
            }
        }
    }

    public void borrowBook(User user, String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                System.out.println(user.getUsername() + " borrowed " + book.getTitle());
                book.setAvailable(false);
                return;
            }
        }
        System.out.println("Book not available for borrowing.");
    }

    public void returnBook(User user, String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isAvailable()) {
                System.out.println(user.getUsername() + " returned " + book.getTitle());
                book.setAvailable(true);
                return;
            }
        }
        System.out.println("Invalid book return request.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ELibrarySystem librarySystem = new ELibrarySystem();

        // Add users
        librarySystem.addUser("user1", "password1");
        librarySystem.addUser("user2", "password2");

        User authenticatedUser = null; // Initialize here

        // Add books
        librarySystem.addBook("Java Programming", "John Doe", "Programming");
        librarySystem.addBook("Introduction to Algorithms", "Alice Smith", "Computer Science");
        librarySystem.addBook("History of Art", "Bob Johnson", "Art");

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Display Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    authenticatedUser = librarySystem.authenticateUser(username, password);
                    if (authenticatedUser != null) {
                        System.out.println("Login successful. Welcome, " + authenticatedUser.getUsername() + "!");
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                    break;

                case 2:
                    librarySystem.displayBooks();
                    break;

                case 3:
                    if (authenticatedUser != null) {
                        System.out.print("Enter the title of the book to borrow: ");
                        String borrowTitle = scanner.nextLine();
                        librarySystem.borrowBook(authenticatedUser, borrowTitle);
                    } else {
                        System.out.println("Please log in first.");
                    }
                    break;

                case 4:
                    if (authenticatedUser != null) {
                        System.out.print("Enter the title of the book to return: ");
                        String returnTitle = scanner.nextLine();
                        librarySystem.returnBook(authenticatedUser, returnTitle);
                    } else {
                        System.out.println("Please log in first.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
