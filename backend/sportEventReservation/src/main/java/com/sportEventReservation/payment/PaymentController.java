package com.sportEventReservation.payment;

import com.google.api.services.drive.Drive;
import com.sportEventReservation.adapter.TaskListService;
import com.sportEventReservation.payment.domain.PaymentFacade;
import com.sportEventReservation.payment.dto.CreatePaymentDto;
import com.sportEventReservation.payment.dto.PaymentDto;
import io.camunda.tasklist.dto.Task;
import io.camunda.tasklist.dto.TaskList;
import io.camunda.tasklist.dto.TaskState;
import io.camunda.tasklist.exception.TaskListException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class PaymentController {
  PaymentFacade paymentFacade;
  TaskListService taskListService;

  @GetMapping("/tasks")
  ResponseEntity<Task> getTasksForPayment() {
    Task task = new Task();
    try {
      task = taskListService.getTaskList(TaskState.CREATED, null).first();
    } catch (Exception e) {

    }
    return ResponseEntity.ok(task);
  }

  @PostMapping("/create")
  ResponseEntity<PaymentDto> createPayment(@RequestBody CreatePaymentDto createPayment) {
    return ResponseEntity.ok(paymentFacade.makePayment(createPayment));
  }

  @PatchMapping("/accept/{taskId}/{paymentId}")
  ResponseEntity<Void> acceptPayment(@PathVariable UUID paymentId, @PathVariable String taskId) throws TaskListException {
    Map<String,Object> variables = new HashMap<>();
    variables.put("makePayment", true);
    paymentFacade.acceptPayment(paymentId);
    taskListService.completeTask(taskId, variables);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/cancel/{taskId}/{paymentId}")
  ResponseEntity<Void> cancelPayment(@PathVariable UUID paymentId, @PathVariable String taskId) throws TaskListException {
    Map<String,Object> variables = new HashMap<>();
    variables.put("makePayment", false);
    paymentFacade.cancelPayment(paymentId);
    taskListService.completeTask(taskId, variables);
    return ResponseEntity.ok().build();
  }
}
