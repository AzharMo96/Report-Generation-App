package net.kingitsolutions.reportgenerator.repo;

import net.kingitsolutions.reportgenerator.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
