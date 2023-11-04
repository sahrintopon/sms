/*****************************************************************************
 * Author: Sahrin Topon
 * Cohort Code: PGSE-PFS-0923
 * Version: 1.0
 * School: GreatLearning University (GLU)
 *****************************************************************************/

//Setting up the environment to import necessary library for Student Management
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// This class represents a Student Management System following OOP principles.
public class StudentManagementSystem {

    // Abstraction (OOP): Student class encapsulates student attributes and methods.
    static class Student {
        private String id;
        private String name;
        private int courseCode;
        private int year;
        private double balance;
        private String email;
        private double paidFee;

        // Encapsulation (OOP): Private fields with public getters for access.
        public Student(String id, String name, int courseCode, int year, String email) {
            this.id = id;
            this.name = name;
            this.courseCode = courseCode;
            this.year = year;
            this.balance = calculateFees();
            this.email = email;
            this.paidFee = 0; // Initialize paidFee to 0
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCourseCode() {
            return courseCode;
        }

        public int getYear() {
            return year;
        }

        public double getBalance() {
            return balance;
        }

        public String getEmail() {
            return email;
        }

        public double getPaidFee() {
            return paidFee;
        }

        public void setPaidFee(double amount) {
            paidFee = amount;
        }

        // Abstraction (OOP): Method to get the name of the course based on the course code.
        public String getCourseName() {
            switch (courseCode) {
                case 101:
                    return "Programming Foundations";
                case 102:
                    return "User Interface Design";
                case 103:
                    return "User Experience Design";
                case 104:
                    return "Database Design and Implementation";
                case 105:
                    return "Web Development Foundations";
                case 106:
                    return "Capstone Project";
                default:
                    return "Unknown";
            }
        }

        // Abstraction (OOP): Method to get the fees associated with a course based on the course code.
        public double calculateFees() {
            switch (courseCode) {
                case 101:
                    return 1200;
                case 102:
                    return 1800;
                case 103:
                    return 2000;
                case 104:
                    return 1500;
                case 105:
                    return 2000;
                case 106:
                    return 2500;
                default:
                    return 0;
            }
        }

        // Encapsulation (OOP): Method to make a payment and update the balance.
        public void makePayment(double amount) {
            balance -= amount;
            paidFee += amount; // Update the paidFee
        }

        // Encapsulation (OOP): Method to send a confirmation email to the student.
        public boolean sendConfirmationEmail() {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "mail.smtp2go.com");
            properties.put("mail.smtp.port", "25");
            properties.put("mail.smtp.auth", "true");

            // Creating an email session with authentication.
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication("greatlearninguniversity", "P@ssw0rd@12345");
                }
            });

            try {
                // Creating an email message.
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("GreatLearningUniversity@top10final.com"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(getEmail()));
                message.setSubject("Great Learning University : Enrollment Confirmation");

                // Composing the email content.
                String emailText = "Dear " + getName() + ",\n\n";
                emailText += "Congratulations!\n\nYou have been successfully enrolled into our system.\nHere are your details:\n";
                emailText += "Student ID: " + getId() + "\n";
                emailText += "Course: " + getCourseName() + "\n";
                emailText += "Year: " + getYear() + "\n";
                emailText += "Fees: $" + calculateFees() + "\n";
                emailText += "Paid: $" + getPaidFee() + "\n"; // Include the paid fee
                emailText += "Balance: $" + getBalance() + "\n\n";
                emailText += "Welcome to Great Learning!\n\nBest regards,\nThe Student Management System";

                message.setText(emailText);

                // Establishing a connection and sending the email.
                Transport transport = session.getTransport("smtp");
                transport.connect("mail.smtp2go.com", "sahrin", "Um@1rah143");
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

                System.out.println("Confirmation email sent to " + getName() + " at " + getEmail());
                return true;
            } catch (MessagingException mex) {
                System.out.println("Failed to send confirmation email to " + getName() + " at " + getEmail());
                return false;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        // Display course options and gather student information.
        System.out.print("+------------------------------------------------------------------+\n");
        System.out.print("|    ID    |              Course                | Year |   Fees    |\n");
        System.out.print("+------------------------------------------------------------------+\n");

        String courseFormat = "|    %s   | %-34s |  %d   | $%.2f  |\n";

        System.out.printf(courseFormat, "101", "Programming Foundations", 1, 1200.0);
        System.out.printf(courseFormat, "102", "User Interface Design", 2, 1800.0);
        System.out.printf(courseFormat, "103", "User Experience Design", 3, 2000.0);
        System.out.printf(courseFormat, "104", "Database Design and Implementation", 2, 1500.0);
        System.out.printf(courseFormat, "105", "Web Development Foundations", 1, 2000.0);
        System.out.printf(courseFormat, "106", "Capstone Project", 3, 2500.0);

        System.out.print("+------------------------------------------------------------------+\n");

        // Process and display student information, and provide various options to the user.
        System.out.print("How many new students will be added? ");
        int numberOfStudents = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character.

        for (int i = 0; i < numberOfStudents; i++) {
            System.out.println("Enter student details for student #" + (i + 1));

            System.out.print("Name: ");
            String name = scanner.nextLine(); // Read the name with nextLine() to consume the newline character.

            System.out.print("Course (101 - 106): ");
            int courseCode = scanner.nextInt();

            if (courseCode < 101 || courseCode > 106) {
                System.out.println("Invalid course code. Please enter a valid code.");
                i--;
                continue;
            }

            System.out.print("Year (1, 2, or 3): ");
            int year = scanner.nextInt();

            if (year < 1 || year > 3) {
                System.out.println("Invalid year. Please enter a valid year.");
                i--;
                continue;
            }

            scanner.nextLine(); // Consume the newline character.

            System.out.print("Email address: ");
            String email = scanner.nextLine();

            String uniqueID = String.format("%d%05d", year, i + 1);

            Student student = new Student(uniqueID, name, courseCode, year, email);
            students.add(student);
        }

        System.out.println("\nStudent Details:");
        System.out.println("+-------------------------------------------------------------------------------------------------+");
        System.out.println("|   ID   |          Name           |               Course               |  Year | Fees  | Balance |");
        System.out.println("+-------------------------------------------------------------------------------------------------+");

        for (Student student : students) {
            System.out.printf("| %s | %-23s | %-34s |   %d   | $%.0f |  $%.0f  |\n",
                    student.getId(), student.getName(), student.getCourseName(), student.getYear(), student.calculateFees(), student.getBalance());
        }

        System.out.println("+-------------------------------------------------------------------------------------------------+");
        boolean quit = false;

        while (!quit) {
            System.out.println("\nOptions:");
            System.out.println("1 - View student status by ID");
            System.out.println("2 - Make a payment");
            System.out.println("3 - View All Students");
            System.out.println("4 - Enroll Students and Send Confirmation");
            System.out.println("5 - Quit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                // Option 1: View student status by ID.
                case 1:
                    System.out.print("Enter a student ID to view their fee details: ");
                    String viewID = scanner.next();
                    boolean found = false;

                    for (Student student : students) {
                        if (student.getId().equals(viewID)) {
                            System.out.println("Student " + student.getName() + " with ID " + student.getId() +
                                    " has fees of $" + student.calculateFees());
                            System.out.println("Current Balance: $" + student.getBalance()); // Display the correct balance
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Student with ID " + viewID + " not found.");
                    }
                    break;

                // Option 2: Make a payment.
                case 2:
                    System.out.print("Enter a student ID to make a payment: ");
                    String paymentID = scanner.next();
                    found = false;

                    for (Student student : students) {
                        if (student.getId().equals(paymentID)) {
                            System.out.print("Enter the payment amount: $");
                            double amount = scanner.nextDouble();
                            student.makePayment(amount);
                            System.out.println("Payment of $" + amount + " made for student " + student.getName() +
                                    ". Updated balance: $" + student.getBalance());
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Student with ID " + paymentID + " not found.");
                    }
                    break;

                // Option 3: View all students.
                case 3:
                    System.out.println("\nAll Students:");
        System.out.println("+-------------------------------------------------------------------------------------------------+");
        System.out.println("|   ID   |          Name           |               Course               |  Year | Fees  | Balance |");
        System.out.println("+-------------------------------------------------------------------------------------------------+");

                    double totalFees = 0.0;
                    double totalBalance = 0.0;

                    for (Student student : students) {
                        System.out.printf("| %s | %-23s | %-34s |   %d   | $%.0f |  $%.0f  |\n",
                                student.getId(), student.getName(), student.getCourseName(), student.getYear(), student.calculateFees(), student.getBalance());
                        totalFees += student.calculateFees();
                        totalBalance += student.getBalance();
                    }

                    System.out.println("+-------------------------------------------------------------------------------------------------+");
                    System.out.println("Total Fees: $" + totalFees);
                    System.out.println("Total Balance: $" + totalBalance);
                    break;

                // Option 4: Enroll Students and Send Confirmation.
                case 4:
                    for (Student student : students) {
                        if (student.sendConfirmationEmail()) {
                            System.out.println("Confirmation email sent to " + student.getName() + " at " + student.getEmail());
                        } else {
                            System.out.println("Failed to send confirmation email to " + student.getName() + " at " + student.getEmail());
                        }
                    }
                    System.out.println("Enrollment confirmation emails sent to all students.");
                    break;

                // Option 5: Quit
                case 5:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        scanner.close();
    }
}
