package com.sportEventReservation.report;

import com.sportEventReservation.adapter.TaskListService;
import com.sportEventReservation.report.domain.ReportFacade;
import com.sportEventReservation.report.dto.CreateReportDto;
import com.sportEventReservation.report.dto.ReportDto;
import com.sportEventReservation.report.dto.ReportStatusDto;
import com.sportEventReservation.report.dto.UpdateReportDto;
import com.sportEventReservation.utils.InstantProvider;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ReportController {
  ReportFacade reportFacade;
  TaskListService taskListService;
  @Qualifier("zeebeClientLifecycle")
  ZeebeClient zeebeClient;

  @PostMapping("/start")
  public void startProcessInstance(@RequestBody Map<String, Object> variables) {
    zeebeClient.newCreateInstanceCommand()
        .bpmnProcessId("sport_event")
        .latestVersion()
        .variables(variables)
        .send();
  }

  @PostMapping("/create")
  ResponseEntity<ReportDto> createReport(@RequestBody CreateReportDto report) {
    return ResponseEntity.ok(reportFacade.createReport(report));
  }

  @PatchMapping("/inProgress/{reportId}")
  ResponseEntity<ReportDto> setInProgress(@PathVariable UUID reportId) {
    return ResponseEntity.ok(reportFacade.setInProgress(reportId));
  }

  @PatchMapping("/accept/{reportId}")
  ResponseEntity<UpdateReportDto> acceptReport(@PathVariable UUID reportId) {
    return ResponseEntity.ok(reportFacade.acceptReport(reportId));
  }

  @PatchMapping("/decline/{reportId}")
  ResponseEntity<UpdateReportDto> declineReport(@PathVariable UUID reportId) {
    return ResponseEntity.ok(reportFacade.declineReport(reportId));
  }

  @GetMapping("/all")
  ResponseEntity<List<ReportDto>> findReports() {
    return ResponseEntity.ok(reportFacade.findAllReports());
  }

  @GetMapping("/count/maxParticipants/{sportEventId}")
  ResponseEntity<Long> countReportsForSportEvent(@PathVariable UUID sportEventId) {
    return ResponseEntity.ok(reportFacade.countAllSportEventReports(sportEventId));
  }
//
//  @GetMapping("/check/maxParticipants/{sportEventId}")
//  ResponseEntity<Boolean> checkIfReachMaxParticipants(@PathVariable UUID sportEventId) {
//    return ResponseEntity.ok(reportFacade.checkIfReachMaxParticipants(sportEventId));
//  }

}
