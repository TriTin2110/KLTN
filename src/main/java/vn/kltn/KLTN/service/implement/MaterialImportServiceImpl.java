package vn.kltn.KLTN.service.implement;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kltn.KLTN.entity.Material;
import vn.kltn.KLTN.entity.MaterialImport;
import vn.kltn.KLTN.repository.MaterialImportRepository;
import vn.kltn.KLTN.repository.MaterialRepository;
import vn.kltn.KLTN.service.MaterialImportService;

@Service
public class MaterialImportServiceImpl implements MaterialImportService {
	@Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialImportRepository importRepository;

    @Override
    public void importMaterial(Long materialId, Integer quantity) {

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nguyên vật liệu"));

        Integer current = material.getQuantity();
        if (current == null) current = 0;

        material.setQuantity(current + quantity);
        materialRepository.save(material);

        // Lưu lịch sử nhập kho
        MaterialImport imp = new MaterialImport();
        imp.setMaterial(material);
        imp.setQuantity(quantity);
        imp.setDate(LocalDate.now());

        importRepository.save(imp);
    }
}
