package com.slz.demo.controller;


import com.slz.demo.VO.Message;
import com.slz.demo.VO.StrategyMsg;
import com.slz.demo.enums.ErrorEnum;
import com.slz.demo.enums.OperationEnum;
import com.slz.demo.enums.StrategyEnum;
import com.slz.demo.pojo.*;
import com.slz.demo.service.*;
import com.slz.demo.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class StrategyCtrl {
    @Autowired
    StrategySerImp strategySer;
    @Autowired
    CitySerImp citySerImp;
    @Autowired
    ReportLogSerImp reportLogSerImp;
    @Autowired
    UserLogSerImp userLogSerImp;
    @Autowired
    UserConcernSerImp userConcernSerImp;
    @Autowired
    UserSerImp userSerImp;
    
    
    @RequestMapping("/getCollectedStrategies")
    public Message getCollectedStrategies(Integer userId){
        Message message=new Message();

        List<UserLog> logList=userLogSerImp.findByUserIdAndOperationIdOrderByTime(userId,OperationEnum.collect.getMessage());
        if(logList.size()<=0){
            return  message;
        }
        List<String > strategyIdList=logList
                .stream()
                .map(e->e.getStrategyId())
                .collect(Collectors.toList());

        List<Strategy> strategyList=strategySer.findByIdList(strategyIdList, PageRequest.of(0,5));
        List<StrategyMsg> strategyMsgs=strategyList.stream()
                .map(e->new StrategyMsg(e,citySerImp.findById(e.getCityId()).getCity()))
                .collect(Collectors.toList());
        strategyMsgs.stream().forEach(getConsumer());
        message.setData(strategyMsgs);
        return message;
    }

    
    
    @RequestMapping("/findStrategiesByAccount")
    public Message findStrategiesByAccount(String account){
        Message message=new Message();
        List<StrategyMsg> strategyMsgs=strategySer.findByAccount(account)
                .stream()
                .map(e->new StrategyMsg(e,citySerImp.findById(e.getCityId()).getCity()))
                .collect(Collectors.toList());
        strategyMsgs.stream().forEach(getConsumer());
        message.setData(strategyMsgs);

        return message;
    }
    @RequestMapping("/showStrategiesOrderByTime")
    public Message showStrategiesOrderByTime(int start,int end){
        Message message=new Message();
        List<Strategy> list=strategySer.findOrderByTime(start,end);
        List<StrategyMsg> strategyMsgs=list.stream()
                .map(e->new StrategyMsg(e,citySerImp.findById(e.getCityId()).getCity()))
                .collect(Collectors.toList());
        strategyMsgs.stream().forEach(getConsumer());
        message.setData(strategyMsgs);
        return message;
    }

    @RequestMapping("/showStrategiesByConcern")
    public Message showStrategiesByConcern(int start,int end,HttpServletRequest request){
        Message message=new Message();
        Integer userId=null;
        try{
            userId=((User)request.getSession().getAttribute("user")).getId();
        }catch (NullPointerException e){
            message.setTip(ErrorEnum.error2.getCode());
            return message;
        }
        List<String > accountList=userConcernSerImp.findByUserId(userId)
                .stream()
                .map(e->e.getAuthorAccount())
                .collect(Collectors.toList());
        if(accountList.size()<=0){
            message.setData(new ArrayList<>());
            return message;
        }
        List<Strategy> strategyList=strategySer.findByAccount(start,end,accountList);
        List<StrategyMsg> strategyMsgs=strategyList.stream()
                .map(e->new StrategyMsg(e,citySerImp.findById(e.getCityId()).getCity()))
                .collect(Collectors.toList());
        strategyMsgs.stream().forEach(getConsumer());
        message.setData(strategyMsgs);
        return message;
    }

    @RequestMapping("/showStrategiesByOperation")
    public Message showStrategiesByOperation(int start,int end,Long startTimeL,String operationId){
        Message message=new Message();
        List<Strategy> list=strategySer.showStrategiesByOperation(start,end,new Timestamp(startTimeL),new Timestamp(System.currentTimeMillis()),operationId);
        List<StrategyMsg> strategyMsgs=list.stream()
                .map(e->new StrategyMsg(e,citySerImp.findById(e.getCityId()).getCity()))
                .collect(Collectors.toList());

        strategyMsgs.stream().forEach(getConsumer());

        message.setData(strategyMsgs);
        return message;
    }

    private Consumer getConsumer(){
        Consumer<StrategyMsg> consumer=new Consumer<StrategyMsg>() {
            @Override
            public void accept(StrategyMsg strategyMsg) {
                Map<String ,String > map=new HashMap<>();
                map.put("strategyId",strategyMsg.getStrategy().getId());
                map.put("operationId",OperationEnum.watch.getMessage());
                strategyMsg.setWatchNum(userLogSerImp.getNumByParam(map));

                map.put("operationId",OperationEnum.collect.getMessage());
                strategyMsg.setCollectNum(userLogSerImp.getNumByParam(map));

                map.put("operationId",OperationEnum.upgood.getMessage());
                strategyMsg.setUpgoodNum(userLogSerImp.getNumByParam(map));
            }
        };
        return consumer;
    }
    
    @RequestMapping("/getAuditStrategies")
    public Message getAuditStrategies(){
        Message message=new Message();
        List<Strategy> strategyList=strategySer.findByIsLegal(0);
        message.setData(strategyList);
        return message;
    }
    
    @RequestMapping("/BanOrPass")
    public Message Ban(String id,Integer o){
        Message message=new Message();
        Strategy strategy=strategySer.findById(id);
        strategy.setIsLegal(o);
        strategySer.save(strategy);
        return message;
    }
    
    

    @RequestMapping("/checkStrategyLegal")
    public Message checkStrategyLegal(String strategyId, HttpServletRequest request){
        Message message=new Message();
        Strategy strategy=strategySer.findById(strategyId);
        Integer userId=null;
        try{
            userId=((User)request.getSession().getAttribute("user")).getId();
        }catch (NullPointerException e){
            message.setTip("Please login in first");
            return  message;
        }

        if(reportLogSerImp.findByUserIdAndStrategyId(userId,strategyId).size()>0){
            message.setTip("You have reported it,do not report again");
            return  message;
        };

        switch (strategy.getBeReported()){
            case 0:
                strategy.setIsLegal(0);
                strategy.setBeReported(1);
                ReportLog reportLog=new ReportLog();
                reportLog.setUserId(userId);
                reportLog.setStrategyId(strategyId);
                reportLogSerImp.save(reportLog);
                strategySer.save(strategy);
                message.setTip("Report Success");
                break;
            case 1:
                message.setTip("This strategy has been reported");
                break;
        }
        return  message;
    }


    @RequestMapping("/showStrategyInfo")
    public ModelAndView   showStrategyInfo(String strategyId,ModelMap modelMap,HttpServletRequest request){
        Strategy strategy=strategySer.findById(strategyId);
        Integer userId=null;
        try{
            userId=((User)request.getSession().getAttribute("user")).getId();
            UserLog userLog=userLogSerImp.findByUserIdAndStrategyIdAndAndOperationId(userId,strategyId,OperationEnum.watch.getMessage());
            if(userLog==null){
                UserLog userLog1=new UserLog();
                userLog1.setOperationId(OperationEnum.watch.getMessage());
                userLog1.setUserId(userId);
                userLog1.setStrategyId(strategyId);
                userLogSerImp.save(userLog1);
            }
        }catch (NullPointerException e){
            String ip=Tools.getIpAddress(request);
            UserLog userLog=userLogSerImp.findByIpAndAndStrategyIdAndAndOperationId(ip,strategyId,OperationEnum.watch.getMessage());
            if(userLog==null){
                UserLog userLog1=new UserLog();
                userLog1.setOperationId(OperationEnum.watch.getMessage());
                userLog1.setIp(ip);
                userLog1.setStrategyId(strategyId);
                userLogSerImp.save(userLog1);
            }
        }
        StrategyMsg msg=new StrategyMsg(strategy,citySerImp.findById(strategy.getCityId()).getCity());

        Map<String ,String > map=new HashMap<>();
        map.put("strategyId",strategyId);
        map.put("operationId",OperationEnum.watch.getMessage());
        msg.setWatchNum(userLogSerImp.getNumByParam(map));

        map.put("operationId",OperationEnum.collect.getMessage());
        msg.setCollectNum(userLogSerImp.getNumByParam(map));

        map.put("operationId",OperationEnum.upgood.getMessage());
        msg.setUpgoodNum(userLogSerImp.getNumByParam(map));

        modelMap.addAttribute("Strategy",msg);
        return new ModelAndView("StrategyInfo");
    }

    @RequestMapping("/searchStrategies")
    public Message searchStrategies(String condition){
        Message message=new Message();
        //查询文章
        List<Strategy> strategyList=new ArrayList<>();

        List<City> cityList=citySerImp.FuzzyScreen(condition);
        List<String > cityIdList=cityList
                .stream()
                .map(e->e.getId())
                .collect(Collectors.toList());
        if(cityIdList.size()>0){
            List<Strategy> strategyList1=strategySer.findByCityId(cityIdList);
            strategyList.addAll(strategyList1);
        }


        List<Strategy> strategyList2=strategySer.findByTitleLikeOrAttractionsNameLike(condition);
        strategyList.addAll(strategyList2);

        System.out.println(strategyList.size());
        System.out.println("+++++++++++++++++++++++++++");
        Tools.RemoveRepeat(strategyList);
        System.out.println(strategyList.size());
        List<StrategyMsg> msgList=strategyList
                .stream()
                .map(e->new StrategyMsg(e,citySerImp.findById(e.getCityId()).getCity()))
                .collect(Collectors.toList());
        msgList.stream().forEach(getConsumer());

        //查询用户
        List<User> userList=userSerImp.findByNameLikeOrAccount(condition);

        Map<String ,Object> msgMap=new HashMap<>();
        msgMap.put("users",userList);
        msgMap.put("strategies",msgList);

        message.setData(msgMap);
        return message;
    }


    @RequestMapping("/showByScreening")
    public Message showByScreening(@RequestParam Map<String ,String > param){
        Message message=new Message();
//        System.out.println(param);
        List<City> cityList=citySerImp.Screening(param);
        if(cityList.size()<=0){
            return message;
        }
        List<String> idList= cityList.stream()
                .map(e -> e.getId())
                .collect(Collectors.toList());
        List<Strategy> strategies=strategySer.findByCityId(0,10,idList);

        List<StrategyMsg> strategyMsgs=strategies.stream()
                .map(e->new StrategyMsg(e,citySerImp.findById(e.getCityId()).getCity()))
                .collect(Collectors.toList());
        strategyMsgs.stream().forEach(getConsumer());
//        Tools.getListContent(strategyMsgs);

        message.setData(strategyMsgs);
        return message;
    }

    @RequestMapping("/saveStrategy")
    public Message  saveStrategy(String account,String title,String article,String articleText,String[] city,String attractionName){
        Message message=new Message();

        Strategy strategy=new Strategy();
        strategy.setId(Tools.getId(StrategyEnum.StrategyIdPrefix.getMessage()));
        strategy.setAccount(account);
        strategy.setAttractionsName(attractionName);
        strategy.setTitle(title);
        strategy.setContent(article);
        strategy.setContentText(articleText);
        strategy.setCityId(citySerImp.Screening(Tools.toCityMap(city)).get(0).getId());

        strategySer.save(strategy);
        return message;
    }

    @RequestMapping("/getStrategyInfo")
    public Message getStrategyInfo(String strategyId){
        Message message=new Message();
        Strategy strategy=strategySer.findById(strategyId);
        StrategyMsg msg=new StrategyMsg(strategy,citySerImp.findById(strategy.getCityId()).getCity());
        System.out.println(msg);
        message.setData(msg);
        return message;
    }


}
