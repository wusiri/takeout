package work.wlong.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import work.wlong.takeout.entity.Orders;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
