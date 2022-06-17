package com.shop.mapper;

import com.shop.pojo.Auction;
import com.shop.pojo.AuctionCustomer;

import java.util.List;

public interface AuctionCustomerMapper {

    Auction selectAuctionAndAuctionRecordList(Integer auctionid);

    List<AuctionCustomer> selectAuctionendtime();

    List<Auction> selectAuctionNoendtime();
}
