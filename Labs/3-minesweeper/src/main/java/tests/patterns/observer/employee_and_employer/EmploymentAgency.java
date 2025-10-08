package tests.patterns.observer.employee_and_employer;

import java.util.ArrayList;
import java.util.List;

public class EmploymentAgency implements Observable {
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(JobPost job) {
        observers.forEach(o -> o.onJobPosted(job));
    }

    public void addJob(JobPost jobPosting) {
        notifyObservers(jobPosting);
    }
}
