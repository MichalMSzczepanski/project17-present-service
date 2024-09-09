package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.reminder.ReminderCreateDto;
import work.szczepanskimichal.model.reminder.ReminderDto;
import work.szczepanskimichal.model.reminder.ReminderUpdateDto;
import work.szczepanskimichal.service.reminder.ReminderService;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/v1/present/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping()
    public ResponseEntity<ReminderDto> createReminder(@RequestBody ReminderCreateDto reminderCreateDto) {
        return ResponseEntity.ok(reminderService.createReminder(reminderCreateDto));
    }

    @GetMapping("/{reminderId}")
    public ResponseEntity<ReminderDto> getReminder(@PathVariable UUID reminderId) {
        return ResponseEntity.ok(reminderService.getReminderDtoById(reminderId));
    }

    @GetMapping("/byoccasion/{occasionId}")
    public ResponseEntity<List<ReminderDto>> getRemindersByOccasion(@PathVariable UUID occasionId) {
        return ResponseEntity.ok(reminderService.getRemindersByOccasion(occasionId));
    }

    @PatchMapping()
    public ResponseEntity<ReminderDto> updateReminder(@RequestBody ReminderUpdateDto reminderDto) {
        return ResponseEntity.ok(reminderService.updateReminder(reminderDto));
    }

    @DeleteMapping("/{reminderId}")
    public void deleteReminder(@PathVariable UUID reminderId) {
        reminderService.deleteReminder(reminderId);
    }
}
