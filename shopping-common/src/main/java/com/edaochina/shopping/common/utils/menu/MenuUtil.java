package com.edaochina.shopping.common.utils.menu;

import com.edaochina.shopping.domain.vo.user.MenuChildrenVO;
import com.edaochina.shopping.domain.vo.user.MenuVO;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author Jason
 * @since 2018-11
 */
@UtilityClass
public class MenuUtil {

    /**
     * 获取菜单目录
     *
     * @param menuVOList 所有的菜单
     * @return 菜单层级目录
     */
    public List<MenuVO> getMenuVOList(List<MenuVO> menuVOList) {
        List<MenuVO> menuVOResultList = new ArrayList<>();
        for (MenuVO vo : menuVOList) {
            if ("-1".equals(vo.getParentId())) {
                vo.setChildren(listMenuChildren(menuVOList, vo));
                menuVOResultList.add(vo);
            }
        }
        return menuVOResultList;
    }

    /**
     * 获取菜单子目录
     *
     * @param menus  菜单数组
     * @param menuVO 父菜单视图实体
     * @return 子菜单数组
     */
    private List<MenuChildrenVO> listMenuChildren(List<MenuVO> menus, MenuVO menuVO) {
        List<MenuChildrenVO> menuChildren = new ArrayList<>();
        // 遍历菜单
        for (MenuVO singleMenu : menus) {
            // 匹配父菜单下的子菜单
            if (singleMenu.getParentId().equals(menuVO.getId())) {
                MenuChildrenVO menuChild = new MenuChildrenVO();
                BeanUtils.copyProperties(singleMenu, menuChild);
                menuChildren.add(menuChild);
            }
        }
        return menuChildren;
    }
}
