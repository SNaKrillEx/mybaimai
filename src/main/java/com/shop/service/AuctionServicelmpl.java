package com.shop.service;

import com.shop.mapper.AuctionCustomerMapper;
import com.shop.mapper.AuctionMapper;
import com.shop.mapper.AuctionrecordMapper;
import com.shop.pojo.*;
import com.shop.uitls.AuctionPriceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuctionServicelmpl implements AuctionService{

    @Autowired
    AuctionMapper auctionMapper;

    @Autowired
    AuctionCustomerMapper auctionCustomerMapper;

    @Autowired
    AuctionrecordMapper auctionrecordMapper;

    @Override
    public List<Auction> getAuctionList() {

        List<Auction> auctions = auctionMapper.selectByExample(null);

        return auctions;
    }

    @Override
    public List<Auction> findAllAuction(Auction auction) {
        //创建查询对象
        AuctionExample example = new AuctionExample();
        AuctionExample.Criteria criteria = example.createCriteria();

        // 判断 用户传入的多个条件或者没有传条件
        if(auction!=null)
        {
            // 竞拍商品的名称
            if(auction.getAuctionname()!=null&&!"".equals(auction.getAuctionname()))
            {
                System.out.println(auction.getAuctionname()+"=============");
                // 模糊查询
                criteria.andAuctionnameLike("%"+auction.getAuctionname()+"%");

            }
            // 竞拍品描述的查询
            if(auction.getAuctiondesc()!=null&&!"".equals(auction.getAuctiondesc()))
            {
                System.out.println(auction.getAuctiondesc()+"=============");
                // 匹配查询
                criteria.andAuctiondescEqualTo(auction.getAuctiondesc());
            }
            // 大于开始时间
            if(auction.getAuctionstarttime()!=null)
            {
                System.out.println(auction.getAuctionstarttime()+"=============");
                criteria.andAuctionstarttimeGreaterThan(auction.getAuctionstarttime());
            }

            // 小于结束时间
            if(auction.getAuctionendtime()!=null)
            {
                System.out.println(auction.getAuctionendtime()+"=============");
                criteria.andAuctionendtimeLessThan(auction.getAuctionendtime());
            }

            // 大于起拍价
            if(auction.getAuctionstartprice()!=null)
            {
                System.out.println(auction.getAuctionstartprice()+"=============");
                criteria.andAuctionstartpriceGreaterThan(auction.getAuctionstartprice());
            }
        }

        // 设置起拍时间的降序
        example.setOrderByClause("auctionstarttime desc");


        List<Auction> auctionList = this.auctionMapper.selectByExample(example);
        return auctionList;
    }

    @Override
    public Auction selectAuctionAndAuctionRecordList(int auctionid) {
        Auction auction = auctionCustomerMapper.selectAuctionAndAuctionRecordList(auctionid);

        return auction;
    }


    @Override
    public void saveAuctionRecord(Auctionrecord auctionrecord) throws Exception{
        //实现竞拍功能    当前商品竞拍数据
        Auction auction = auctionCustomerMapper.selectAuctionAndAuctionRecordList(auctionrecord.getAuctionid());

        //表示竞拍时间结束

//        if (auction.getAuctionendtime().after(new Date())==false){
        if (auction==null){
            throw new AuctionPriceException("您的竞拍商品时间已结束");
        }

        if (auction.getAuctionrecodList().size()>0){
            //表示有竞拍记录
            //获取商品最大竞拍价格
            Auctionrecord max = auction.getAuctionrecodList().get(0);
            //用户输入价格和最高价格做比较
            if (auctionrecord.getAuctionprice().compareTo(max.getAuctionprice())<1){
                throw new AuctionPriceException("您当前价格小于最高价格");
            }
        }else {
            //表示没有竞拍记录
            //用户输入价格和商品起拍价进行比较
            if (auctionrecord.getAuctionprice().compareTo(auction.getAuctionstartprice())<1){
                throw new AuctionPriceException("您输入的价格小于起拍价");
            }
        }
        auctionrecordMapper.insert(auctionrecord);
    }

    @Override
    public void deteleById(Integer auctionId) {
        //创建查询对象
        AuctionrecordExample example = new AuctionrecordExample();
        AuctionrecordExample.Criteria criteria = example.createCriteria();

        criteria.andAuctionidEqualTo(auctionId);
        //delete from auctionrecord where aucionId = ?

        auctionrecordMapper.deleteByExample(example);

        //根据主键删除
        //delete from auction where aucionId = ?
        auctionMapper.deleteByPrimaryKey(auctionId);
    }

    @Override
    public void addAuction(Auction auction) {
        auctionMapper.insert(auction);
    }

    @Override
    public int updateAuction(Auction auction) {
        return auctionMapper.updateByPrimaryKeySelective(auction);
    }

    @Override
    public List<AuctionCustomer> selectAuctionendtime() {
        return this.auctionCustomerMapper.selectAuctionendtime();
    }

    @Override
    public List<Auction> selectAuctionNoendtime() {
        return this.auctionCustomerMapper.selectAuctionNoendtime();
    }

}
