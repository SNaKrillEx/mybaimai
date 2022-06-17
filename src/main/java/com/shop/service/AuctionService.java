package com.shop.service;

import com.shop.pojo.Auction;
import com.shop.pojo.AuctionCustomer;
import com.shop.pojo.Auctionrecord;

import java.util.List;

public interface AuctionService {

    List<Auction> getAuctionList();

    List<Auction> findAllAuction(Auction auction);

    public void deteleById(Integer auctionId);

    Auction selectAuctionAndAuctionRecordList(int auctionid);

    void saveAuctionRecord(Auctionrecord auctionrecord) throws Exception;

    void addAuction(Auction auction);

    int updateAuction(Auction auction);

    List<AuctionCustomer> selectAuctionendtime();

    List<Auction> selectAuctionNoendtime();

}
