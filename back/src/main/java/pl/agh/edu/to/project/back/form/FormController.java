package pl.agh.edu.to.project.back.form;

import org.springframework.web.bind.annotation.*;
import pl.agh.edu.to.project.back.award.Award;

import java.util.List;

@RestController
@RequestMapping(path = "forms")
public class FormController {
    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping
    public List<Form> getAllForms() {
        return formService.getForms();
    }

    @PatchMapping("/{id}")
    public Form updateFormAward(@RequestBody Award award, @PathVariable int id) {
        return this.formService.updateFormAward(id, award);
    }
}
