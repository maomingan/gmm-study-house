package com.gmm.mybatis_generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmm.mybatis_generator.dao.ClientDao;
import com.gmm.mybatis_generator.entity.Client;
import com.gmm.mybatis_generator.service.ClientService;
import org.springframework.stereotype.Service;

/**
 * (Client)表服务实现类
 *
 * @author makejava
 * @since 2024-11-13 15:33:32
 */
@Service("clientService")
public class ClientServiceImpl extends ServiceImpl<ClientDao, Client> implements ClientService {

}

