package net.kingitsolutions.reportgenerator.service;

import net.kingitsolutions.reportgenerator.dto.request.ReportRequestDto;

public interface ReportService {

    public Long initiateReportGeneration(ReportRequestDto reportDto);
}
