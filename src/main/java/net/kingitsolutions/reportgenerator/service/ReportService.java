package net.kingitsolutions.reportgenerator.service;

import net.kingitsolutions.reportgenerator.dto.request.ReportRequestDto;
import net.kingitsolutions.reportgenerator.entity.Report;

public interface ReportService {

    public Long initiateReportGeneration(ReportRequestDto reportDto);
    public Report getReportStatus(Long reportId);
}
