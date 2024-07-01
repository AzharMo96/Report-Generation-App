package net.kingitsolutions.reportgenerator.service.custom.impl;

import lombok.extern.slf4j.Slf4j;
import net.kingitsolutions.reportgenerator.dto.request.ReportRequestDto;
import net.kingitsolutions.reportgenerator.dto.response.ReportResponseDto;
import net.kingitsolutions.reportgenerator.entity.Report;
import net.kingitsolutions.reportgenerator.exception.NotFoundException;
import net.kingitsolutions.reportgenerator.repo.ReportRepository;
import net.kingitsolutions.reportgenerator.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

//@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ModelMapper modelMapper;


    public Long initiateReportGeneration(ReportRequestDto reportDto) {

        Report report =modelMapper.map(reportDto,Report.class);
        report = reportRepository.save(report);
        simulateReportGeneration(report);

        ReportResponseDto reportResponseDto =modelMapper.map(report,ReportResponseDto.class);
        return reportResponseDto.getId();
    }

    public ReportResponseDto getReportStatus(Long reportId) {

        Report report = reportRepository.findById(reportId).orElse(null);
        if (report == null) {
//            log.error("report id not Found");
            throw new NotFoundException("report id not Found ");
        }
        ReportResponseDto reportResponseDto =modelMapper.map(report ,ReportResponseDto.class);
        return reportResponseDto;

    }

    public  ByteArrayResource generateReportContent(ReportResponseDto report) {
        String content = String.join("\n",
                "Start Date: " + report.getStartDate(),
                "End Date: " + report.getEndDate(),
                "Random String: " + report.getRandomString());

        ByteArrayResource resource = new ByteArrayResource(content.getBytes());
//        log.info("text file generation finshed");
        return resource;
    }

    private void simulateReportGeneration(Report report) {
        new Thread(() -> {
            try {
                int delay = new Random().nextInt(4501) + 500;
                Thread.sleep(delay);
                report.setStatus("finished");
//                log.info("report generation finshed");
                reportRepository.save(report);
            } catch (InterruptedException e) {
                report.setStatus("failed");
//                log.error("Report generation failed");
                reportRepository.save(report);
            }
        }).start();
    }

}
