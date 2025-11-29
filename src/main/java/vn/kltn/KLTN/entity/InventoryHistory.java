package vn.kltn.KLTN.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_history")
public class InventoryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết tới Material
    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    // Hành động (IMPORT / UPDATE / EXPORT / ADJUST)
    @Column(nullable = false)
    private String action;

    // Số lượng thay đổi (âm hoặc dương)
    @Column(nullable = false)
    private int quantityChange;

    // Thời gian thực hiện
    @Column(nullable = false)
    private LocalDateTime timestamp;

    // ---------------- CONSTRUCTORS ----------------
    public InventoryHistory() {
        this.timestamp = LocalDateTime.now();
    }

    public InventoryHistory(Material material, String action, int quantityChange) {
        this.material = material;
        this.action = action;
        this.quantityChange = quantityChange;
        this.timestamp = LocalDateTime.now();
    }

    // ---------------- GETTERS / SETTERS ----------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
