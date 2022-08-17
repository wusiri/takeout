package work.wlong.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.wlong.takeout.dto.DishDto;
import work.wlong.takeout.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish,dis_flavor
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    void removeWithFlavor(List<Long> ids);
}
