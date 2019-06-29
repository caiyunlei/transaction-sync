package com.cyl.transaction.controller;

import com.cyl.transaction.entity.Commodity;
import com.cyl.transaction.service.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketController {
    private final int commodityId = 0;
    private final int perchaseAmount = 1;
    private final Object lock = new Object();

    @Autowired
    private CommodityRepository commodityRepository;

    @ResponseBody
    @RequestMapping(path = "/v0/perchase", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public String perchaseOne() throws Exception {
        doPerchase();
        return "ok!";
    }

    @ResponseBody
    @RequestMapping(path = "/v1/perchase", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public String perchaseOneSyncInnerTransactional() throws Exception {
        synchronized (lock) {
            doPerchase();
        }
        return "ok!";
    }

    @ResponseBody
    @RequestMapping(path = "/v2/perchase", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
    public String perchaseOneRepeatableRead() throws Exception {
        doPerchase();
        return "ok!";
    }

    @ResponseBody
    @RequestMapping(path = "/v3/perchase", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.SERIALIZABLE)
    public String perchaseOneSerializableIsolation() throws Exception {
        doPerchase();
        return "ok!";
    }

    private void doPerchase() {
        Commodity commodity = commodityRepository.findById(commodityId);
        int stock = commodity.getStock();
        int stockAfterPurshase = stock - perchaseAmount;
        commodity.setStock(stockAfterPurshase);
        commodityRepository.save(commodity);
    }

    @ResponseBody
    @RequestMapping(path = "/v4/perchase", method = RequestMethod.POST)
    public String perchaseOneOnlyUseSync() throws Exception {
        synchronized (lock) {
            doPerchase();
        }
        return "ok!";
    }

    @ResponseBody
    @RequestMapping(path = "/v5/perchase", method = RequestMethod.POST)
    public String perchaseOneSyncOuterTransactional() throws Exception {
        synchronized (lock) {
            perchaseByTransaction();
        }
        return "ok!";
    }

    @Transactional(rollbackFor = Exception.class)
    protected void perchaseByTransaction() {
        doPerchase();
    }
}
