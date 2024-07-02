package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.reminder.date.ReminderDate;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreatedDto;
import work.szczepanskimichal.service.ReminderDateService;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/v1/reminderdate")
@RequiredArgsConstructor
public class ReminderDateController {

    private final ReminderDateService reminderDateService;

    @PostMapping
    public ResponseEntity<ReminderDateCreatedDto> createReminderDate(@RequestBody ReminderDateCreateDto reminderDateCreateDto) {
        return ResponseEntity.ok(reminderDateService.createReminder(reminderDateCreateDto));
    }

    @GetMapping
    public ReminderDate getReminderDate(@RequestParam UUID id) {
        return reminderDateService.getReminderDateById(id);
    }

    @GetMapping("/all")
    public List<ReminderDate> getRemindersByReminder(@RequestParam UUID reminderId) {
        return reminderDateService.getReminderDatesByReminder(reminderId);
    }
}
