package work.wlong.takeout.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.wlong.takeout.entity.User;
import work.wlong.takeout.mapper.UserMapper;
import work.wlong.takeout.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
