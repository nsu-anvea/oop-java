package tests.patterns.observer.employee_and_employer;

public interface Observable {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(JobPost job);
}
