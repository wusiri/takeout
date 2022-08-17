package work.wlong.takeout.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.wlong.takeout.entity.DishFlavor;
import work.wlong.takeout.mapper.DishFlavorMapper;
import work.wlong.takeout.service.DishFlavorService;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
