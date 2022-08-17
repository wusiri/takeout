package work.wlong.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import work.wlong.takeout.entity.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
