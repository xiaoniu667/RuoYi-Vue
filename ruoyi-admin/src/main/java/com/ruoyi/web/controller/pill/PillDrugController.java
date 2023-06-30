package com.ruoyi.web.controller.pill;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.pill.domain.PillDrug;
import com.ruoyi.pill.service.IPillDrugService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 药品信息Controller
 * 
 * @author xiaoniu
 * @date 2023-06-30
 */
@RestController
@RequestMapping("/pill/drug")
public class PillDrugController extends BaseController
{
    @Autowired
    private IPillDrugService pillDrugService;

    /**
     * 查询药品信息列表
     */
    @PreAuthorize("@ss.hasPermi('pill:drug:list')")
    @GetMapping("/list")
    public TableDataInfo list(PillDrug pillDrug)
    {
        startPage();
        List<PillDrug> list = pillDrugService.selectPillDrugList(pillDrug);
        return getDataTable(list);
    }

    /**
     * 导出药品信息列表
     */
    @PreAuthorize("@ss.hasPermi('pill:drug:export')")
    @Log(title = "药品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PillDrug pillDrug)
    {
        List<PillDrug> list = pillDrugService.selectPillDrugList(pillDrug);
        ExcelUtil<PillDrug> util = new ExcelUtil<PillDrug>(PillDrug.class);
        util.exportExcel(response, list, "药品信息数据");
    }

    /**
     * 获取药品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('pill:drug:query')")
    @GetMapping(value = "/{drugId}")
    public AjaxResult getInfo(@PathVariable("drugId") Long drugId)
    {
        return success(pillDrugService.selectPillDrugByDrugId(drugId));
    }

    /**
     * 新增药品信息
     */
    @PreAuthorize("@ss.hasPermi('pill:drug:add')")
    @Log(title = "药品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PillDrug pillDrug)
    {
        return toAjax(pillDrugService.insertPillDrug(pillDrug));
    }

    /**
     * 修改药品信息
     */
    @PreAuthorize("@ss.hasPermi('pill:drug:edit')")
    @Log(title = "药品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PillDrug pillDrug)
    {
        return toAjax(pillDrugService.updatePillDrug(pillDrug));
    }

    /**
     * 删除药品信息
     */
    @PreAuthorize("@ss.hasPermi('pill:drug:remove')")
    @Log(title = "药品信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{drugIds}")
    public AjaxResult remove(@PathVariable Long[] drugIds)
    {
        return toAjax(pillDrugService.deletePillDrugByDrugIds(drugIds));
    }
}
