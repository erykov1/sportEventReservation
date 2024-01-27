package com.sportEventReservation.report.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface ReportRepository extends JpaRepository<Report, UUID> {
  //List<Report> findAllReportsByUsername(String username);
  Optional<Report> findByReportId(UUID reportId);
  List<Report> findAllReportsBySportEventId(UUID sportEventId);
  @Query("SELECT COUNT(r) FROM Report r WHERE r.reportStatus IN ('ACCEPTED', 'IN_PROGRESS') AND r.sportEventId = :sportEventId")
  Long countAllAcceptedAndInProgress(@Param("sportEventId") UUID sportEventId);
}
