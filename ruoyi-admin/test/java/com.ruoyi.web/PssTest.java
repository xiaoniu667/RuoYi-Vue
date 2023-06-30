package com.ruoyi.web;

import com.ruoyi.pill.domain.PillDrug;
import com.ruoyi.pill.domain.PillFactory;
import com.ruoyi.pill.service.IPillDrugService;
import com.ruoyi.pill.service.IPillFactoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class PssTest {


    @Resource
    private IPillFactoryService pillFactoryService;

    @Resource
    private IPillDrugService pillDrugService;


    @Test
    void name() {
        PillFactory pillFactory=new PillFactory();
        pillFactory.setFactoryName("云南");
        List<PillFactory> pillFactories = pillFactoryService.selectPillFactoryList(pillFactory);
        pillFactories.forEach(System.out::println);
    }

    @Test
    void name1() {
        PillDrug pillDrug = new PillDrug();
        PillFactory pillFactory = new PillFactory();
        pillFactory.setFactoryName("哈");
        pillDrug.setPillFactory(pillFactory);
        List<PillDrug> pillDrugs = pillDrugService.selectPillDrugList(pillDrug);
        pillDrugs.forEach(System.out::println);
    }
}
