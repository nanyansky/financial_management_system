package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.dao.InitMenusDao;
import com.nanyan.entity.Menus.MenuEntity;
import com.nanyan.entity.Menus.MenuVo;
import com.nanyan.service.InitMenusService;
import com.nanyan.utils.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/5 12:55
 */
@Service
public class InitMenusServiceImpl implements InitMenusService {


    @Resource
    private InitMenusDao initMenusDao;

    @Override
    public JSONObject menu() {
        Map<String, Object> map = new HashMap<>(16);
        Map<String,Object> home = new HashMap<>(16);
        Map<String,Object> logo = new HashMap<>(16);
        List<MenuEntity> menuList = initMenusDao.findAllByStatusOrderBySort(true);
        List<MenuVo> menuInfo = new ArrayList<>();
        for (MenuEntity e : menuList) {
            MenuVo menuVO = new MenuVo();
            menuVO.setId(e.getKey().getId());
            menuVO.setPid(e.getPid());
            menuVO.setHref(e.getKey().getHref());
            menuVO.setTitle(e.getKey().getTitle());
            menuVO.setIcon(e.getIcon());
            menuVO.setTarget(e.getTarget());
            menuInfo.add(menuVO);
        }
        map.put("menuInfo", TreeUtil.toTree(menuInfo, 0L));
        home.put("title","首页");
        home.put("href","/index.jsp");//控制器路由,自行定义
        logo.put("title","家庭理财系统");
        logo.put("href","/index.jsp");
        logo.put("image","statics/layui/images/logo.png");//静态资源文件路径,可使用默认的logo.png
        map.put("homeInfo",home);
        map.put("logoInfo", logo);
        return new JSONObject(map);
    }
}
