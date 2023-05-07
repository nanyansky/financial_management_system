package com.nanyan.entity.Menus;

import com.nanyan.entity.Menus.MenuKey;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "system_menu")
public class MenuEntity implements Serializable {
    // 复合主键要用这个注解
    @EmbeddedId
    private MenuKey key;
    private Long pid;
    private String icon;
    private String target;
    private Integer sort;
    private Boolean status;
    private String remark;
    @Column(name = "is_admin")
    private int isAdmin;
    @CreatedDate
    private Date create_at;
    @CreatedDate
    private Date update_at;
    private Date delete_at;
}


