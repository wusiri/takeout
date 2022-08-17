package work.wlong.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import work.wlong.takeout.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
