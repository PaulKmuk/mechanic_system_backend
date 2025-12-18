package pl.mechanicsystem.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.mechanicsystem.dto.clients.ClientResponse;
import pl.mechanicsystem.dto.clients.ClientsResponse;
import pl.mechanicsystem.dto.clients.dto.ClientCarDTO;
import pl.mechanicsystem.dto.clients.dto.ClientsListItemDTO;
import pl.mechanicsystem.dto.common.PaginationDTO;
import pl.mechanicsystem.entity.MsClient;
import pl.mechanicsystem.exception.ClientNotFoundException;
import pl.mechanicsystem.repository.ClientCarRepository;
import pl.mechanicsystem.repository.MsClientRepository;
import pl.mechanicsystem.service.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final MsClientRepository msClientRepository;
    private final ClientCarRepository clientCarRepository;

    public ClientServiceImpl(MsClientRepository msClientRepository, ClientCarRepository clientCarRepository) {
        this.msClientRepository = msClientRepository;
        this.clientCarRepository = clientCarRepository;
    }

    @Override
    public ClientsResponse getClients(int page, int size, String search) {

        // zabezpieczenie danych wejsciowych
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size,1),100);

        Pageable pageable = PageRequest.of(
                safePage,
                safeSize,
                Sort.by(Sort.Direction.DESC, "insstmp")
        );

        Page<MsClient> result = msClientRepository.findActiveClients(search, pageable);

        List<ClientsListItemDTO> clients = result.getContent().stream()
                .map(c -> new ClientsListItemDTO(
                      c.getGid(),
                      c.getClnam1(),
                      c.getClnam2(),
                      c.getPhone(),
                      c.getEmail(),
                      c.getInsstmp()
                )).toList();

        PaginationDTO paginationDTO = new PaginationDTO(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );

        return new ClientsResponse(paginationDTO, clients);
    }

    @Override
    public ClientResponse getClientByid(Integer clntid) {
        Optional<MsClient> msClient = msClientRepository.findActiveById(clntid);

        if (msClient.isEmpty()) {
            throw new ClientNotFoundException(
                "Nie znaleziono klienta o ID: " + clntid,
                "CLIENT_NOT_FOUND",
                "Client not found"
            );
        }

        List<ClientCarDTO> cars = clientCarRepository.findCarsClient(clntid)
                .stream()
                .map(row -> new ClientCarDTO(
                        ((Number) row[0]).intValue(),
                        (String) row[1],
                        (String) row[2],
                        ((Number) row[3]).intValue(),
                        (String) row[4],
                        (String) row[5]
                ))
                .toList();

        return new ClientResponse(
                msClient.get().getGid(),
                msClient.get().getClnam1(),
                msClient.get().getClnam2(),
                msClient.get().getPhone(),
                msClient.get().getEmail(),
                msClient.get().getInsstmp(),
                cars
        );
    }
}
