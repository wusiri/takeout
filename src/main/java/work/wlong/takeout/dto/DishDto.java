package work.wlong.takeout.dto;


import lombok.Data;
import work.wlong.takeout.entity.Dish;
import work.wlong.takeout.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {
    //菜品对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
