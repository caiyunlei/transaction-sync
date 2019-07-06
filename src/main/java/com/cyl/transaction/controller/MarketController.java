package com.cyl.transaction.controller;

import com.cyl.transaction.entity.Commodity;
import com.cyl.transaction.repository.CommodityRepository;
import com.cyl.transaction.service.PurchaseService;
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
    private final int purchaseAmount = 1;
    private final Object lock = new Object();

    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private PurchaseService purchaseService;

    @ResponseBody
    @RequestMapping(path = "/v0/purchase", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public String purchaseOne() throws Exception {
        doPurchase();
        return "ok!";
    }

    @ResponseBody
    @RequestMapping(path = "/v1/purchase", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public String purchaseOneSyncInnerTransactional() throws Exception {
        synchronized (lock) {
            doPurchase();
        }
        return "ok!";
    }

    @ResponseBody
    @RequestMapping(path = "/v2/purchase", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
    public String purchaseOneRepeatableRead() throws Exception {
        doPurchase();
        return "ok!";
    }

    @ResponseBody
    @RequestMapping(path = "/v3/purchase", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.SERIALIZABLE)
    public String purchaseOneSerializableIsolation() throws Exception {
        doPurchase();
        return "ok!";
    }

    @ResponseBody
    @RequestMapping(path = "/v4/purchase", method = RequestMethod.POST)
    public String purchaseOneOnlyUseSync() throws Exception {
        synchronized (lock) {
            doPurchase();
        }
        return "ok!";
    }

    @ResponseBody
    @RequestMapping(path = "/v5/purchase", method = RequestMethod.POST)
    public String purchaseOneSyncOuterTransactional() throws Exception {
        synchronized (lock) {
            purchaseByTransaction();
        }
        return "ok!";
    }

    @ResponseBody
    @RequestMapping(path = "/v6/purchase", method = RequestMethod.POST)
    public String purchaseOneSyncAvilableTransactional() throws Exception {
        synchronized (lock) {
            purchaseService.doPurchase(commodityId,purchaseAmount);
        }
        return "ok!";
    }

    @Transactional(rollbackFor = Exception.class)
    public void purchaseByTransaction() {
        doPurchase();
    }

    private void doPurchase() {
        Commodity commodity = commodityRepository.findById(commodityId);
        int stock = commodity.getStock();
        int stockAfterPurshase = stock - purchaseAmount;
        commodity.setStock(stockAfterPurshase);
        commodityRepository.save(commodity);
        throw new RuntimeException();
    }
}
