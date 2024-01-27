package com.sportEventReservation.report.domain;

import com.sportEventReservation.report.dto.ReportStatusDto;

enum ReportStatus {
  PENDING,
  DECLINED,
  IN_PROGRESS,
  ACCEPTED;

  ReportStatusDto dto() {
    return ReportStatusDto.valueOf(name());
  }
}
