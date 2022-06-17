package com.shop.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.AuctionCustomerMapper;
import com.shop.pojo.Auction;
import com.shop.pojo.AuctionCustomer;
import com.shop.pojo.Auctionrecord;
import com.shop.pojo.User;
import com.shop.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class AuctionController {

    private static final int PAGE_SIZE = 6;
    @Autowired
    AuctionService auctionService;

    @Autowired
    AuctionCustomerMapper auctionCustomerMapper;


    @RequestMapping("/queryAuctions")
    public String getAuctionList(Model model,
                                 @RequestParam(name = "pageNum",required = false,defaultValue = "1") int pageNum,
                                 @ModelAttribute("condition") Auction auction){
        //设置分页区间
        PageHelper.startPage(pageNum,PAGE_SIZE);

//        List<Auction> auctionList = auctionService.getAuctionList();

        List<Auction> allAuction = auctionService.findAllAuction(auction);

        PageInfo page = new PageInfo(allAuction);
        model.addAttribute("auctionList",allAuction);
        model.addAttribute("page",page);
        return "index";
    }

    @RequestMapping("/findAuctionDetial/{auctionid}")
    public String findAuctionDetial(@PathVariable int auctionid, Model model){
        Auction auction = auctionService.selectAuctionAndAuctionRecordList(auctionid);

        model.addAttribute("auctionDetail",auction);

        return "auctionDetail";
    }

    @RequestMapping("/update/{auctionid}")
    public String update(@PathVariable int auctionid, Model model){
        model.addAttribute("update",auctionid);
        return "update";
    }

    @RequestMapping("/updateId")
    public String updateAuction(Auction auction){
        auctionService.updateAuction(auction);
        System.out.println(auction.toString());
        return "redirect:/queryAuctions";
    }


    @RequestMapping("/saveAuctionRecord")
    public String saveAuctionRecord(Auctionrecord auctionrecord, HttpSession session) throws Exception {

        //获取用户数据
        User user = (User)session.getAttribute("user");
        auctionrecord.setUserid(user.getUserid());
        //封装当前时间
        auctionrecord.setAuctiontime(new Date());

        auctionService.saveAuctionRecord(auctionrecord);

        return "redirect:/findAuctionDetial/"+auctionrecord.getAuctionid();

    }

    @RequestMapping("/deleteById/{auctionid}")
    public String deleteById(@PathVariable int auctionid){
        auctionService.deteleById(auctionid);
        return "redirect:/queryAuctions";
    }

    @RequestMapping("/toAuctionPage")
    public String toAuctionPage(){
        //跳转商品发布页面
        return "addAuction";
    }
    //MultipartFile 获取文件上传信息
    @RequestMapping("/publishAuctions")
    public String publishAuctions(MultipartFile pic,Auction auction) throws IOException {

        //封装图片信息
        auction.setAuctionpic(pic.getOriginalFilename());
        auction.setAuctionpictype(pic.getContentType());


        //创建文件上传路径
        File file = new File("E:\\图片\\自己保存\\" + pic.getOriginalFilename());

        pic.transferTo(file);

        auctionService.addAuction(auction);

        return "redirect:/queryAuctions";

    }

    @RequestMapping("/toAuctionResult")
    public String  toAuction(Model model){
        List<AuctionCustomer> auctionendtime = auctionService.selectAuctionendtime();
        List<Auction> endtimeList = auctionService.selectAuctionNoendtime();

        model.addAttribute("endtimeList",auctionendtime);
        model.addAttribute("noendtimeList",endtimeList);

        return "auctionResult";
    }

}
