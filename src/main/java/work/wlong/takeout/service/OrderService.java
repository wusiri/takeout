package work.wlong.takeout.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import work.wlong.takeout.dto.OrdersDto;
import work.wlong.takeout.entity.Orders;

public interface OrderService extends IService<Orders> {



    void submit(Orders orders);

    Page<OrdersDto> pageDto(int page, int pageSize);
}


