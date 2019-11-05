package com.noah.solutions.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.noah.solutions.system.repository.DaoUtil.Criteria;
import com.noah.solutions.system.repository.DaoUtil.Criterion;
import com.noah.solutions.system.repository.DaoUtil.Restrictions;
import com.noah.solutions.system.repository.DaoUtil.SimpleExpression;
import com.noah.solutions.system.repository.LoginRecordRepository;
import com.noah.solutions.common.utils.DateUtil;
import com.noah.solutions.system.entity.LoginRecord;
import com.noah.solutions.system.service.LoginRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class LoginRecordServiceImpl implements LoginRecordService {
//    @Autowired
//    private LoginRecordMapper loginRecordMapper;

    @Resource
    private LoginRecordRepository loginRecordRepository;

    @Override
    public boolean add(LoginRecord loginRecord) {
        loginRecord.setCreateTime(new Date());
        loginRecordRepository.save(loginRecord);
        return true;
    }

    @Override
    public Page<LoginRecord> list(int pageNum, int pageSize, String startDate, String endDate, String username) {
        Criteria criteria = new Criteria();
        if(!StringUtils.isEmpty(startDate)) {
            criteria.add(Restrictions.lte("createTime", DateUtil.parseDate(startDate), true)).add(Restrictions.gte("createTime", DateUtil.parseDate(endDate), true));
        }
        if(!StringUtils.isEmpty(username)){
            criteria.add(new SimpleExpression("user.username",username, Criterion.Operator.LIKE));
        }
        return loginRecordRepository.findAll(criteria,PageRequest.of(pageNum-1,pageSize,new Sort(Sort.Direction.DESC,"id")));
    }
}