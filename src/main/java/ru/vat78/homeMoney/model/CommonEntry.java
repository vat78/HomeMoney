package ru.vat78.homeMoney.model;

import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CommonEntry implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(name = "created_on", nullable = false)
    private Date createOn;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="created_by", referencedColumnName = "id")
    private User createBy;

    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(name = "modified_on", nullable = false)
    private Date modifyOn;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="modified_by", referencedColumnName = "id")
    private User modifyBy;

    @NotNull
    @Column(name = "object_type")
    private int type;

    public Long getId() {
        return id;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public User getCreateBy() {
        return createBy;
    }

    public Date getModifyOn() {
        return modifyOn;
    }

    public User getModifyBy() {
        return modifyBy;
    }

    public int getType() {
        return type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public void setModifyOn(Date modifyOn) {
        this.modifyOn = modifyOn;
    }

    public void setModifyBy(User modifyBy) {
        this.modifyBy = modifyBy;
    }

    public void setType(int type) {
        this.type = type;
    }
}
