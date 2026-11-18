
import java.time.LocalDate;

public class IssueRecord {

    private int issueId;
    private int bookId;
    private String studentName;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;

    public IssueRecord(int issueId, int bookId, String studentName,
                       LocalDate issueDate, LocalDate dueDate) {

        this.issueId = issueId;
        this.bookId = bookId;
        this.studentName = studentName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.fine = 0;
    }

    public int getIssueId() {
        return issueId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getStudentName() {
        return studentName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public double getFine() {
        return fine;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void completeReturn(LocalDate returnDate, double fine) {
        this.returnDate = returnDate;
        this.fine = fine;
    }

    public void displayIssueDetails() {
        System.out.println("Issue ID: " + issueId);
        System.out.println("Book ID: " + bookId);
        System.out.println("Student Name: " + studentName);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("Due Date: " + dueDate);

        if (isReturned()) {
            System.out.println("Return Date: " + returnDate);
            System.out.println("Fine: Rs. " + fine);
        } else {
            System.out.println("Status: Currently Issued");
        }
    }
}
