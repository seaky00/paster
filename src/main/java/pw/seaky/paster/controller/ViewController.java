package pw.seaky.paster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import pw.seaky.paster.exception.PasteNotFoundException;
import pw.seaky.paster.model.Paste;
import pw.seaky.paster.service.PasteService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {

  private final PasteService textService;

  @Autowired
  public ViewController(PasteService textService) {
    this.textService = textService;
  }


  @ModelAttribute("cookie")
  public Boolean hasCookies(HttpServletRequest servletRequest) {
    return servletRequest.getCookies() != null;
  }


  @GetMapping(path = "/")
  public String get() {
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

  @GetMapping(path = "/recent")
  public String renderRecent(Model model, HttpServletRequest request)
          throws PasteNotFoundException {
    String page = "home";
    Cookie[] cookies = request.getCookies();
    List<Paste> valid = new ArrayList<Paste>();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getValue().equals("paste")) {
          Optional<Paste> paste = Optional.ofNullable(this.textService.getPaste(cookie.getName()));
          paste.ifPresent(value -> valid.add(0, value));
        }
        if (valid.size() > 0) {
          page = "recent";
        }
      }
    }
    model.addAttribute("list", valid);
    return page;
  }
}