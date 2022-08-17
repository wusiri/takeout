package work.wlong.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import work.wlong.takeout.common.R;
import work.wlong.takeout.entity.Employee;
import work.wlong.takeout.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request  请求信息
     * @param employee 请求信息封装对象
     * @return 返回信息
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //将页面提交的密码进行md5加密
        //获取密码
        String password = employee.getPassword();
        //md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据页面提交的username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //实体类属性，返回数据
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        //查询
        Employee emp = employeeService.getOne(queryWrapper);
        //查询不到，登录失败
        if (emp == null) {
            return R.error("登录失败，未找到该用户");
        }
        //密码比对，不一致登录失败
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败，密码错误");
        }
        //查看员工状态，已禁用登录失败
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }
        //登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request 请求信息
     * @return 返回信息
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前登录员工的id
        //从HttpServletRequest对象中获取，然后清除
        request.getSession().removeAttribute("employee");
        //返回信息
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee 新增员工信息
     * @return 返回信息
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工,员工信息:{}", employee.toString());
        //设置初始密码，进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        ////设置创建时间
        //employee.setCreateTime(LocalDateTime.now());
        ////设置更新时间
        //employee.setUpdateTime(LocalDateTime.now());
        ////获取当前登录用户id
        //Long empId = (Long) request.getSession().getAttribute("employee");
        ////设置创建操作人员
        //employee.setCreateUser(empId);
        ////设置更新操作人员
        //employee.setUpdateUser(empId);
        //增加到数据库
        employeeService.save(employee);
        return R.success("新增员工成功");
    }


    /**
     * 员工信息分页查询
     *
     * @param page     当前页面
     * @param pageSize 目标页码
     * @param name     搜索名称（过滤标准）
     * @return 页面信息
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);
        //构造分页构造器
        Page pageInfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     *
     * @param employee 请求信息
     * @return 返回信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        //获取线程id
        long id = Thread.currentThread().getId();
        log.info("线程id为：{}", id);
        //Long empId = (Long)request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empId);
        //更新
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询员工信息
     *
     * @param id 员工id
     * @return 返回信息
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息...");
        //查询
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }
}
