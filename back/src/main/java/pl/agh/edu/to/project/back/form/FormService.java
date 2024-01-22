package pl.agh.edu.to.project.back.form;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.edu.to.project.back.award.Award;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    private final FormRepository formRepository;

    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public Optional<Form> getForm(int id) {
        return formRepository.findById(id);
    }

    public Form saveForm(Form form) {
        return formRepository.save(form);
    }

    @Transactional
    public Form updateFormAward(int id, Award award) {
        Form form = getForm(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Form " + id + " not found"));

        form.setAward(award);
        saveForm(form);

        return form;
    }

    public List<Form> getForms() {
        return formRepository.findAll();
    }
}
