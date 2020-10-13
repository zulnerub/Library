package model.common;

import java.time.LocalDate;

public class UserRegistryForm {
    private String username;
    private String ISBN;
    private LocalDate startDate;
    private LocalDate endDate;

    public UserRegistryForm(String username, String ISBN) {
        this.username = username;
        this.ISBN = ISBN;
    }

    public UserRegistryForm(String username, String ISBN, int endTime) {
        this(username, ISBN);
        this.startDate = LocalDate.now();
        this.endDate = startDate.plusDays(endTime);
    }

    public String getUsername() {
        return username;
    }

    public String getISBN() {
        return ISBN;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void extendDueDate(int days) {
        endDate = endDate.plusDays(days);
    }
}
