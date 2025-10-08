package tests.patterns.observer.employee_and_employer;

public class Main {
    public static void main(String[] args) {
        JobSeeker seeker1 = new JobSeeker("Seeker 1");
        JobSeeker seeker2 = new JobSeeker("Seeker 2");

        EmploymentAgency agency = new EmploymentAgency();
        agency.attach(seeker1);
        agency.attach(seeker2);

        agency.addJob(new JobPost("Software Engineer"));

        agency.detach(seeker2);
    }
}
