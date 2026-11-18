
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LibraryService {

    private List<Book> books = new ArrayList<>();
    private List<IssueRecord> issueRecords = new ArrayList<>();

    private int nextIssueId = 1;

    private static final int LOAN_DAYS = 14;
    private static final double FINE_PER_DAY = 2.0;

    // Find book by ID
    private Book findBookById(int bookId) {

        for (Book b : books) {
            if (b.getBookId() == bookId) {
                return b;
            }
        }

        return null;
    }

    // Find issue record by ID
    private IssueRecord findIssueById(int issueId) {

        for (IssueRecord record : issueRecords) {
            if (record.getIssueId() == issueId) {
                return record;
            }
        }

        return null;
    }

    // Add book
    public void addBook(Book book) {

        if (book.getBookId() <= 0 || book.getQuantity() <= 0
                || book.getTitle().trim().isEmpty()
                || book.getAuthor().trim().isEmpty()) {

            System.out.println("Invalid book details!");
            return;
        }

        if (findBookById(book.getBookId()) != null) {
            System.out.println("Book ID already exists!");
            return;
        }

        books.add(book);
        System.out.println("Book added successfully!");
    }

    // View all books
    public void viewBooks() {

        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (Book b : books) {
            b.displayBookDetails();
            System.out.println("--------------------");
        }
    }

    // Search book
    public void searchBook(int bookId) {

        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found!");
        } else {
            System.out.println("Book found!");
            book.displayBookDetails();
        }
    }

    // Update book
    public void updateBook(int bookId, String title,
                           String author, int newQuantity) {

        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        if (title.trim().isEmpty() || author.trim().isEmpty()
                || newQuantity <= 0) {

            System.out.println("Invalid book details!");
            return;
        }

        int currentlyIssued =
                book.getQuantity() - book.getAvailableQuantity();

        if (newQuantity < currentlyIssued) {
            System.out.println(
                    "Quantity cannot be less than currently issued copies!"
            );
            return;
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setQuantity(newQuantity);
        book.setAvailableQuantity(newQuantity - currentlyIssued);

        System.out.println("Book updated successfully!");
    }

    // Delete book
    public void deleteBook(int bookId) {

        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        if (book.getAvailableQuantity() != book.getQuantity()) {
            System.out.println(
                    "Cannot delete! Some copies are currently issued."
            );
            return;
        }

        books.remove(book);
        System.out.println("Book deleted successfully!");
    }

    // Issue book
    public void issueBook(int bookId, String studentName) {

        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        if (studentName.trim().isEmpty()) {
            System.out.println("Student name cannot be empty!");
            return;
        }

        if (book.getAvailableQuantity() <= 0) {
            System.out.println("No copies available for issuing!");
            return;
        }

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(LOAN_DAYS);

        IssueRecord record = new IssueRecord(
                nextIssueId,
                bookId,
                studentName,
                issueDate,
                dueDate
        );

        issueRecords.add(record);
        nextIssueId++;

        book.setAvailableQuantity(
                book.getAvailableQuantity() - 1
        );

        System.out.println("Book issued successfully!");
        System.out.println("Issue ID: " + record.getIssueId());
        System.out.println("Due Date: " + dueDate);
    }

    // Return book
    public void returnBook(int issueId) {

        IssueRecord record = findIssueById(issueId);

        if (record == null) {
            System.out.println("Issue record not found!");
            return;
        }

        if (record.isReturned()) {
            System.out.println("This book has already been returned!");
            return;
        }

        Book book = findBookById(record.getBookId());

        if (book == null) {
            System.out.println("Book record not found!");
            return;
        }

        LocalDate returnDate = LocalDate.now();

        long lateDays = ChronoUnit.DAYS.between(
                record.getDueDate(), returnDate
        );

        if (lateDays < 0) {
            lateDays = 0;
        }

        double fine = lateDays * FINE_PER_DAY;

        record.completeReturn(returnDate, fine);

        book.setAvailableQuantity(
                book.getAvailableQuantity() + 1
        );

        System.out.println("Book returned successfully!");
        System.out.println("Late Days: " + lateDays);
        System.out.println("Fine Amount: Rs. " + fine);
    }

    // View currently issued books
    public void viewIssuedBooks() {

        boolean found = false;

        for (IssueRecord record : issueRecords) {

            if (!record.isReturned()) {
                record.displayIssueDetails();
                System.out.println("--------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books are currently issued.");
        }
    }

    // Load sample books
    public void addDefaultBooks() {

        addBook(new Book(101, "The Origin of Species",
                "Charles Darwin", 12));

        addBook(new Book(102, "Wings of Fire",
                "A P J Abdul Kalam", 20));

        addBook(new Book(103, "Relativity",
                "Albert Einstein", 10));
    }
}
