package com.goblincwl.dragontwilight.service.impl;

import com.goblincwl.dragontwilight.common.CommonUtils;
import com.goblincwl.dragontwilight.common.EntityMap;
import com.goblincwl.dragontwilight.dao.BlessingTextureRepository;
import com.goblincwl.dragontwilight.dao.BlessingUsersRepository;
import com.goblincwl.dragontwilight.dao.BlessingYggLogRepository;
import com.goblincwl.dragontwilight.dao.WebNavIframeRepository;
import com.goblincwl.dragontwilight.entity.WebNavIframe;
import com.goblincwl.dragontwilight.service.WebNavIframeService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 基础 ServiceImpl
 * @create 2020-05-22 15:45
 */
@Service
@Transactional
public class WebNavIframeServiceImpl implements WebNavIframeService {

    private final WebNavIframeRepository webNavIframeRepository;
    private final BlessingUsersRepository blessingUsersRepository;
    private final BlessingTextureRepository blessingTextureRepository;
    private final BlessingYggLogRepository blessingYggLogRepository;

    public WebNavIframeServiceImpl(WebNavIframeRepository webNavIframeRepository, BlessingUsersRepository blessingUsersRepository, BlessingTextureRepository blessingTextureRepository, BlessingYggLogRepository blessingYggLogRepository) {
        this.webNavIframeRepository = webNavIframeRepository;
        this.blessingUsersRepository = blessingUsersRepository;
        this.blessingTextureRepository = blessingTextureRepository;
        this.blessingYggLogRepository = blessingYggLogRepository;
    }

    @Override
    public List<WebNavIframe> findNavList(WebNavIframe webNavIframe) {
        //根据slot字段正序排序
        return this.webNavIframeRepository.findAll(Example.of(webNavIframe), Sort.by(Sort.Direction.ASC, "slot"));
    }

    @Override
    public WebNavIframe findNavByUri(String webManagerIndexUri) {
        WebNavIframe webNavIframe = new WebNavIframe();
        webNavIframe.setUri(webManagerIndexUri);
        return this.webNavIframeRepository.findOne(Example.of(webNavIframe)).orElse(null);
    }

    @Override
    public Map<String, Object> getDashboardDate(EntityMap<String, Object> entityMap) throws ParseException {
        //返回数据组装
        Map<String, Object> resultMap = new HashMap<>();
        //用户总数
        Long userCount = this.blessingUsersRepository.count();
        resultMap.put("userCount", userCount);
        //材质总数
        Long textureCount = this.blessingTextureRepository.count();
        resultMap.put("textureCount", textureCount);
        //已登录玩家总数
        Long loginCount = this.blessingYggLogRepository.groupCount();
        resultMap.put("loginCount", loginCount);

        //处理日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date startDate = sdf.parse(entityMap.get("startDate").toString());
        Date endDate = sdf.parse(entityMap.get("endDate").toString());

        //按天材质上传数量
        Map<String, Object> textureUploadData = new HashMap<>();
        //按天用户注册数量
        Map<String, Object> userRegData = new HashMap<>();
        //按天查询登陆过的玩家数量
        Map<String, Object> userLoginData = new HashMap<>();
        for (int i = 0; i < CommonUtils.calDateBetweenDays(startDate, endDate) + 1; i++) {
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_MONTH, i);
            String thisTimeDate = sdf.format(calendar.getTime());
            //材质
            long countTexture = this.blessingTextureRepository.getDateCount(thisTimeDate);
            textureUploadData.put(thisTimeDate, countTexture);
            //用户
            long countUser = this.blessingUsersRepository.getDateCount(thisTimeDate);
            userRegData.put(thisTimeDate, countUser);
            //登陆玩家
            long countUserLogin = this.blessingYggLogRepository.getLoginPlayerDateCount(thisTimeDate);
            userLoginData.put(thisTimeDate,countUserLogin);

        }
        resultMap.put("textureUploadData", textureUploadData);
        resultMap.put("userRegData", userRegData);
        resultMap.put("userLoginData", userLoginData);

        return resultMap;
    }
}
