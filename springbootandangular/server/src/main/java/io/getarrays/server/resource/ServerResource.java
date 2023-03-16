package io.getarrays.server.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.Response;
import io.getarrays.server.model.Server;
import io.getarrays.server.service.IServerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/server")
public class ServerResource {
	@Autowired
	private IServerService serverService; 
	
	@GetMapping("/list")
	public ResponseEntity<?> getServers() throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		return ResponseEntity.ok(Response.builder()
				.timeStamp(LocalDateTime.now())
//				.data(Map.of("servers", serverService.list(30)))
				.data(serverService.list(30))
				.message("Server retrieved")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
		);
	}
	
	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<?> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
		Server server = serverService.ping(ipAddress);
		return ResponseEntity.ok(Response.builder()
				.timeStamp(LocalDateTime.now())
				.data(server)
				.message(server.getStatus().equals(Status.SERVER_UP) ? "Ping success" : "Ping failed")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
		);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveServer(@RequestBody @Valid Server server ) {
		server = serverService.create(server);
		return ResponseEntity.ok(Response.builder()
				.timeStamp(LocalDateTime.now())
				.data(server)
				.message("Server Created")
				.status(HttpStatus.CREATED)
				.statusCode(HttpStatus.CREATED.value())
				.build()
		);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getServer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(Response.builder()
				.timeStamp(LocalDateTime.now())
				.data(serverService.get(id))
				.message("Server retrieved")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
		);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteServer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(Response.builder()
				.timeStamp(LocalDateTime.now())
				.data(serverService.delete(id))
				.message("Server deleted")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
		);
	}
	
	@GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
		return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/" + fileName));
	}
}
