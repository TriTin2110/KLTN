package vn.kltn.KLTN.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.kltn.KLTN.entity.Material;
import vn.kltn.KLTN.repository.MaterialRepository;
import vn.kltn.KLTN.service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService {
	private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    @Override
    public Material getMaterialById(Long id) {
        return materialRepository.findById(id).orElse(null);
    }

    @Override
    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }

}
