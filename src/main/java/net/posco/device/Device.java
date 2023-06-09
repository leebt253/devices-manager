package net.posco.device;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import net.posco.history.History;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 4, nullable = false)
    private String itemGroup;

    @Column(length = 32, nullable = false)
    private String itemCode;

    @Column(length = 128, nullable = false)
    private String deviceName;

    @Column(length = 128, nullable = false)
    private String deviceModel;

    @Column(length = 128, nullable = false)
    private String serialNumber;

    @Column(length = 128, nullable = false)
    private String department;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer min;

    @Column(nullable = false)
    private Integer max;

    @Column(length = 255, nullable = false)
    private String remark;

    @Column(columnDefinition = "DateTime", nullable = false)
    private LocalDateTime dateCreated;

    @Column(columnDefinition = "DateTime", nullable = false)
    private LocalDateTime dateModified;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private List<History> histories;

    public Device() {
    }

    public Device(String itemGroup, String itemCode, String deviceName, String deviceModel, String serialNumber,
            String department, Integer quantity, Integer min, Integer max, String remark, LocalDateTime dateCreated,
            LocalDateTime dateModified) {
        this.itemGroup = itemGroup;
        this.itemCode = itemCode;
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.serialNumber = serialNumber;
        this.department = department;
        this.quantity = quantity;
        this.min = min;
        this.max = max;
        this.remark = remark;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

}
