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
    @Column(name = Defenitions.FIELDS.ID)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @DateTimeFormat(pattern=Defenitions.DATE_FORMAT)
    @Column(name = Defenitions.FIELDS.CREATE_ON, nullable = false)
    private Date createOn;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.CREATE_BY, referencedColumnName = Defenitions.FIELDS.ID)
    private User createBy;

    @DateTimeFormat(pattern=Defenitions.DATE_FORMAT)
    @Column(name = Defenitions.FIELDS.MODIFY_ON, nullable = false)
    private Date modifyOn;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.MODIFY_BY, referencedColumnName = Defenitions.FIELDS.ID)
    private User modifyBy;

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

}
