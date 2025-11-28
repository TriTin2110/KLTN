package vn.kltn.KLTN.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.kltn.KLTN.entity.Material;

public interface MaterialService {
	List<Material> getAllMaterials();
    Material getMaterialById(Long id);
    Material saveMaterial(Material material);
    void deleteMaterial(Long id);

}
