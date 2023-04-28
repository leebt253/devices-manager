package net.posco.history;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import net.posco.device.Device;
import net.posco.user.User;

@Entity
@Table(name = "histories")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private Integer inQuantity;

    @Column(nullable = true)
    private Integer outQuantity;

    @Column(length = 255, nullable = true)
    private String remark;

    @Column(columnDefinition = "DateTime", nullable = false)
    private LocalDateTime dateCreated;

    @Column(columnDefinition = "DateTime", nullable = false)
    private LocalDateTime dateModified;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    public History() {
    }

    public History(Integer inQuantity, Integer outQuantity, String remark, LocalDateTime dateCreated,
            LocalDateTime dateModified, User user, Device device) {
        this.inQuantity = inQuantity;
        this.outQuantity = outQuantity;
        this.remark = remark;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.user = user;
        this.device = device;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Integer getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(Integer inQuantity) {
        this.inQuantity = inQuantity;
    }

    public Integer getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(Integer outQuantity) {
        this.outQuantity = outQuantity;
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
}
