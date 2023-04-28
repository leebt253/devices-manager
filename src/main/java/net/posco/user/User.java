package net.posco.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import net.posco.history.History;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 128, nullable = false, unique = true)
  private String email;

  @Column(length = 128, nullable = false)
  private String firstName;

  @Column(length = 128, nullable = false)
  private String lastName;

  @Column(length = 64, nullable = false)
  private String password;

  @Column(length = 10, nullable = false)
  private String role;

  @Column(length = 255, nullable = true)
  private String picture;

  private boolean enabled;

  @Column(columnDefinition = "DateTime", nullable = false)
  private LocalDateTime dateCreated;

  @Column(columnDefinition = "DateTime", nullable = false)
  private LocalDateTime dateModified;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<History> histories;

  public User() {
  }

  public User(String email,
      String firstName,
      String lastName,
      String password,
      String role,
      boolean enabled,
      LocalDateTime dateCreated,
      LocalDateTime dateModified) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.role = role;
    this.enabled = enabled;
    this.dateCreated = dateCreated;
    this.dateModified = dateModified;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public List<History> getHistories() {
    return histories;
  }

  public void setHistories(List<History> histories) {
    this.histories = histories;
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

  @Transient
  public String getPhotoImagePath() {
    if (id == null || picture == null) {
      return "/img/profile-picture/default/default.jpg";
    }
    return ("/upload/picture/user/profile-picture/" + this.id + "/" + this.picture);
  }

  @Transient
  public String getFullName() {
    return lastName + " " + firstName;
  }
}
