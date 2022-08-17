package work.wlong.takeout.service;


import com.baomidou.mybatisplus.extension.service.IService;
import work.wlong.takeout.entity.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
