package com.rass.server.rass_transport.controller;


import cn.hutool.json.JSONUtil;
import com.rass.server.rass_common.rass_utils.CodeGeneratorUtils;
import com.rass.server.rass_common.rass_utils.StringUtils;
import com.rass.server.rass_framework.rass_tenant.TenantDatabaseHandler;
import com.rass.server.rass_research.rdm_article.dto.TRdmArticles;
import com.rass.server.rass_research.rdm_article.service.TRdmArticlesService;
import com.rass.server.rass_research.rdm_cell.dto.TRdmCell;
import com.rass.server.rass_research.rdm_cell.service.TRdmCellService;
import com.rass.server.rass_research.rdm_data.dto.TRdmDataAnalysis;
import com.rass.server.rass_research.rdm_data.service.TRdmDataAnalysisService;
import com.rass.server.rass_research.rdm_molding.dto.TRdmAnimalModel;
import com.rass.server.rass_research.rdm_molding.dto.TRdmMoldingDetail;
import com.rass.server.rass_research.rdm_molding.dto.TRdmMoldingFile;
import com.rass.server.rass_research.rdm_molding.dto.TRdmMoldingTheme;
import com.rass.server.rass_research.rdm_molding.service.TRdmAnimalModelService;
import com.rass.server.rass_research.rdm_molding.service.TRdmMoldingDetailService;
import com.rass.server.rass_research.rdm_molding.service.TRdmMoldingFileService;
import com.rass.server.rass_research.rdm_molding.service.TRdmMoldingThemeService;
import com.rass.server.rass_research.rdm_primer.dto.TRdmPrimer;
import com.rass.server.rass_research.rdm_primer.service.TRdmPrimerService;
import com.rass.server.rass_transport.dto.*;
import com.rass.server.rass_transport.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rass.server.rass_common.rass_constant.Constants.FILL_CHARACTER;
import static com.rass.server.rass_experiment.ex_contract.service.impl.TExContractServiceImpl.GTMA_KE_JI;

/**
 * <p>
 * 科研文章表 前端控制器
 * </p>
 *
 * @author liuchang
 * @since 2024-06-27
 */
@RestController
@RequestMapping("/rdResearchArticle")
@RequiredArgsConstructor
public class RdResearchArticleController {

    private final RdResearchArticleService rdResearchArticleService;
    private final TRdmArticlesService trdmArticlesService;
    private final RdModelDetailService rdModelDetailService;
    private final RdResearchModelService rdResearchModelService;
    private final TRdmAnimalModelService trdmAnimalModelService;
    private final TRdmMoldingThemeService trdmMoldingThemeService;
    private final TRdmMoldingDetailService trdmMoldingDetailService;
    private final TRdmMoldingFileService trdmMoldingFileService;
    private final RdDiseaseAnalysisService rdDiseaseAnalysisService;
    private final TRdmDataAnalysisService trdmDataAnalysisService;
    private final RdResearchPrimerService rdResearchPrimerService;
    private final TRdmPrimerService trdmPrimerService;
    private final RdDictionaryCellService rdDictionaryCellService;
    private final TRdmCellService trdmCellService;

    @GetMapping("/trdmCell")
    @Transactional(rollbackFor = Exception.class)
    public String trdmCell() {
        // 设置标记，绕过多租户拦截器
        TenantDatabaseHandler.setBypassInterceptor(true);

        List<RdDictionaryCell> cells = rdDictionaryCellService.list();

        cells.forEach(c -> {
            TRdmCell cell = new TRdmCell();
            cell.setCellUuid(CodeGeneratorUtils.getUUID("XBb"))
                    .setCellNumber(c.getCellNumber())
                    .setCellName(c.getCellName())
                    .setCellChineseName(StringUtils.isBlank(c.getCellChineseName()) ? FILL_CHARACTER : c.getCellChineseName())
                    .setAffiliationType(StringUtils.isBlank(c.getCategory()) ? FILL_CHARACTER : c.getCategory())
                    .setCancerSpecies(c.getTumorSpecies())
                    .setSpecies(StringUtils.isBlank(c.getSpecies()) ? FILL_CHARACTER : c.getSpecies())
                    .setHistogenesis(StringUtils.isBlank(c.getTissueSource()) ? FILL_CHARACTER : c.getTissueSource())
                    .setCultureMedium(StringUtils.isBlank(c.getCultureMedium()) ? FILL_CHARACTER : c.getCultureMedium())
                    .setGenerationalMethods(c.getPassageMethod())
                    .setGrowthConditions(c.getGrowthConditions())
                    .setGrowthCharacteristics(StringUtils.isBlank(c.getGrowthCharacteristics()) ? FILL_CHARACTER : c.getGrowthCharacteristics())
                    .setCellStr(StringUtils.isBlank(c.getStrInfo()) ? FILL_CHARACTER : c.getStrInfo())
                    .setStorageConditions(c.getStorageConditions())
                    .setMycoplasmaDetectionResults(StringUtils.isBlank(c.getDetectionResult()) ? FILL_CHARACTER : c.getDetectionResult())
                    .setAffiliationDept("粒成生物")
                    .setManagerUuid("17174019016437972")
                    .setManagerName("蹇天燊")
                    .setRemarks(c.getNote())
                    .setDelFlag(c.getIsDeleted())
                    .setCreateTime(c.getCreateTime())
                    .setUpdateTime(c.getUpdateTime())
                    .setTenantUuid(GTMA_KE_JI)
            ;

            trdmCellService.save(cell);
        });

        TenantDatabaseHandler.setBypassInterceptor(false);
        return "success!";
    }

    @GetMapping("/tRdmPrimer")
    @Transactional(rollbackFor = Exception.class)
    public String tRdmPrimer() {
        // 设置标记，绕过多租户拦截器
        TenantDatabaseHandler.setBypassInterceptor(true);

        List<RdResearchPrimer> primers = rdResearchPrimerService.list();

        primers.forEach(p -> {
            TRdmPrimer primer = new TRdmPrimer();
            primer.setPrimerUuid(CodeGeneratorUtils.getUUID("YWb"))
                    .setPrimerNumber(p.getPrimerNumber())
                    .setPrimerType(p.getPrimerCategory())
                    .setPrimerName(p.getPrimerName())
                    .setPrimerAttribute(p.getPrimerProperty())
                    .setGeneName(p.getGeneName())
                    .setSpecies(p.getSpecies())
                    .setPrimerUtility(p.getPrimerUtility())
                    .setPrimerSequenceF(p.getPrimerSequenceF())
                    .setPrimerSequenceR(p.getPrimerSequenceR())
                    .setGeneId(p.getGeneId())
                    .setStripeSize(p.getBandSize())
                    .setNmNumber(p.getNmNumber())
                    .setGcContent(p.getGcPercentage())
                    .setTmValue(p.getTmValue())
                    .setCds(p.getCds())
                    .setAmplificationFiveEndPosition(p.getAmplificationFragmentB())
                    .setAmplificationThreeEndPosition(p.getAmplificationFragmentA())
                    .setAffiliationDept("粒成生物")
                    .setManagerUuid("17174019016437972")
                    .setManagerName("蹇天燊")
                    .setStressFlag(p.getIsMark())
                    .setRemarks(p.getRemarks())
                    .setDelFlag(p.getIsDelete())
                    .setCreateTime(p.getCreateTime())
                    .setUpdateTime(p.getUpdateTime())
                    .setTenantUuid(GTMA_KE_JI)
            ;

            trdmPrimerService.save(primer);
        });

        TenantDatabaseHandler.setBypassInterceptor(false);
        return "success!";
    }


    @GetMapping("/rdDiseaseAnalysis")
    @Transactional(rollbackFor = Exception.class)
    public String rdDiseaseAnalysis() {
        // 设置标记，绕过多租户拦截器
        TenantDatabaseHandler.setBypassInterceptor(true);

        List<RdDiseaseAnalysis> originModelList = rdDiseaseAnalysisService.list();

        originModelList.forEach(o -> {
            TRdmDataAnalysis analysis = new TRdmDataAnalysis();
            analysis.setDataUuid(CodeGeneratorUtils.getUUID("GB"))
                    .setDataLibraryType("TCGA")
                    .setDiseaseName(o.getDiseaseName())
                    .setDiseaseAbbreviation(o.getDiseaseAbbreviation())
                    .setDataNumber(o.getGeoNumber())
                    .setDataStatus("未开始")
                    .setPlatform(o.getPlatform())
                    .setSpecies(o.getCenter())
                    .setExperimentalGrouping(o.getExperimentalGroup())
                    .setDataType(o.getSampleType())
                    .setDataQuantity(new BigDecimal(o.getQuantity()))
                    .setLiterature(StringUtils.isBlank(o.getLiterature()) ? FILL_CHARACTER : o.getLiterature())
                    .setDataLinkOne(StringUtils.isBlank(o.getDataLinkOne()) ? FILL_CHARACTER : o.getDataLinkOne())
                    .setDataLinkTwo(StringUtils.isBlank(o.getDataLinkTwo()) ? FILL_CHARACTER : o.getDataLinkTwo())
                    .setAffiliationDept("生信部")
                    .setManagerUuid("17120422409436247")
                    .setManagerName("陈佳莹")
                    .setRemarks(o.getRemarks())
                    .setDelFlag(o.getIsDelete())
                    .setCreateTime(o.getCreateTime())
                    .setUpdateTime(o.getUpdateTime())
                    .setTenantUuid(GTMA_KE_JI)
            ;

            trdmDataAnalysisService.save(analysis);
        });

        TenantDatabaseHandler.setBypassInterceptor(false);
        return "success!";
    }

    @GetMapping("/tRdmAnimalModel")
    @Transactional(rollbackFor = Exception.class)
    public String tRdmAnimalModel() {
        // 设置标记，绕过多租户拦截器
        TenantDatabaseHandler.setBypassInterceptor(true);

        List<RdResearchModel> originModelList = rdResearchModelService.list();

        List<RdModelDetail> originDetailList = rdModelDetailService.list();
        Map<Integer, List<RdModelDetail>> originDetailMap = originDetailList.stream().collect(Collectors.groupingBy(RdModelDetail::getResearchInformationId));

        originModelList.forEach(o -> {
            String uuid = CodeGeneratorUtils.getUUID("MX");
            TRdmAnimalModel model = new TRdmAnimalModel();
            String star = o.getStarRating().replace("星", "");
            model.setModelUuid(uuid)
                    .setModelNumber(o.getInformationNumber())
                    .setModelName(o.getInformationName())
                    .setUsingAnimals(o.getAnimalName())
                    .setStarRating(Integer.parseInt(star))
                    .setModelType(o.getTypeId().toString())
                    .setManagerUuid("17120421592016889")
                    .setManagerName("蹇天燊")
                    .setRemarks(o.getNote())
                    .setCreateTime(o.getCreateTime())
                    .setUpdateTime(o.getUpdateTime())
                    .setTenantUuid(GTMA_KE_JI)
            ;

            trdmAnimalModelService.save(model);

            List<RdModelDetail> detailList = originDetailMap.get(o.getId());

            if (CollectionUtils.isEmpty(detailList)) {
                return;
            }

            detailList.forEach(d -> {
                TRdmMoldingTheme theme = new TRdmMoldingTheme();
                theme.setModelUuid(uuid)
                        .setThemeUuid(CodeGeneratorUtils.getUUID("MT"))
                        .setThemeName(d.getDetailInformationTitle())
                        .setCreatorUuid(d.getDetailInformationContent())
                        .setCreatorName("蹇天燊")
                        .setDelFlag(d.getIsValid())
                        .setCreateTime(d.getCreateTime())
                        .setUpdateTime(d.getUpdateTime())
                        .setTenantUuid(GTMA_KE_JI)
                ;

                trdmMoldingThemeService.save(theme);
            });
        });

        List<TRdmMoldingTheme> themes = trdmMoldingThemeService.list();

        themes.forEach(t -> {
            String content = t.getCreatorUuid();

            List<DetailInformation> informations = JSONUtil.toList(content, DetailInformation.class);

            DetailInformation information = informations.get(0);

            List<ItemList> itemLists = information.getList();

            itemLists.forEach(item -> {
                TRdmMoldingDetail detail = new TRdmMoldingDetail();
                String uuid = CodeGeneratorUtils.getUUID("MTD");

                detail.setThemeUuid(t.getThemeUuid())
                        .setDetailUuid(uuid)
                        .setMoldingTitle(item.getName())
                        .setMoldingContent(item.getContent())
                        .setTenantUuid(GTMA_KE_JI)
                ;
                trdmMoldingDetailService.save(detail);

                if (CollectionUtils.isNotEmpty(item.getFileitem())) {
                    item.getFileitem().forEach(f -> {
                        TRdmMoldingFile file = new TRdmMoldingFile();
                        file.setDetailUuid(uuid)
                                .setFileType(0)
                                .setFileName(f.getFileName())
                                .setFilePath(f.getFilePath())
                                .setType(f.getName().split("\\.")[1])
                                .setName(f.getName())
                                .setCreateTime(t.getCreateTime())
                                .setTenantUuid(GTMA_KE_JI)
                        ;

                        trdmMoldingFileService.save(file);
                    });
                }
            });
        });


        TenantDatabaseHandler.setBypassInterceptor(false);
        return "success!";
    }

    @GetMapping("/tRdmArticles")
    @Transactional(rollbackFor = Exception.class)
    public String tRdmArticles() {
        // 设置标记，绕过多租户拦截器
        TenantDatabaseHandler.setBypassInterceptor(true);

        List<RdResearchArticle> originList = rdResearchArticleService.list();

        originList.forEach(o -> {
            TRdmArticles trdmArticles = new TRdmArticles();
            trdmArticles.setArticlesUuid(CodeGeneratorUtils.getUUID("WZ"))
                    .setArticlesNumber(o.getArticleNumber())
                    .setArticleTitle(o.getTitle())
                    .setAuthor(o.getAuthor())
                    .setJournalName(StringUtils.isEmpty(o.getJournalTitle()) ? FILL_CHARACTER : o.getJournalTitle())
                    .setImpactFactor(o.getImpactFactorsIf())
                    .setArticleLink(StringUtils.isEmpty(o.getArticleLink()) ? FILL_CHARACTER : o.getArticleLink())
                    .setTechnicalSupport("位云")
                    .setPublicationTime(o.getPublicationTime())
                    .setAffiliationDept("医学部")
                    .setManagerUuid("17120421592016889")
                    .setManagerName("位云")
                    .setRemarks(o.getRemarks())
                    .setCreateTime(o.getCreateTime())
                    .setUpdateTime(o.getUpdateTime())
                    .setTenantUuid(GTMA_KE_JI)
            ;
            trdmArticlesService.save(trdmArticles);
        });

        TenantDatabaseHandler.setBypassInterceptor(false);
        return "success!";
    }

    @Data
    public class DetailInformation {
        private String DetailInformationTitle;
        private List<ItemList> list;

    }

    @Data
    public class ItemList {
        private String name;
        private String content;
        private List<FileItem> fileitem;
        private List<Object> listfile;

    }

    @Data
    public class FileItem {
        private String name;
        private String fileName;
        private String filePath;

    }
}
