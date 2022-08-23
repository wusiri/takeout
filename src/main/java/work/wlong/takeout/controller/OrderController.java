package work.wlong.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import work.wlong.takeout.common.BaseContext;
import work.wlong.takeout.common.R;
import work.wlong.takeout.dto.OrdersDto;
import work.wlong.takeout.entity.*;
import work.wlong.takeout.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ShoppingCartService shoppingCartService;


    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> pageR(int page,int pageSize){
        Page<OrdersDto> pageDto = orderService.pageDto(page,pageSize);
        return R.success(pageDto);
    }


    /**
     * 再买一单
     * @param map
     * @return
     */
    @PostMapping("/again")
    public R<String> againSubmit(@RequestBody Map<String,String> map){
        String ids = map.get("id");

        long id = Long.parseLong(ids);

        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,id);
        //获取该订单对应的所有的订单明细表
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);

        //通过用户id把原来的购物车给清空
        shoppingCartService.clean();

        //获取用户id
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map((item) -> {
            //把从order表中和order_details表中获取到的数据赋值给这个购物车对象
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart.setImage(item.getImage());
            Long dishId = item.getDishId();
            Long setmealId = item.getSetmealId();
            if (dishId != null) {
                //如果是菜品那就添加菜品的查询条件
                shoppingCart.setDishId(dishId);
            } else {
                //添加到购物车的是套餐
                shoppingCart.setSetmealId(setmealId);
            }
            shoppingCart.setName(item.getName());
            shoppingCart.setDishFlavor(item.getDishFlavor());
            shoppingCart.setNumber(item.getNumber());
            shoppingCart.setAmount(item.getAmount());
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());

        //把携带数据的购物车批量插入购物车表  这个批量保存的方法要使用熟练！！！
        shoppingCartService.saveBatch(shoppingCartList);

        return R.success("操作成功");
    }


    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param number
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String number){
        //分页构造器对象
        Page<Orders> ordersPage=new Page<>(page,pageSize);
        Page<OrdersDto> ordersDtoPage=new Page<>();
        //按订单号进行查询
        LambdaQueryWrapper<Orders> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(number!=null,Orders::getNumber,number);
        orderService.page(ordersPage, queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(ordersPage,ordersDtoPage,"records");
        List<Orders> records = ordersPage.getRecords();
        List<OrdersDto> list=records.stream().map(item->{
            OrdersDto ordersDto=new OrdersDto();
            //对象拷贝
            BeanUtils.copyProperties(item,ordersDto);
            //用户Id
            Long userId = item.getUserId();
            //根据用户Id查询用户
            User user = userService.getById(userId);
            if(user!=null){
                //用户名称
                String name = user.getName();
                ordersDto.setUserName(name);
            }
            return ordersDto;
        }).collect(Collectors.toList());
        ordersDtoPage.setRecords(list);
        return R.success(ordersDtoPage);
    }

    /**
     * 更新状态
     * @param orders
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Orders orders){
        LambdaUpdateWrapper<Orders> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Orders::getNumber,orders.getId());
        updateWrapper.eq(Orders::getStatus,orders.getStatus()-1).set(Orders::getStatus,orders.getStatus());
        orderService.update(updateWrapper);
        return R.success("修改成功");
    }
}