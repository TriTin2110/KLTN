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
import vn.kltn.KLTN.entity.InventoryHistory;
import vn.kltn.KLTN.service.InventoryHistoryService;
import vn.kltn.KLTN.service.MaterialImportService;
import vn.kltn.KLTN.service.MaterialService;

@Controller
@RequestMapping("/admin/inventory")
public class InventoryController {

    @Autowired
    private InventoryHistoryService historyService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialImportService importService;

    // ------------------ TRANG CHÍNH ------------------
    @GetMapping
    public String showInventoryPage(Model model) {
        model.addAttribute("materials", materialService.getAllMaterials());
        return "admin/inventory/list";
    }

    // ------------------ NHẬP MỘT NGUYÊN VẬT LIỆU ------------------
    @PostMapping("/import")
    public String importMaterial(
            @RequestParam Long materialId,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes
    ) {
        importService.importMaterial(materialId, quantity);

        Material m = materialService.getMaterialById(materialId);
        historyService.save(new InventoryHistory(m, "IMPORT", quantity));

        redirectAttributes.addFlashAttribute("success", "Nhập kho thành công!");
        return "redirect:/admin/inventory";
    }

    // ------------------ NHẬP NHIỀU NGUYÊN VẬT LIỆU ------------------
    @PostMapping("/import-multiple")
    public String importMultiple(
            @RequestParam("materialId") List<Long> materialIds,
            @RequestParam("quantity") List<Integer> quantities,
            RedirectAttributes redirectAttributes
    ) {
        for (int i = 0; i < materialIds.size(); i++) {
            importService.importMaterial(materialIds.get(i), quantities.get(i));

            Material m = materialService.getMaterialById(materialIds.get(i));
            historyService.save(new InventoryHistory(m, "IMPORT_MULTI", quantities.get(i)));
        }

        redirectAttributes.addFlashAttribute("success", "Nhập kho nhiều nguyên vật liệu thành công!");
        return "redirect:/admin/inventory";
    }

    // ------------------ CẬP NHẬT SỐ LƯỢNG ------------------
    @PostMapping("/update/{id}")
    public String updateStock(
            @PathVariable Long id,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes
    ) {
        Material material = materialService.getMaterialById(id);
        int oldQty = material.getQuantity() != null ? material.getQuantity() : 0;

        material.setQuantity(quantity);
        materialService.saveMaterial(material);

        int change = quantity - oldQty;
        historyService.save(new InventoryHistory(material, "UPDATE", change));

        redirectAttributes.addFlashAttribute("success", "Cập nhật số lượng thành công!");
        return "redirect:/admin/inventory";
    }

    // ------------------ HIỂN THỊ LỊCH SỬ KIỂM KÊ ------------------
    @GetMapping("/history")
    public String showHistory(Model model) {
        model.addAttribute("histories", historyService.getAll());
        model.addAttribute("materials", materialService.getAllMaterials());
        model.addAttribute("materialCount", materialService.getAllMaterials().size());
        model.addAttribute("totalChange", historyService.getAll()
                .stream()
                .mapToInt(h -> Math.abs(h.getQuantityChange()))
                .sum());
        return "admin/inventory/history";
    }

    // ------------------ THÊM LỊCH SỬ KIỂM KÊ THỦ CÔNG ------------------
    @PostMapping("/history/add")
    public String addHistory(
            @RequestParam Long materialId,
            @RequestParam String action,
            @RequestParam Integer quantityChange,
            RedirectAttributes redirectAttributes
    ) {
        Material m = materialService.getMaterialById(materialId);
        historyService.save(new InventoryHistory(m, action, quantityChange));
        redirectAttributes.addFlashAttribute("success", "Đã thêm bản ghi kiểm kê!");
        return "redirect:/admin/inventory/history";
    }
}
