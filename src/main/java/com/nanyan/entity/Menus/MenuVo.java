package com.nanyan.entity.Menus;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuVo {
    private Long id;

    private Long pid;

    private String title;

    private String icon;

    private String href;

    private String target;

    private List<MenuVo> child;

}
