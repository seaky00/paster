package pw.seaky.paster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pw.seaky.paster.exception.PasteNotFoundException;
import pw.seaky.paster.model.Paste;
import pw.seaky.paster.service.PasteService;

@RequestMapping("/api")
@Controller
public class PasteController {

    private final PasteService textService;

    @Autowired
    public PasteController(PasteService textService) {
        this.textService = textService;
    }

    @PostMapping(path = "/post")

    public String addPost(Paste text, BindingResult result) {
        if (!result.hasFieldErrors() && !text.getTitle().isEmpty()) {

            textService.addPaste(text);
            return "redirect:/pastes/" + text.getId();
        }
        return "paste";
    }


    @ResponseBody
    @GetMapping(path = "/{id}")
    public Paste getText(@PathVariable String id) {
        try {
            return textService.getPaste(id);
        } catch (PasteNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paste not found!", e);
        }
    }

    /*
    @ResponseBody
    @GetMapping("/all")
    public List<Paste> getAll() {
        return textService.getDao().getPastes();
    }
    */


}
