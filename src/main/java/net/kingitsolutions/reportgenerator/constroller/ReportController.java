package net.kingitsolutions.reportgenerator.constroller;

import net.kingitsolutions.reportgenerator.dto.request.ReportRequestDto;
import net.kingitsolutions.reportgenerator.entity.Report;
import net.kingitsolutions.reportgenerator.service.ReportService;
import net.kingitsolutions.reportgenerator.service.util.Support;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> initiateReportGeneration(@RequestParam("startDate") String startDateStr,
                                                              @RequestParam("endDate") String endDateStr) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);

            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            if (startDate == null || endDate == null || !startDate.isBefore(endDate)) {
                return ResponseEntity.badRequest().build();
            }
            ReportRequestDto reportDto =new ReportRequestDto();
            reportDto.setStartDate(startDate);
            reportDto.setEndDate(endDate);
            reportDto.setStatus("in progress");
            reportDto.setRandomString(Support.generateRandomString());

            Long id = reportService.initiateReportGeneration(reportDto);
            return ResponseEntity.ok(id);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(Long.valueOf("Error parsing date-time: " + e.getMessage()));
        }





    }
}
