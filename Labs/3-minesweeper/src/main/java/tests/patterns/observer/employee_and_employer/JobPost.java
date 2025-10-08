package tests.patterns.observer.employee_and_employer;

public class JobPost {
    private final String title;

    public JobPost(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}