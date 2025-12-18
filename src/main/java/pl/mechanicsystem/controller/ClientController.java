package pl.mechanicsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mechanicsystem.dto.ApiResponse;
import pl.mechanicsystem.dto.clients.ClientResponse;
import pl.mechanicsystem.dto.clients.ClientsResponse;
import pl.mechanicsystem.service.ClientService;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public ResponseEntity<ApiResponse<ClientsResponse>> getClients(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(required = false) String search
    ) {
        ClientsResponse response = clientService.getClients(page, size, search);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ApiResponse<ClientResponse>> getClient(@PathVariable Integer id) {
        ClientResponse response = clientService.getClientByid(id);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
