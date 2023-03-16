package io.getarrays.server.service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.Server;
import io.getarrays.server.repository.ServerRepository;
import io.getarrays.server.service.IServerService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServerService implements IServerService{
	@Autowired
	private ServerRepository serverRepository;

	public Server create(Server server) {
		log.info("Save new server: {}", server.getName());
		server.setImageUrl(setServerImageUrl());
		return serverRepository.save(server);
	}

	private String setServerImageUrl() {
		String[] imageName = { "server1.png", "server2.png", "server3.png", "server4.png" };
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageName[new Random().nextInt(4)]).toUriString();
	}

	public Server ping(String ipAddress) throws IOException{
		log.info("Pinging server ID: {}", ipAddress);
		Server server = serverRepository.findByIpAddress(ipAddress);
		InetAddress address = InetAddress.getByName(ipAddress);
		server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
		serverRepository.save(server);
		return server;
	}

	public Collection<Server> list(int limit) {
		log.info("Fetching all server");
		return serverRepository.findAll(PageRequest.of(0, limit)).toList();
	}

	public Server get(Long id) {
		log.info("Fetching server by id: {}", id);
		return serverRepository.findById(id).get();
	}

	public Server update(Server server) {
		log.info("Updating server: {}", server.getName());
		return serverRepository.save(server);
	}

	public Boolean delete(Long id) {
		log.info("Deleting server by ID: {}", id);
		serverRepository.deleteById(id);
		return true;
	}

}
