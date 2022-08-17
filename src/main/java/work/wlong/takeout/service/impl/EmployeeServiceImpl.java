package work.wlong.takeout.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.wlong.takeout.entity.Employee;
import work.wlong.takeout.mapper.EmployeeMapper;
import work.wlong.takeout.service.EmployeeService;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
