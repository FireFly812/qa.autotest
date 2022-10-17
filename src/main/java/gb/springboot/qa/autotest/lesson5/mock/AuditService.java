package gb.springboot.qa.autotest.lesson5.mock;

public class AuditService {

    public boolean sendAudit(Object o) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("Object " + o + " has been sent to audit");
        return true;
    }
}
