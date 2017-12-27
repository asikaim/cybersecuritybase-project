package sec.project.controller;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        if(!signupRepository.findByName(name).isEmpty()){
            return "redirect:/duplicate?name=" + name;
        } else {
            signupRepository.save(new Signup(name, address));
            return "done";
        }
    }
    
    @RequestMapping(value = "/duplicate", method = RequestMethod.GET)
    @ResponseBody
    public String duplicate(@RequestParam String name){
        if (signupRepository.findByName(name).isEmpty()){
            return name + " hasn't been registered.";
        } else {
            return name + " has already been registered with address " +
                    signupRepository.findByName(name).get(0).getAddress() + ".";
        }
    }
    
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String loadPasswordForm(){
        return "reset";
    }
    
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    @ResponseBody
    public String submitPasswordForm(@RequestParam String password, @RequestParam String confirm){
        if(Objects.equals(password, confirm)){
            return "Password could be reseted ;)";
        } else {
            return "Password not confirmed";
        }
    }

}
