
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LibraryService service = new LibraryService();

        // Load sample books when application starts
        service.addDefaultBooks();

        int choice;

        do {

            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Issue Book");
            System.out.println("7. Return Book");
            System.out.println("8. View Issued Books");
            System.out.println("9. Exit");

            System.out.print("Enter your choice: ");

            choice = readInt(sc);

            switch (choice) {

                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = readInt(sc);

                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Author Name: ");
                    String author = sc.nextLine();

                    System.out.print("Enter Quantity: ");
                    int quantity = readInt(sc);

                    Book book = new Book(
                            id, title, author, quantity
                    );

                    service.addBook(book);
                    break;

                case 2:
                    service.viewBooks();
                    break;

                case 3:
                    System.out.print("Enter Book ID to search: ");
                    int searchId = readInt(sc);

                    service.searchBook(searchId);
                    break;

                case 4:
                    System.out.print("Enter Book ID to update: ");
                    int updateId = readInt(sc);

                    System.out.print("Enter New Title: ");
                    String newTitle = sc.nextLine();

                    System.out.print("Enter New Author: ");
                    String newAuthor = sc.nextLine();

                    System.out.print("Enter New Total Quantity: ");
                    int newQuantity = readInt(sc);

                    service.updateBook(
                            updateId,
                            newTitle,
                            newAuthor,
                            newQuantity
                    );
                    break;

                case 5:
                    System.out.print("Enter Book ID to delete: ");
                    int deleteId = readInt(sc);

                    service.deleteBook(deleteId);
                    break;

                case 6:
                    System.out.print("Enter Book ID to issue: ");
                    int issueBookId = readInt(sc);

                    System.out.print("Enter Student Name: ");
                    String studentName = sc.nextLine();

                    service.issueBook(issueBookId, studentName);
                    break;

                case 7:
                    System.out.print("Enter Issue ID: ");
                    int issueId = readInt(sc);

                    service.returnBook(issueId);
                    break;

                case 8:
                    service.viewIssuedBooks();
                    break;

                case 9:
                    System.out.println(
                            "Thank you for using the Library Management System!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice! Please try again."
                    );
            }

        } while (choice != 9);

        sc.close();
    }

    // Read integer safely
    public static int readInt(Scanner sc) {

        while (true) {

            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;

            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
