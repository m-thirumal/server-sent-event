/**
 * 
 */
package in.thirumal.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import in.thirumal.service.LogsSseService;

/**
 * @author Thirumal
 *
 */
@RestController
public class LogController {
	
	LogsSseService logsSseService;
	
	public LogController(LogsSseService logsSseService) {
		super();
		this.logsSseService = logsSseService;
	}


	@GetMapping(path = "/logs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter streamLog() {
		return logsSseService.newSseEmitter();
	}

	
	@GetMapping(path = "/generate-log")
	public int generateLog() {
		return logsSseService.generateLog();
	}

}
