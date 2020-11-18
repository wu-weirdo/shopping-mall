package com.edaochina.shopping.api.service.live.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.live.LivingCurriculumDao;
import com.edaochina.shopping.api.service.live.LivingCurriculumService;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.domain.entity.live.LivingCurriculum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * 直播课程(LivingCurriculum)表服务实现类
 *
 * @author wangpenglei
 * @since 2019-08-26 17:24:54
 */
@Service("livingCurriculumService")
@Transactional(rollbackFor = Exception.class)
public class LivingCurriculumServiceImpl extends ServiceImpl<LivingCurriculumDao, LivingCurriculum> implements LivingCurriculumService {

    private Random random = new Random();

    @Override
    public boolean updateById(LivingCurriculum entity) {
        check(entity);
        return super.updateById(entity);
    }

    @Override
    public boolean save(LivingCurriculum entity) {
        check(entity);
        return super.save(entity);
    }

    private String getCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    private void check(LivingCurriculum entity) {
        boolean isDel = entity.getDelFlag() != null && entity.getDelFlag() == 1;
        if (!isDel
                && entity.getLastValidTime().isAfter(LocalDateTime.now())
                && entity.getPutawayTime().isBefore(LocalDateTime.now())) {
            QueryWrapper<LivingCurriculum> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .ge(LivingCurriculum::getLastValidTime, LocalDateTime.now())
                    .le(LivingCurriculum::getPutawayTime, LocalDateTime.now())
                    .ne(LivingCurriculum::getId, entity.getId())
                    .eq(LivingCurriculum::getDelFlag, 0);
            if (this.count(queryWrapper) > 0) {
                throw new YidaoException("其他课程正在上架中！");
            }
        }
    }
}