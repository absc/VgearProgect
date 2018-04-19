package com.example.newdemo.Service;

import com.alibaba.fastjson.JSON;
import com.example.newdemo.VO.MessageVO;
import com.example.newdemo.bean.Images;
import com.example.newdemo.bean.ReciveMessage;
import com.example.newdemo.dao.ReciveMessageMapper;
import com.example.newdemo.dao.VgearUser;
import com.example.newdemo.dao.VgearUserMapper;
import com.example.newdemo.util.EncodeFilter;
import com.example.newdemo.util.sensi.SensitiveFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Administrator on 2017/8/19.
 */
@ServerEndpoint("/webSocket/{id}")
@Slf4j
@Component
public class WebSocket {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    private MessageVO messageVO = new MessageVO();

    private static VgearUserMapper vgearUserMapper;

    private static ReciveMessageMapper reciveMessageMapper;

    private static SensitiveFilter filter;

    private static ImagesService imagesService;


    @Autowired
    public void setImagesService(ImagesService imagesService) {
        WebSocket.imagesService = imagesService;
    }

    @Autowired
    public void setVgearUserMapper(VgearUserMapper vgearUserMapper){
        WebSocket.vgearUserMapper = vgearUserMapper;
    }

    @Autowired
    public void setReciveMessageMapper(ReciveMessageMapper reciveMessageMapper){
        WebSocket.reciveMessageMapper = reciveMessageMapper;
    }

//    private static SensitiveWordUtil sensitiveWordUtil;


    @Autowired
    public void initSenstity(){
        filter = SensitiveFilter.DEFAULT;
//        sensitiveWordUtil = new SensitiveWordUtil();
        //初始化敏感词库
//        sensitiveWordUtil.init();//sensitiveWordSet
//        log.info("敏感词的数量：" , sensitiveWordUtil.sensitiveWordMap.size());
//        String string = "太多的伤感情怀也许只局限于饲养草你 荧幕中的草情节。"
//                + "然后我们的扮演的角色就是跟随着主人公中国红我操客联盟 怒草哀操而过于牵强的把自己的情感也联国附加于银幕情节中，然后感动就流泪，"
//                + "难过就躺在某一个人的怀里尽情的阐述心扉或者逼卡复制器一操你酒一你妈部电影在夜 深人静的晚上，关上电话静静的发呆着。";
//
//        //获取语句中的敏感词
//        Set<String> set = sensitiveWordUtil.getSensitiveWord(string);
//        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
//        set = sensitiveWordUtil.getSensitiveWord(string, SensitiveWordUtil.MinMatchTYpe);
//        System.out.println(filter.filter(string, '*'));

    }

    @OnOpen
    public void onOpen(Session session,@PathParam("id") String  id) {//,@PathParam("headurl") String headurl, @PathParam("nickname") String nickname
        if (id.equals("34")){
            this.session = session;
            webSockets.add(this);
            return;
        }
        this.session = session;
        webSockets.add(this);
        VgearUser user;
        try {
            user = vgearUserMapper.findUserById(id);

        }catch (Exception e){
            log.error(e.getMessage());
            webSockets.remove(this);
            this.onClose();
            return;
        }
        if (user==null){
            webSockets.remove(this);
            this.onClose();
            return;
        }
        messageVO.setType(1);
        messageVO.setUserNum(webSockets.size());
        messageVO.setMessage("有新的连接");
        messageVO.setHeadpic(user.getWxheadimgurl());
        messageVO.setNickname(user.getWxnickname());

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageVO);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        this.sendMessage(Json);
        log.info("【websocket消息】有新的连接, 总数:{}", webSockets.size());
    }


    @OnClose
    public void onClose() {
        webSockets.remove(this);

        messageVO.setType(2);
        messageVO.setUserNum(webSockets.size());
        messageVO.setMessage("有用户离开");

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageVO);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        this.sendMessage(Json);
        log.info("【websocket消息】连接断开, 总数:{}", webSockets.size());
    }
//    @Transactional
    @OnMessage
    public void onMessage(String message) {
        ReciveMessage  reciveMessage = JSON.parseObject(message,ReciveMessage.class);

        String   time=df.format(new Date());//获取当前时间
        if (reciveMessage.getType()==3||reciveMessage.getType()==4) {
            reciveMessageMapper.insert(reciveMessage.getType(), filter.filter(reciveMessage.getMessage(), '*'), time, reciveMessage.getNickname(), reciveMessage.getHeadpic(),"",reciveMessage.getMoney());
            reciveMessageMapper.delete();
        }
        if (reciveMessage.getType()==5){
            List<Images> imagesList=imagesService.select(reciveMessage.getUid()+"");
            if (imagesList.size()<=0)
                return;
                messageVO.setImageOne(imagesList.get(0).getPath());
            if (imagesList.size()>1)
                messageVO.setImageTwo(imagesList.get(1).getPath());
            reciveMessageMapper.insert(reciveMessage.getType(), filter.filter(reciveMessage.getMessage(), '*'), time, reciveMessage.getNickname(), reciveMessage.getHeadpic(),imagesList.get(0).getPath(),reciveMessage.getMoney());
            reciveMessageMapper.delete();
        }
        messageVO.setType(reciveMessage.getType());
        messageVO.setUserNum(webSockets.size());
        messageVO.setHeadpic(reciveMessage.getHeadpic());
        messageVO.setNickname(reciveMessage.getNickname());
        messageVO.setMessage(EncodeFilter.encode(filter.filter(reciveMessage.getMessage(), '*')));
        messageVO.setMoney(reciveMessage.getMoney());
        messageVO.setTime(time);

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageVO);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        this.sendMessage(Json);
    }

    public void sendMessage(String message) {
        log.info("【websocket消息】广播消息, message={}", message);
        for (WebSocket webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen())
                webSocket.session.getBasicRemote().sendText(message);
                else
                    webSockets.remove(webSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}