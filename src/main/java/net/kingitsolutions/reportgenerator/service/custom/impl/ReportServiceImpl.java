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

//        String randomString = generateRandomString();
//        Report report = new Report();
//        report.setStartDate(startDate);
//        report.setEndDate(endDate);
//        report.setStatus("in progress");
//        report.setRandomString(randomString);
        System.out.println("inside initiateReportGeneration ");

        Report report =modelMapper.map(reportDto,Report.class);
        report = reportRepository.save(report);
        simulateReportGeneration(report);

        ReportResponseDto reportResponseDto =modelMapper.map(report,ReportResponseDto.class);
        System.out.println("report "+report);
        return reportResponseDto.getId();
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
