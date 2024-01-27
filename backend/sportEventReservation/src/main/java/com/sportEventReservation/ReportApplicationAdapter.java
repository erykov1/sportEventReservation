package com.sportEventReservation;

import com.google.api.services.drive.Drive;
import com.sportEventReservation.email.domain.EmailService;
import com.sportEventReservation.payment.domain.PaymentFacade;
import com.sportEventReservation.payment.dto.CreatePaymentDto;
import com.sportEventReservation.payment.dto.PaymentDto;
import com.sportEventReservation.report.domain.ReportFacade;
import com.sportEventReservation.report.dto.CreateReportDto;
import com.sportEventReservation.report.dto.ReportDto;
import com.sportEventReservation.report.dto.UpdateReportDto;
import com.sportEventReservation.sportEvent.domain.SportEventFacade;
import io.camunda.zeebe.client.api.worker.JobClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportApplicationAdapter {
  ReportFacade reportFacade;
  SportEventFacade sportEventFacade;
  PaymentFacade paymentFacade;
  EmailService emailService;

  @JobWorker(type = "addNewReport")
  public Map<String, Object> addNewReport(final JobClient client, final ActivatedJob job) {
    log.info("Creating new report");
    HashMap<String, Object> jobResultVariables = new HashMap<>();
    CreateReportDto createReport = CreateReportDto.builder()
        .sportEventId(UUID.fromString(job.getVariablesAsMap().get("sportEventId").toString()))
        .name((String) job.getVariablesAsMap().get("name"))
        .surname((String) job.getVariablesAsMap().get("surname"))
        .email((String) job.getVariablesAsMap().get("email"))
        .build();
    ReportDto report = reportFacade.createReport(createReport);

    boolean availableToReport = !reportFacade.checkIfReachMaxParticipants(UUID.fromString(job.getVariablesAsMap().get("sportEventId").toString()));
//    job.getVariablesAsMap().put("reportId", report.getReportId());
//    job.getVariablesAsMap().put("availableToReport", availableToReport);
    jobResultVariables.put("reportId", report.getReportId());
    jobResultVariables.put("availableToReport", availableToReport);
    jobResultVariables.put("email", report.getEmail());
    reportFacade.setInProgress(report.getReportId());
    CreatePaymentDto createPayment = CreatePaymentDto.builder()
        .email(report.getEmail())
        .reportId(report.getReportId())
        .sportEventId(report.getSportEventId())
        .build();
    PaymentDto payment = paymentFacade.makePayment(createPayment);
    jobResultVariables.put("paymentId", payment.getPaymentId());
    log.info("successfully added new report");
    return jobResultVariables;
  }

  @JobWorker(type = "checkIfReachMaxParticipants")
  public Map<String, Object> checkIfSeatIsFree(final JobClient client, final ActivatedJob job) {
    log.info("checking if sport event reach max participants");
    HashMap<String, Object> jobResultVariables = new HashMap<>();
    boolean hasReachMaxParticipants = reportFacade.checkIfReachMaxParticipants((UUID) job.getVariablesAsMap().get("sportEventId"));
    if (hasReachMaxParticipants) {
      jobResultVariables.put("availableToReport", true);
    } else {
      jobResultVariables.put("availableToReport", false);
    }
    log.info("successfully checked if sport event reach max participants");
    return jobResultVariables;
  }

  @JobWorker(type = "setInProgress")
  public Map<String, Object> setInProgress(final JobClient client, final ActivatedJob job) {
    log.info("Blocking current report");
    HashMap<String, Object> jobResultVariables = new HashMap<>();
    ReportDto currentReport = reportFacade.setInProgress((UUID) job.getVariablesAsMap().get("reportId"));
    log.info("successfully blocked report");
    return jobResultVariables;
  }

  @JobWorker(type = "makePayment")
  public Map<String, Object> makePayment(final JobClient client, final ActivatedJob job) {
    log.info("Making payment");
    HashMap<String, Object> jobResultVariables = new HashMap<>();
    CreatePaymentDto createPayment = CreatePaymentDto.builder()
        .email((String) job.getVariablesAsMap().get("email"))
        .reportId((UUID) job.getVariablesAsMap().get("reportId"))
        .sportEventId((UUID) job.getVariablesAsMap().get("sportEventId"))
        .build();
    PaymentDto payment = paymentFacade.makePayment(createPayment);
    jobResultVariables.put("payment", payment);
    jobResultVariables.put("sportEventId", payment.getSportEventId());
    jobResultVariables.put("email", payment.getEmail());
    log.info("successfully create payment");
    return jobResultVariables;
  }

  @JobWorker(type = "finalizePayment")
  public Map<String, Object> finalizePayment(final JobClient client, final ActivatedJob job) {
    log.info("Finalizing payment");
    HashMap<String, Object> jobResultVariables = new HashMap<>();
    if ((boolean) job.getVariablesAsMap().get("makePayment")) {
      PaymentDto acceptedPayment = paymentFacade.acceptPayment(UUID.fromString(job.getVariablesAsMap().get("paymentId").toString()));
      jobResultVariables.put("payment", acceptedPayment);
      jobResultVariables.put("sportEventId", acceptedPayment.getSportEventId());
      jobResultVariables.put("email", acceptedPayment.getEmail());
      jobResultVariables.put("status", acceptedPayment.getPaymentStatus());
      log.info("successfully accepted payment");
      return jobResultVariables;
    } else {
      PaymentDto canceledPayment = paymentFacade.cancelPayment(UUID.fromString(job.getVariablesAsMap().get("paymentId").toString()));
      jobResultVariables.put("payment", canceledPayment);
      jobResultVariables.put("sportEventId", canceledPayment.getSportEventId());
      jobResultVariables.put("email", canceledPayment.getEmail());
      jobResultVariables.put("status", canceledPayment.getPaymentStatus());
      log.info("successfully canceled payment");
      return jobResultVariables;
    }
  }

  @JobWorker(type = "confirmReport")
  public Map<String, Object> confirmReport(final JobClient client, final ActivatedJob job) {
    log.info("confirming/declining report");
    HashMap<String, Object> jobResultVariables = new HashMap<>();
    if ((boolean) job.getVariablesAsMap().get("isPaid")) {
      UpdateReportDto acceptedReport = reportFacade.acceptReport(UUID.fromString(job.getVariablesAsMap().get("reportId").toString()));
      jobResultVariables.put("reportId", acceptedReport.getReportId());
      log.info("successfully accepted report");
      return jobResultVariables;
    } else {
      UpdateReportDto declinedReport = reportFacade.declineReport(UUID.fromString(job.getVariablesAsMap().get("reportId").toString()));
      jobResultVariables.put("reportId", declinedReport.getReportId());
      log.info("successfully declined report");
      return jobResultVariables;
    }
  }

  @JobWorker(type = "declineReport")
  public Map<String, Object> declineReport(final JobClient client, final ActivatedJob job) {
    log.info("declining report");
    HashMap<String, Object> jobResultVariables = new HashMap<>();
    UpdateReportDto declinedReport = reportFacade.declineReport((UUID) job.getVariablesAsMap().get("reportId"));
    jobResultVariables.put("reportId", declinedReport.getReportId());
    jobResultVariables.put("status", declinedReport.getStatus());
    log.info("successfully declined report");
    return jobResultVariables;
  }

  @JobWorker(type = "sendEmail")
  public Map<String, Object> sendEmail(final JobClient client, final ActivatedJob job) {
    log.info("sending email");
    HashMap<String, Object> jobResultVariables = new HashMap<>();
    emailService.sendEmail((String) job.getVariablesAsMap().get("type").toString(), (String) job.getVariablesAsMap().get("to"));
    log.info("successfully send email");
    return jobResultVariables;
  }
}
