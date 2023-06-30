package com.ruoyi.pill.mapper;

import java.util.List;
import com.ruoyi.pill.domain.PillFactory;

/**
 * 生产厂家信息Mapper接口
 * 
 * @author xiaoniu
 * @date 2023-06-29
 */
public interface PillFactoryMapper 
{
    /**
     * 查询生产厂家信息
     * 
     * @param factoryId 生产厂家信息主键
     * @return 生产厂家信息
     */
    public PillFactory selectPillFactoryByFactoryId(Long factoryId);

    /**
     * 查询生产厂家信息列表
     * 
     * @param pillFactory 生产厂家信息
     * @return 生产厂家信息集合
     */
    public List<PillFactory> selectPillFactoryList(PillFactory pillFactory);

    /**
     * 新增生产厂家信息
     * 
     * @param pillFactory 生产厂家信息
     * @return 结果
     */
    public int insertPillFactory(PillFactory pillFactory);

    /**
     * 修改生产厂家信息
     * 
     * @param pillFactory 生产厂家信息
     * @return 结果
     */
    public int updatePillFactory(PillFactory pillFactory);

    /**
     * 删除生产厂家信息
     * 
     * @param factoryId 生产厂家信息主键
     * @return 结果
     */
    public int deletePillFactoryByFactoryId(Long factoryId);

    /**
     * 批量删除生产厂家信息
     * 
     * @param factoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePillFactoryByFactoryIds(Long[] factoryIds);
}
