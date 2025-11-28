package vn.kltn.KLTN.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.kltn.KLTN.entity.Material;
import vn.kltn.KLTN.service.MaterialImportService;
import vn.kltn.KLTN.service.MaterialService;

@Controller
@RequestMapping("/admin/inventory")
public class InventoryController {
	@Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialImportService importService;
    
    @GetMapping
    public String showInventoryPage(Model model) {
        model.addAttribute("materials", materialService.getAllMaterials());
        return "admin/inventory/list";
    }

    @PostMapping("/import")
    public String importMaterial(
            @RequestParam Long materialId,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes
    ) {
        importService.importMaterial(materialId, quantity);
        redirectAttributes.addFlashAttribute("success", "Nhập kho thành công!");
        return "redirect:/admin/inventory";
    }
    
    @PostMapping("/import-multiple")
    public String importMultiple(
            @RequestParam("materialId") List<Long> materialIds,
            @RequestParam("quantity") List<Integer> quantities,
            RedirectAttributes redirectAttributes
    ) {
        for (int i = 0; i < materialIds.size(); i++) {
            importService.importMaterial(materialIds.get(i), quantities.get(i));
        }

        redirectAttributes.addFlashAttribute("success", "Nhập kho nhiều nguyên vật liệu thành công!");
        return "redirect:/admin/inventory";
    }

    
    @PostMapping("/update/{id}")
    public String updateStock(@PathVariable Long id, @RequestParam Integer quantity, RedirectAttributes redirectAttributes) {
        Material material = materialService.getMaterialById(id);
        material.setQuantity(quantity);
        materialService.saveMaterial(material);
        redirectAttributes.addFlashAttribute("success", "Cập nhật số lượng thành công!");
        return "redirect:/admin/inventory";
    }
}
