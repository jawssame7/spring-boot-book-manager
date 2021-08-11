package com.wiz.bookmanager.model;

import javax.persistence.*;

@Entity
public class Book extends BaseModel {

    /**
     * id
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 名前
     */
    private String name;

    /**
     * 説明
     */
    private String description;

    /**
     * サムネイル
     */
    private String thumbnail;

    /**
     * 保管場所
     */
    @Column(name = "place_id")
    private Long placeId;

    /**
     * 使用者
     */
    @Column(name = "employee_id")
    private Long employeeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id", insertable = false, updatable = false)
    private Place place;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private Employee employee;

    /**
     * idを返す
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * idを設定する
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 名前を設定する
     * @return 名前
     */
    public String getName() {
        return name;
    }

    /**
     * 名前を返す
     * @param name 名前
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 説明を返す
     * @return 説明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 説明を設定する
     * @param description 説明
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * サムネイルを返す
     * @return サムネイル
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * サムネイルを設定する
     * @param thumbnail サムネイル
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 保管場所IDを設定する
     * @return 保管場所ID
     */
    public Long getPlaceId() {
        return placeId;
    }

    /**
     * 保管場所IDを設定する
     * @param placeId　保管場所ID
     */
    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    /**
     * 使用者を返す
     * @return 使用者
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * 使用者を設定する
     * @param employeeId　使用者
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 保管場所を返す
     * @return 保管場所
     */
    public Place getPlace() {
        return place;
    }

    /**
     * 保管場所を設定する
     * @param place 保管場所
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * 使用者を返す
     * @return 使用者
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * 使用者を設定する
     * @param employee　使用者
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
