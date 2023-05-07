package com.nanyan.entity.Menus;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class MenuKey implements Serializable {
    private Long id;
    private String title;
    private String href;
}
