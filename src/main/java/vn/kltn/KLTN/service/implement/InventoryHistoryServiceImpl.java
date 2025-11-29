package vn.kltn.KLTN.service.implement;

import org.springframework.stereotype.Service;
import vn.kltn.KLTN.entity.InventoryHistory;
import vn.kltn.KLTN.repository.InventoryHistoryRepository;
import vn.kltn.KLTN.service.InventoryHistoryService;

import java.util.List;

@Service
public class InventoryHistoryServiceImpl implements InventoryHistoryService {
    private final InventoryHistoryRepository repo;

    public InventoryHistoryServiceImpl(InventoryHistoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(InventoryHistory history) {
        repo.save(history);
    }

    @Override
    public List<InventoryHistory> getAll() {
        return repo.findAll();
    }
}
