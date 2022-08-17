package work.wlong.takeout.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import work.wlong.takeout.entity.SetmealDish;
import work.wlong.takeout.mapper.SetmealDishMapper;
import work.wlong.takeout.service.SetmealDishService;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
