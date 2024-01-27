package com.sportEventReservation.report.domain;

import com.sportEventReservation.report.dto.*;
import com.sportEventReservation.report.exception.FullParticipantsException;
import com.sportEventReservation.report.exception.ReportNotFoundException;
import com.sportEventReservation.sportEvent.exception.NotExistingSportEventException;
import com.sportEventReservation.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportFacade {
  ReportRepository reportRepository;
  SportEventAssignRepository sportEventAssignRepository;
  InstantProvider instantProvider;

  public ReportDto createReport(CreateReportDto createReportDto) {
//    if (checkIfReachMaxParticipants(createReportDto.getSportEventId())) {
//      throw new FullParticipantsException("Full participants reached");
//    }
    Report saveReport = Report.builder()
        .reportId(UUID.randomUUID())
        .name(createReportDto.getName())
        .surname(createReportDto.getSurname())
        .email(createReportDto.getEmail())
        .reportStatus(ReportStatus.PENDING)
        .reportedAt(instantProvider.now())
        .sportEventId(createReportDto.getSportEventId())
        .build();
    return reportRepository.save(saveReport).dto();
  }

  public ReportDto setInProgress(UUID reportId) {
    Report report = reportRepository.findByReportId(reportId).orElseThrow(() -> new ReportNotFoundException("Report not found"));
    report = report.toBuilder()
        .reportStatus(ReportStatus.IN_PROGRESS)
        .build();
    return reportRepository.save(report).dto();
  }

  public UpdateReportDto acceptReport(UUID reportId) {
    Report acceptReport = reportRepository.findByReportId(reportId)
        .orElseThrow(() -> new ReportNotFoundException("Report with such id not found"));
    acceptReport = acceptReport.toBuilder()
        .reportStatus(ReportStatus.ACCEPTED)
        .build();
    reportRepository.save(acceptReport).dto();
    return new UpdateReportDto(reportId, ReportStatus.ACCEPTED.dto());
  }

  public UpdateReportDto declineReport(UUID reportId) {
    Report declineReport = reportRepository.findByReportId(reportId)
        .orElseThrow(() -> new ReportNotFoundException("Report with such id not found"));
    declineReport = declineReport.toBuilder()
        .reportStatus(ReportStatus.DECLINED)
        .build();
    reportRepository.save(declineReport).dto();
    return new UpdateReportDto(reportId, ReportStatus.DECLINED.dto());
  }

  public List<ReportDto> getAllReportsByStatus(String reportStatus) {
    return reportRepository.findAll().stream()
        .filter(report -> report.dto().getReportStatus().name().equals(reportStatus))
        .map(Report::dto)
        .collect(Collectors.toList());
  }

  public List<ReportDto> findAllReports() {
    return reportRepository.findAll().stream()
        .map(Report::dto)
        .collect(Collectors.toList());
  }

  public Long countAllSportEventReports(UUID sportEventId) {
    return reportRepository.countAllAcceptedAndInProgress(sportEventId);
  }

  public boolean checkIfReachMaxParticipants(UUID sportEventId) {
    Long currentParticipants = reportRepository.countAllAcceptedAndInProgress(sportEventId);
    Long maxParticipants = sportEventAssignRepository.findSportEventBySportEventId(sportEventId)
        .map(SportEventAssign::dto)
        .map(SportEventAssignDto::getMaxParticipants)
        .orElseThrow(() -> new NotExistingSportEventException("sport event does not exist"));
    return currentParticipants >= maxParticipants;
  }

//  public ReportDto getLatestReport() {
//    return reportRepository.
//  }

  @EventListener
  void onSportEventCreate(SportEventAssignDto sportEventPublish) {
    SportEventAssign sportEventAssign = SportEventAssign.builder()
        .sportEventId(sportEventPublish.getSportEventId())
        .maxParticipants(sportEventPublish.getMaxParticipants())
        .registrationDeadline(sportEventPublish.getRegistrationDeadline())
        .eventTime(sportEventPublish.getEventTime())
        .build();
    sportEventAssignRepository.save(sportEventAssign).dto();
  }
}
