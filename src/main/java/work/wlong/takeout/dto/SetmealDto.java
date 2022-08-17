package work.wlong.takeout.dto;


import lombok.Data;
import work.wlong.takeout.entity.Setmeal;
import work.wlong.takeout.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
