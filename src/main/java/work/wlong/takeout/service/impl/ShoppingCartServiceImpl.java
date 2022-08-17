package work.wlong.takeout.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.wlong.takeout.entity.ShoppingCart;
import work.wlong.takeout.mapper.ShoppingCartMapper;
import work.wlong.takeout.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
