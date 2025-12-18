package pl.mechanicsystem.dto.common;

public class PaginationDTO {

    private int page;
    private int size;
    private long totalElements;
    private int totalPAges;

    public PaginationDTO(int page, int size, long totalElements, int totalPAges) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPAges = totalPAges;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPAges() {
        return totalPAges;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPAges(int totalPAges) {
        this.totalPAges = totalPAges;
    }
}
