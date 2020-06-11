package pw.seaky.paster.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pw.seaky.paster.model.Paste;
import pw.seaky.paster.service.PasteService;

@Controller

public class ViewController {

    private final PasteService textService;

    @Autowired
    public ViewController(PasteService textService) {
        this.textService = textService;

    }
    @GetMapping(path = "/")
    public String getHomePage() {
        return "redirect:/home";
    }

    @GetMapping(path = "/home")
    public String getHomepage(Model model) {
        return "home";
    }

    @GetMapping(path = "/paste")
    public String paste(Model model) {
        model.addAttribute(new Paste());
        return "paste";
    }

    @GetMapping(path = "/pastes")
    public String getPaste() {
        return "redirect:/pastes/404";
    }

    @GetMapping(path = "/pastes/{id}")
    public String getPaste(@PathVariable("id") String id, Model model) {
        if (textService.getDao().getPaste(id).isPresent()) {
            Paste text = textService.getDao().getPaste(id).get();
            model.addAttribute("id", text.getId());
            model.addAttribute("title", text.getTitle());
            model.addAttribute("body", text.getBody());
            model.addAttribute("date", text.getCreated());
            return "result";
        } else {
            return "redirect:/pastes/404";
        }

    }


}
