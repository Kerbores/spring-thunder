package club.zhcs.thunder.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
@Component
public class TestTask {

	@Scheduled(cron = "*/5 * * * * ? ")
	public void run() {
		System.err.println("running....");
	}
}
