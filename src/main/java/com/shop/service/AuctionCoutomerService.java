package com.shop.service;

import com.shop.pojo.Auction;
import com.shop.pojo.AuctionCustomer;
import com.shop.pojo.Auctionrecord;

import java.util.List;

public interface AuctionCoutomerService {

    Auction selectAuctionAndAuctionRecordList(Integer auctionId);

    void saveAuctionRecord(Auctionrecord auctionrecord) throws Exception;

    List<AuctionCustomer> selectAuctionendtime();

    List<Auction> selectAuctionNoendtime();

}
