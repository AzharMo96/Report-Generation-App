package net.kingitsolutions.reportgenerator.service;

import net.kingitsolutions.reportgenerator.dto.request.ReportRequestDto;
import net.kingitsolutions.reportgenerator.dto.response.ReportResponseDto;
import net.kingitsolutions.reportgenerator.entity.Report;
import org.springframework.core.io.ByteArrayResource;

public interface ReportService {

    public Long initiateReportGeneration(ReportRequestDto reportDto);
    public ReportResponseDto getReportStatus(Long reportId);

    public ByteArrayResource generateReportContent(ReportResponseDto report);
}
