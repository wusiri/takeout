package work.wlong.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.wlong.takeout.dto.SetmealDto;
import work.wlong.takeout.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param list
     */
    void removeWithDish(List<Long> list);

    void updateWithDish(SetmealDto setmealDto);

   SetmealDto getByIdWithDish(Long id);
}
