package com.iot.baobiao.service;

import com.iot.baobiao.dao.SelfSiteDao;
import com.iot.baobiao.exception.NoDataException;
import com.iot.baobiao.pojo.SelfSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ja on 2016/6/22.
 */

@Service
public class DataService {

    @Autowired
    private SelfSiteDao selfSiteDao;

    public List<SelfSite> fetchData(String ids, Date fromTime, List<String> words, int page) {
       try {
           if (fromTime == null) {
               if (words == null) return selfSiteDao.findByUrl(ids, page);
               else return selfSiteDao.findByUrlAndWord(ids, words, page);
           } else {
               if (words == null) return selfSiteDao.findByUrlAndTime(ids, fromTime, page);
               else return selfSiteDao.findByUrlAndWordAndTime(ids, fromTime, words, page);
           }
       } catch (EmptyResultDataAccessException e) {
           throw new NoDataException();
       }
    }
}
