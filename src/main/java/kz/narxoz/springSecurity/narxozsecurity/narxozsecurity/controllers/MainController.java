package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.controllers;


import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model.*;
import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PlayersRepository playersRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TrophyRepository trophyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ImageRepository imageRepository;







    @GetMapping(value = "/")
    public String indexPage(Model model){
        List<Players> players = playersRepository.findAll();
        model.addAttribute("player" , players);


        return "index";
    }

    @GetMapping(value = "/home")
    public String home(Model model){
        List<Players> players = playersRepository.findAll();
        model.addAttribute("player" , players);

        return "Players";
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "login";
    }




    @PostMapping(value = "/adduser")
    public String adduser(@RequestParam(name = "user_email")String email,
                          @RequestParam(name = "user_password")String password,
                          @RequestParam(name = "user_full_name")String name){
            Users users = new Users();
            users.setEmail(email);
            users.setPassword(password);
            users.setFullName(name);
            userRepository.save(users);

        return "redirect:/login";
    }



    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "profile";
    }

    @GetMapping(value = "/adminpanel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String admin(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "adminpanel";
    }





    @GetMapping(value = "/moderatorpanel")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String moderator(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "moderatorpanel";
    }

    @GetMapping(value = "/403")
    public String accessDeniedPage(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "403";
    }

    private Users getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users currentUser = (Users) authentication.getPrincipal();
            return currentUser;
        }
        return null;
    }

    @GetMapping(value = "/allPlayers")
    public String AllPlayers(Model model){
        List <Players> players = playersRepository.findAll();
        model.addAttribute("player" , players);

        List <Position> positions = positionRepository.findAll();
        model.addAttribute("position" , positions);

        List <Img> image = imageRepository.findAll();
        model.addAttribute("image" , image);


        return "AllStudents";
    }
    @PostMapping(value = "/addPlayer")
    public String addPlayer(@RequestParam(name = "name")String name,
                            @RequestParam(name = "surname")String surname,
                            @RequestParam(name = "birthdate")String birthdate,
                            @RequestParam(name = "pos_id") Long posId){
        Position position = positionRepository.findById(posId).orElse(null);
        if(position != null) {
            Players players = new Players();
            players.setName(name);
            players.setSurname(surname);
            players.setDate(birthdate);
            players.setPosition(position);
            playersRepository.save(players);
        }

        return "redirect:/allPlayers";
    }

    @GetMapping(value = "/setting")
    public String setting(Model model){
        List<Users> users = userRepository.findAll();
        model.addAttribute("user" , users);
        return "setting";
    }
    @GetMapping(value = "/allTrophy")
    public String AddTrophy(Model model){
        List<Trophy> trophies = trophyRepository.findAll();
        model.addAttribute("trophy" , trophies);
        return "Cities";
    }
    @PostMapping(value = "/addTrophy")
    public String addTrophy(@RequestParam(name = "name")String name,
                            @RequestParam(name = "year")int year){

        Trophy trophy = new Trophy();
        trophy.setName(name);
        trophy.setYear(year);

        trophyRepository.save(trophy);
        return "redirect:/allTrophy";
    }

    @GetMapping(value = "/details/{id}")
    public String details(@PathVariable(name = "id")Long id , Model model){
        Players players = playersRepository.findById(id).orElse(null);
        model.addAttribute("player" , players);

        List <Position> position = positionRepository.findAll();
        model.addAttribute("position" , position);

        List <Img> image = imageRepository.findAll();
        model.addAttribute("image" , image);
        return "Details";
    }

    @PostMapping(value = "/savePlayer")
    public String savePlayer(@RequestParam(name = "id")Long id,
                             @RequestParam(name = "name")String name,
                             @RequestParam(name = "surname")String surname,
                             @RequestParam(name = "birthdate")String birthdate,
                             @RequestParam(name = "pos_id")Long posId){
        Players players = playersRepository.findById(id).orElse(null);
        Position position = positionRepository.findById(posId).orElse(null);
        if(players != null && position != null) {
            players.setName(name);
            players.setSurname(surname);
            players.setDate(birthdate);
            players.setPosition(position);
            playersRepository.save(players);
            return "redirect:/details/" + id;
        }
        return "redirect:/allPlayers";
    }

    @PostMapping(value = "/deletePlayer")
    public String deletePlayer(@RequestParam(name = "id")Long id){
        Players players = playersRepository.findById(id).orElse(null);
        if(players != null) {
            playersRepository.delete(players);
        }
        return "redirect:/allPlayers";
    }


    @GetMapping(value = "/detailst/{id}")
    public String detailsTrophy(@PathVariable(name = "id")Long id , Model model){

        Trophy trophy = trophyRepository.findById(id).orElse(null);
        model.addAttribute("trophy" , trophy);
        return "Detailscity";
    }

    @PostMapping(value = "/saveTrophy")
    public String saveTrophy(@RequestParam(name = "id")Long id,
                             @RequestParam(name = "name")String name,
                             @RequestParam(name = "year")int year){
        Trophy trophy = trophyRepository.findById(id).orElse(null);
        if(trophy!=null){
            trophy.setName(name);
            trophy.setYear(year);

            trophyRepository.save(trophy);
            return "redirect:/detailst/" + id;
        }

        return "redirect:/allTrophy";
    }

    @PostMapping(value = "/deleteTrophy")
    public String deleteTrophy(@RequestParam(name = "id")Long id){
        Trophy trophy = trophyRepository.findById(id).orElse(null);
        if(trophy != null) {
            trophyRepository.delete(trophy);
        }
        return "redirect:/allTrophy";
    }

    @GetMapping(value = "/detailsu/{id}")
    public String detailsu(@PathVariable(name = "id")Long id , Model model){
        Users users = userRepository.findById(id).orElse(null);
        model.addAttribute("user" , users);

        return "detailsu";
    }

    @PostMapping(value = "/saveUser")
    public String savePlayer(@RequestParam(name = "id")Long id,
                             @RequestParam(name = "email")String email,
                             @RequestParam(name = "full_name")String name){
        Users users = userRepository.findById(id).orElse(null);
        if(users != null) {
            users.setEmail(email);
            users.setFullName(name);
            userRepository.save(users);
            return "redirect:/detailsu/" + id;
        }
        return "redirect:/profile";
    }



    @PostMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam(name = "id")Long id){
        Users users = userRepository.findById(id).orElse(null);
        if(users != null) {
            userRepository.delete(users);
        }
        return "redirect:/profile";
    }

    @GetMapping(value = "/players")
    public String players(Model model){
        List<Players> players = playersRepository.findAll();
        model.addAttribute("player" , players);

        List <Img> image = imageRepository.findAll();
        model.addAttribute("image" , image);


        return "Players";
    }



    @GetMapping(value = "/trophy")
    public String trophy(Model model){
        List<Trophy> trophies = trophyRepository.findAll();
        model.addAttribute("trophy" , trophies);


        return "trophy";
    }


}
