package com.cyl.transaction.service.impl;

import com.cyl.transaction.entity.Commodity;
import com.cyl.transaction.repository.CommodityRepository;
import com.cyl.transaction.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PuechaseImpl implements PurchaseService {
    @Autowired
    private CommodityRepository commodityRepository;

    @Override
    @Transactional
    public void doPurchase(int commodityId, int purchaseAmount) {
        Commodity commodity = commodityRepository.findById(commodityId);
        int stock = commodity.getStock();
        int stockAfterPurshase = stock - purchaseAmount;
        commodity.setStock(stockAfterPurshase);
        commodityRepository.save(commodity);
        throw new RuntimeException();
    }
}
