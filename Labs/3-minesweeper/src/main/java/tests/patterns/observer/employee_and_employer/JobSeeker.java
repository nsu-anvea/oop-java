package tests.patterns.observer.employee_and_employer;

public class JobSeeker implements Observer {
    private final String name;

    public JobSeeker(String name) {
        this.name = name;
    }

    @Override
    public void onJobPosted(JobPost job) {
        System.out.println("Hi " + name + "! New job posted: " +
                job.getTitle());
    }
}