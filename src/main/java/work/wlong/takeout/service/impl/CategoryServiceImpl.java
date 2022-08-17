package work.wlong.takeout.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.wlong.takeout.common.CustomException;
import work.wlong.takeout.entity.Category;
import work.wlong.takeout.entity.Dish;
import work.wlong.takeout.entity.Setmeal;
import work.wlong.takeout.mapper.CategoryMapper;
import work.wlong.takeout.service.CategoryService;
import work.wlong.takeout.service.DishService;
import work.wlong.takeout.service.SetmealService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    //菜品
    @Autowired
    private DishService dishService;
    //套餐
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除之前需要判断
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id查询,等值查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        //聚合查询
        long count = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if (count > 0) {
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        long count1 = setmealService.count();
        if (count1 > 0) {
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除
        super.removeById(id);
    }
}
