package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateUpdateDto;
import work.szczepanskimichal.service.ReminderDateService;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/v1/present/reminderdate")
@RequiredArgsConstructor
public class ReminderDateController {

    private final ReminderDateService reminderDateService;

    @PostMapping
    public ResponseEntity<ReminderDateDto> createReminderDate(@RequestBody ReminderDateCreateDto reminderDateCreateDto) {
        return ResponseEntity.ok(reminderDateService.createReminder(reminderDateCreateDto));
    }

    @GetMapping("/{reminderDateId}")
    public ResponseEntity<ReminderDateDto> getReminderDate(@PathVariable UUID reminderDateId) {
        return ResponseEntity.ok(reminderDateService.getReminderDateById(reminderDateId));
    }

    @GetMapping("/byreminder/{reminderId}")
    public ResponseEntity<List<ReminderDateDto>> getRemindersByReminder(@PathVariable UUID reminderId) {
        return ResponseEntity.ok(reminderDateService.getReminderDatesByReminder(reminderId));
    }

    @PatchMapping()
    public ResponseEntity<ReminderDateDto> updateReminderDate(@RequestBody ReminderDateUpdateDto reminderDto) {
        return ResponseEntity.ok(reminderDateService.updateReminderDate(reminderDto));
    }

    @DeleteMapping("/{reminderDateId}")
    public void deleteReminderDate(@PathVariable UUID reminderDateId) {
        reminderDateService.deleteReminderDate(reminderDateId);
    }

}
