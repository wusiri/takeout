package work.wlong.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import work.wlong.takeout.entity.AddressBook;
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
