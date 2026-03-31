import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        scheduler.scheduleAtFixedRate(() -> System.out.println(1), 1, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() -> System.out.println(2), 2, 2, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() -> System.out.println(3), 3, 3, TimeUnit.SECONDS);
    }
}