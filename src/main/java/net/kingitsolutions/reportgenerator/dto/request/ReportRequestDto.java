package net.kingitsolutions.reportgenerator.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ReportRequestDto {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String status;

    private String randomString;

}
