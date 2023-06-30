package com.ruoyi.pill.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.pill.mapper.PillDrugMapper;
import com.ruoyi.pill.domain.PillDrug;
import com.ruoyi.pill.service.IPillDrugService;

/**
 * 药品信息Service业务层处理
 * 
 * @author xiaoniu
 * @date 2023-06-30
 */
@Service
public class PillDrugServiceImpl implements IPillDrugService 
{
    @Autowired
    private PillDrugMapper pillDrugMapper;

    /**
     * 查询药品信息
     * 
     * @param drugId 药品信息主键
     * @return 药品信息
     */
    @Override
    public PillDrug selectPillDrugByDrugId(Long drugId)
    {
        return pillDrugMapper.selectPillDrugByDrugId(drugId);
    }

    /**
     * 查询药品信息列表
     * 
     * @param pillDrug 药品信息
     * @return 药品信息
     */
    @Override
    public List<PillDrug> selectPillDrugList(PillDrug pillDrug)
    {
        return pillDrugMapper.selectPillDrugList(pillDrug);
    }

    /**
     * 新增药品信息
     * 
     * @param pillDrug 药品信息
     * @return 结果
     */
    @Override
    public int insertPillDrug(PillDrug pillDrug)
    {
        pillDrug.setCreateTime(DateUtils.getNowDate());
        return pillDrugMapper.insertPillDrug(pillDrug);
    }

    /**
     * 修改药品信息
     * 
     * @param pillDrug 药品信息
     * @return 结果
     */
    @Override
    public int updatePillDrug(PillDrug pillDrug)
    {
        pillDrug.setUpdateTime(DateUtils.getNowDate());
        return pillDrugMapper.updatePillDrug(pillDrug);
    }

    /**
     * 批量删除药品信息
     * 
     * @param drugIds 需要删除的药品信息主键
     * @return 结果
     */
    @Override
    public int deletePillDrugByDrugIds(Long[] drugIds)
    {
        return pillDrugMapper.deletePillDrugByDrugIds(drugIds);
    }

    /**
     * 删除药品信息信息
     * 
     * @param drugId 药品信息主键
     * @return 结果
     */
    @Override
    public int deletePillDrugByDrugId(Long drugId)
    {
        return pillDrugMapper.deletePillDrugByDrugId(drugId);
    }
}
