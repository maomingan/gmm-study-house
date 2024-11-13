package com.gmm.mybatis_generator.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gmm.mybatis_generator.ApiController;
import com.gmm.mybatis_generator.R;
import com.gmm.mybatis_generator.entity.Client;
import com.gmm.mybatis_generator.service.ClientService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Client)表控制层
 *
 * @author makejava
 * @since 2024-11-13 15:33:32
 */
@RestController
@RequestMapping("client")
public class ClientController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ClientService clientService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param client 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Client> page, Client client) {
        return success(this.clientService.page(page, new QueryWrapper<>(client)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.clientService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param client 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Client client) {
        return success(this.clientService.save(client));
    }

    /**
     * 修改数据
     *
     * @param client 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Client client) {
        return success(this.clientService.updateById(client));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.clientService.removeByIds(idList));
    }
}

