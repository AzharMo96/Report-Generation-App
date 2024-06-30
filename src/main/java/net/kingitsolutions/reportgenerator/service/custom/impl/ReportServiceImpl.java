package net.kingitsolutions.reportgenerator.service.custom.impl;

import net.kingitsolutions.reportgenerator.dto.request.ReportRequestDto;
import net.kingitsolutions.reportgenerator.dto.response.ReportResponseDto;
import net.kingitsolutions.reportgenerator.entity.Report;
import net.kingitsolutions.reportgenerator.repo.ReportRepository;
import net.kingitsolutions.reportgenerator.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

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
        System.out.println("report "+report);
        return reportResponseDto.getId();
    }

    public ReportResponseDto getReportStatus(Long reportId) {
        Report report = reportRepository.findById(reportId).orElse(null);

        if (report == null) {
            System.out.println("here the no report");
            return null; // or throw an exception if you prefer
        }

        ReportResponseDto reportResponseDto =modelMapper.map(report ,ReportResponseDto.class);

         return reportResponseDto;


    }

    private void simulateReportGeneration(Report report) {
        new Thread(() -> {
            try {
                int delay = new Random().nextInt(4501) + 500;
                Thread.sleep(delay);
                report.setStatus("finished");
                reportRepository.save(report);
            } catch (InterruptedException e) {
                report.setStatus("failed");
                reportRepository.save(report);
            }
        }).start();
    }

}
