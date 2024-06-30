package net.kingitsolutions.reportgenerator.controller;

import net.kingitsolutions.reportgenerator.dto.request.ReportRequestDto;
import net.kingitsolutions.reportgenerator.dto.response.ReportResponseDto;
import net.kingitsolutions.reportgenerator.service.ReportService;
import net.kingitsolutions.reportgenerator.service.util.Support;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    private  ReportService reportService;

    @PostMapping("/initiate")
    public ResponseEntity<Long> initiateReportGeneration(@RequestParam("startDate") String startDateStr,
                                                              @RequestParam("endDate") String endDateStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);
            System.out.print("startDate  "+startDate);
            System.out.print("endDate "+endDate);

            if (startDate == null || endDate == null || !startDate.isBefore(endDate)) {
                return ResponseEntity.badRequest().build();
            }
            ReportRequestDto reportDto =new ReportRequestDto();
            reportDto.setStartDate(startDate);
            reportDto.setEndDate(endDate);
            reportDto.setStatus("in progress");
            reportDto.setRandomString(Support.generateRandomString());

            Long id = reportService.initiateReportGeneration(reportDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(Long.valueOf("Error parsing date-time format should be yyyy-MM-dd HH:mm:ss: " + e.getMessage()));
        }

    }

    @GetMapping("/status")
    public ResponseEntity<String> getReportStatus(@Valid @RequestParam("id") Long reportId) {
        ReportResponseDto report = reportService.getReportStatus(reportId);
        if (report == null) {
            return new ResponseEntity<>("Report not found check the id", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(report.getStatus());
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadReport(@RequestParam("id") Long reportId) {

        ReportResponseDto report = reportService.getReportStatus(reportId);
        if (report == null || !report.getStatus().equals("finished")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String content = Support.generateReportContent(report);
        ByteArrayResource resource = new ByteArrayResource(content.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report_" + reportId + ".txt");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/plain");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .body(resource);

    }



    
}
