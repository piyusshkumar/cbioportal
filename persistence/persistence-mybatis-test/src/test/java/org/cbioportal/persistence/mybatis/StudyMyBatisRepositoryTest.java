package org.cbioportal.persistence.mybatis;

import org.cbioportal.model.CancerStudy;
import org.cbioportal.model.TypeOfCancer;
import org.cbioportal.model.meta.BaseMeta;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testContextDatabase.xml")
@Configurable
public class StudyMyBatisRepositoryTest {

    @Autowired
    private StudyMyBatisRepository studyMyBatisRepository;

    @Test
    public void getAllStudiesIdProjection() throws Exception {

        List<CancerStudy> result = studyMyBatisRepository.getAllStudies("ID", null, null, null, null);

        Assert.assertEquals(2, result.size());
        CancerStudy cancerStudy = result.get(0);
        Assert.assertEquals((Integer) 1, cancerStudy.getCancerStudyId());
        Assert.assertEquals("study_tcga_pub", cancerStudy.getCancerStudyIdentifier());
        Assert.assertNull(cancerStudy.getTypeOfCancer());
    }

    @Test
    public void getAllStudiesSummaryProjection() throws Exception {

        List<CancerStudy> result = studyMyBatisRepository.getAllStudies("SUMMARY", null, null, null, null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Assert.assertEquals(2, result.size());
        CancerStudy cancerStudy = result.get(0);
        Assert.assertEquals((Integer) 1, cancerStudy.getCancerStudyId());
        Assert.assertEquals("study_tcga_pub", cancerStudy.getCancerStudyIdentifier());
        Assert.assertEquals("brca", cancerStudy.getTypeOfCancerId());
        Assert.assertEquals("Breast Invasive Carcinoma (TCGA, Nature 2012)", cancerStudy.getName());
        Assert.assertEquals("BRCA (TCGA)", cancerStudy.getShortName());
        Assert.assertEquals("<a href=\\\"http://cancergenome.nih.gov/\\\">The Cancer Genome Atlas (TCGA)</a> Breast" +
                " Invasive Carcinoma project. 825 cases.<br><i>Nature 2012.</i> <a href=\\\"http://tcga-data.nci." +
                "nih.gov/tcga/\\\">Raw data via the TCGA Data Portal</a>.", cancerStudy.getDescription());
        Assert.assertEquals(true, cancerStudy.getPublicStudy());
        Assert.assertEquals("23000897", cancerStudy.getPmid());
        Assert.assertEquals("TCGA, Nature 2012", cancerStudy.getCitation());
        Assert.assertEquals("SU2C-PI3K;PUBLIC;GDAC", cancerStudy.getGroups());
        Assert.assertEquals((Integer)0 , cancerStudy.getStatus());
        Assert.assertEquals(simpleDateFormat.parse("2011-12-18 13:17:17"), cancerStudy.getImportDate());
        Assert.assertNull(cancerStudy.getTypeOfCancer());
    }

    @Test
    public void getAllStudiesDetailedProjection() throws Exception {

        List<CancerStudy> result = studyMyBatisRepository.getAllStudies("DETAILED", null, null, null, null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Assert.assertEquals(2, result.size());
        CancerStudy cancerStudy = result.get(0);
        Assert.assertEquals((Integer) 1, cancerStudy.getCancerStudyId());
        Assert.assertEquals("study_tcga_pub", cancerStudy.getCancerStudyIdentifier());
        Assert.assertEquals("brca", cancerStudy.getTypeOfCancerId());
        Assert.assertEquals("Breast Invasive Carcinoma (TCGA, Nature 2012)", cancerStudy.getName());
        Assert.assertEquals("BRCA (TCGA)", cancerStudy.getShortName());
        Assert.assertEquals("<a href=\\\"http://cancergenome.nih.gov/\\\">The Cancer Genome Atlas (TCGA)</a> Breast" +
                " Invasive Carcinoma project. 825 cases.<br><i>Nature 2012.</i> <a href=\\\"http://tcga-data.nci." +
                "nih.gov/tcga/\\\">Raw data via the TCGA Data Portal</a>.", cancerStudy.getDescription());
        Assert.assertEquals(true, cancerStudy.getPublicStudy());
        Assert.assertEquals("23000897", cancerStudy.getPmid());
        Assert.assertEquals("TCGA, Nature 2012", cancerStudy.getCitation());
        Assert.assertEquals("SU2C-PI3K;PUBLIC;GDAC", cancerStudy.getGroups());
        Assert.assertEquals((Integer)0 , cancerStudy.getStatus());
        Assert.assertEquals(simpleDateFormat.parse("2011-12-18 13:17:17"), cancerStudy.getImportDate());
        TypeOfCancer typeOfCancer = cancerStudy.getTypeOfCancer();
        Assert.assertEquals("brca", typeOfCancer.getTypeOfCancerId());
        Assert.assertEquals("Breast Invasive Carcinoma", typeOfCancer.getName());
        Assert.assertEquals("breast,breast invasive", typeOfCancer.getClinicalTrialKeywords());
        Assert.assertEquals("HotPink", typeOfCancer.getDedicatedColor());
        Assert.assertEquals("Breast", typeOfCancer.getShortName());
        Assert.assertEquals("tissue", typeOfCancer.getParent());
    }

    @Test
    public void getAllStudiesSummaryProjection1PageSize() throws Exception {

        List<CancerStudy> result = studyMyBatisRepository.getAllStudies("SUMMARY", 1, 0, null, null);

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void getAllStudiesSummaryProjectionCancerStudyIdentifierSort() throws Exception {

        List<CancerStudy> result = studyMyBatisRepository.getAllStudies("SUMMARY", null, null, "cancerStudyIdentifier",
                "ASC");

        Assert.assertEquals(2, result.size());
        Assert.assertEquals("acc_tcga", result.get(0).getCancerStudyIdentifier());
        Assert.assertEquals("study_tcga_pub", result.get(1).getCancerStudyIdentifier());
    }

    @Test
    public void getMetaStudies() throws Exception {

        BaseMeta result = studyMyBatisRepository.getMetaStudies();

        Assert.assertEquals((Integer)2, result.getTotalCount());
    }

    @Test
    public void getStudyNullResult() throws Exception {

        CancerStudy result = studyMyBatisRepository.getStudy("invalid_study");

        Assert.assertNull(result);
    }

    @Test
    public void getStudy() throws Exception {

        CancerStudy result = studyMyBatisRepository.getStudy("study_tcga_pub");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Assert.assertEquals((Integer) 1, result.getCancerStudyId());
        Assert.assertEquals("study_tcga_pub", result.getCancerStudyIdentifier());
        Assert.assertEquals("brca", result.getTypeOfCancerId());
        Assert.assertEquals("Breast Invasive Carcinoma (TCGA, Nature 2012)", result.getName());
        Assert.assertEquals("BRCA (TCGA)", result.getShortName());
        Assert.assertEquals("<a href=\\\"http://cancergenome.nih.gov/\\\">The Cancer Genome Atlas (TCGA)</a> Breast" +
                " Invasive Carcinoma project. 825 cases.<br><i>Nature 2012.</i> <a href=\\\"http://tcga-data.nci." +
                "nih.gov/tcga/\\\">Raw data via the TCGA Data Portal</a>.", result.getDescription());
        Assert.assertEquals(true, result.getPublicStudy());
        Assert.assertEquals("23000897", result.getPmid());
        Assert.assertEquals("TCGA, Nature 2012", result.getCitation());
        Assert.assertEquals("SU2C-PI3K;PUBLIC;GDAC", result.getGroups());
        Assert.assertEquals((Integer)0 , result.getStatus());
        Assert.assertEquals(simpleDateFormat.parse("2011-12-18 13:17:17"), result.getImportDate());
        TypeOfCancer typeOfCancer = result.getTypeOfCancer();
        Assert.assertEquals("brca", typeOfCancer.getTypeOfCancerId());
        Assert.assertEquals("Breast Invasive Carcinoma", typeOfCancer.getName());
        Assert.assertEquals("breast,breast invasive", typeOfCancer.getClinicalTrialKeywords());
        Assert.assertEquals("HotPink", typeOfCancer.getDedicatedColor());
        Assert.assertEquals("Breast", typeOfCancer.getShortName());
        Assert.assertEquals("tissue", typeOfCancer.getParent());
    }
}
