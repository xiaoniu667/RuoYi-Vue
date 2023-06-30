package com.ruoyi.pill.mapper;

import java.util.List;
import com.ruoyi.pill.domain.PillDrug;

/**
 * 药品信息Mapper接口
 * 
 * @author xiaoniu
 * @date 2023-06-30
 */
public interface PillDrugMapper 
{
    /**
     * 查询药品信息
     * 
     * @param drugId 药品信息主键
     * @return 药品信息
     */
    public PillDrug selectPillDrugByDrugId(Long drugId);

    /**
     * 查询药品信息列表
     * 
     * @param pillDrug 药品信息
     * @return 药品信息集合
     */
    public List<PillDrug> selectPillDrugList(PillDrug pillDrug);

    /**
     * 新增药品信息
     * 
     * @param pillDrug 药品信息
     * @return 结果
     */
    public int insertPillDrug(PillDrug pillDrug);

    /**
     * 修改药品信息
     * 
     * @param pillDrug 药品信息
     * @return 结果
     */
    public int updatePillDrug(PillDrug pillDrug);

    /**
     * 删除药品信息
     * 
     * @param drugId 药品信息主键
     * @return 结果
     */
    public int deletePillDrugByDrugId(Long drugId);

    /**
     * 批量删除药品信息
     * 
     * @param drugIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePillDrugByDrugIds(Long[] drugIds);
}
