/**
 * 
 */
package in.thirumal.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author Thirumal
 *
 */
@Service
public class LogsSseService {

	private static final Logger log = LoggerFactory.getLogger(LogsSseService.class);

    private static final String TOPIC = "logs";
    private final SseTemplate template;
    private static final AtomicLong COUNTER = new AtomicLong(0);

    public LogsSseService(SseTemplate template, MonitoringFileService monitoringFileService) {
        this.template = template;
        monitoringFileService.listen(file -> {

            try {
                Files.lines(file)
                        .skip(COUNTER.get())
                        .forEach(line ->
                                template.broadcast(TOPIC, SseEmitter.event()
                                        .id(String.valueOf(COUNTER.incrementAndGet()))
                                        .data(line)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public SseEmitter newSseEmitter() {
        return template.newSseEmitter(TOPIC);
    }

	public int generateLog() {
		int i = 0;
		for (;i < 5; i++) {
			log.info("ha {}", i);
		}
		return i;
	}
    
    

}
