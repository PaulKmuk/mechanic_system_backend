package pl.mechanicsystem.dto.clients;

import pl.mechanicsystem.dto.clients.dto.ClientsListItemDTO;
import pl.mechanicsystem.dto.common.PaginationDTO;

import java.util.List;

public class ClientsResponse {

    private PaginationDTO pagination;
    List<ClientsListItemDTO> clients;

    public ClientsResponse(PaginationDTO pagination, List<ClientsListItemDTO> clients) {
        this.pagination = pagination;
        this.clients = clients;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

    public List<ClientsListItemDTO> getClients() {
        return clients;
    }

    public void setClients(List<ClientsListItemDTO> clients) {
        this.clients = clients;
    }
}
