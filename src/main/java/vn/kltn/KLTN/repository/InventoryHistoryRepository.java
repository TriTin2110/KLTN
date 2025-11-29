package vn.kltn.KLTN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.kltn.KLTN.entity.InventoryHistory;

public interface InventoryHistoryRepository extends JpaRepository<InventoryHistory, Long> { 
	
}
