package vn.kltn.KLTN.service;

import java.util.List;
import vn.kltn.KLTN.entity.InventoryHistory;

public interface InventoryHistoryService {
    void save(InventoryHistory history);
    List<InventoryHistory> getAll();
}
