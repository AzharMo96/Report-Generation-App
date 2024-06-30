
package net.kingitsolutions.reportgenerator.service.custom.impl;
import net.kingitsolutions.reportgenerator.dto.request.ReportRequestDto;
import net.kingitsolutions.reportgenerator.dto.response.ReportResponseDto;
import net.kingitsolutions.reportgenerator.entity.Report;
import net.kingitsolutions.reportgenerator.exception.NotFoundException;
import net.kingitsolutions.reportgenerator.repo.ReportRepository;
import net.kingitsolutions.reportgenerator.service.util.Support;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @InjectMocks
    private ReportServiceImpl reportService;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitiateReportGeneration() {
        ReportRequestDto requestDto = new ReportRequestDto();
        requestDto.setStartDate(LocalDateTime.from(LocalDateTime.now().minusDays(1)));
        requestDto.setEndDate(LocalDateTime.from(LocalDateTime.now()));

        Report report = new Report();
        report.setId(1L);
        report.setStartDate(requestDto.getStartDate());
        report.setEndDate(requestDto.getEndDate());
        report.setStatus("in progress");
        report.setRandomString(Support.generateRandomString());

        when(modelMapper.map(requestDto, Report.class)).thenReturn(report);
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        ReportResponseDto responseDto = new ReportResponseDto();
        responseDto.setId(1L);
        when(modelMapper.map(report, ReportResponseDto.class)).thenReturn(responseDto);

        Long reportId = reportService.initiateReportGeneration(requestDto);
        assertNotNull(0);
        assertEquals(1L, reportId);

        assertEquals(requestDto.getEndDate(), report.getEndDate());

        assertEquals("in progress", report.getStatus(), "Report status should be 'in progress'");
        verify(modelMapper, times(1)).map(requestDto, Report.class);


    }

    @Test
    void testGetReportStatus() {
        Long reportId = 1L;

        Report report = new Report();
        report.setId(reportId);
        report.setStatus("in progress");

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        ReportResponseDto responseDto = new ReportResponseDto();
        responseDto.setId(reportId);
        responseDto.setStatus("in progress");

        when(modelMapper.map(report, ReportResponseDto.class)).thenReturn(responseDto);

        ReportResponseDto result = reportService.getReportStatus(reportId);
        assertNotNull(result);
        assertEquals("in progress", result.getStatus());
    }

}