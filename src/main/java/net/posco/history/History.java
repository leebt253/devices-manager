package net.posco.history;

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

    @Column(length = 32, nullable = false)
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    public History() {

    }

    public History(User user, Device device, Integer inQuantity, Integer outQuantity, String date) {
        this.user = user;
        this.device = device;
        this.inQuantity = inQuantity;
        this.outQuantity = outQuantity;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
