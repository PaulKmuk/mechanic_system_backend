package pl.mechanicsystem.service;

import org.springframework.stereotype.Service;
import pl.mechanicsystem.dto.clients.ClientResponse;
import pl.mechanicsystem.dto.clients.ClientsResponse;

@Service
public interface ClientService {

    ClientsResponse getClients(int page, int size, String search);

    ClientResponse getClientByid(Integer clntid);
}
