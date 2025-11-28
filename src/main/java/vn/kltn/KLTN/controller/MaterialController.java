package vn.kltn.KLTN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.kltn.KLTN.entity.Material;
import vn.kltn.KLTN.entity.Supplier;
import vn.kltn.KLTN.service.MaterialService;
import vn.kltn.KLTN.service.SupplierService;

@Controller
@RequestMapping("/admin/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String listMaterials(Model model) {
        model.addAttribute("materials", materialService.getAllMaterials());
        return "admin/materials/list";
    }

    @GetMapping("/new")
    public String createMaterialForm(Model model) {
        model.addAttribute("material", new Material());
        model.addAttribute("suppliers", supplierService.findAll());
        return "admin/materials/form";
    }

    @PostMapping
    public String saveMaterial(
            @ModelAttribute("material") Material material,
            @RequestParam(required = false) String newSupplierName,
            @RequestParam(required = false) String newSupplierAddress,
            @RequestParam(required = false) String newSupplierPhone
    ) {

        Supplier supplier = null;

        // Nếu người dùng chọn "Thêm nhà cung cấp mới"
        if (material.getSupplier() != null 
            && "__new__".equals(material.getSupplier().getName())) {

            supplier = new Supplier();
            supplier.setName(newSupplierName);
            supplier.setAddress(newSupplierAddress);
            supplier.setPhoneNumber(newSupplierPhone);

            supplierService.add(supplier);

        } else {
            // Chọn NCC cũ
            supplier = supplierService.findById(material.getSupplier().getName());
        }

        material.setSupplier(supplier);
        materialService.saveMaterial(material);

        return "redirect:/admin/materials";
    }


    @GetMapping("/edit/{id}")
    public String editMaterial(@PathVariable Long id, Model model) {
        model.addAttribute("material", materialService.getMaterialById(id));
        model.addAttribute("suppliers", supplierService.findAll());
        return "admin/materials/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return "redirect:/admin/materials";
    }
    

}
