package net.kingitsolutions.reportgenerator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
public class ReportResponseDto {

    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String status;

    private String randomString;

    public ReportResponseDto(){}

    public ReportResponseDto(Long id,LocalDateTime startDate,LocalDateTime endDate,String status,String randomString)
    {
        this.id =id;
        this.startDate=startDate;
        this.endDate=endDate;
        this.status=status;
        this.randomString=randomString;
    }




}
