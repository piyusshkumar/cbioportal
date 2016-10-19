package org.cbioportal.service;

import org.cbioportal.model.CancerStudy;
import org.cbioportal.model.meta.BaseMeta;
import org.cbioportal.service.exception.StudyNotFoundException;

import java.util.List;

public interface StudyService {

    List<CancerStudy> getAllStudies(String projection, Integer pageSize, Integer pageNumber,
                                    String sortBy, String direction);

    BaseMeta getMetaStudies();

    CancerStudy getStudy(String studyId) throws StudyNotFoundException;
}
