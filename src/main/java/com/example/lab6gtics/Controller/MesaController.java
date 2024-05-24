package com.example.lab6gtics.Controller;

import com.example.lab6gtics.Entity.Mesa;
import com.example.lab6gtics.Repository.MesaRepository;
import com.example.lab6gtics.Repository.ReservaRepository;
import com.example.lab6gtics.Repository.RolRepository;
import com.example.lab6gtics.Repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/Mesas")
public class MesaController {
    final MesaRepository mesaRepository;
    final ReservaRepository reservaRepository;
    final RolRepository rolRepository;
    final UsuarioRepository usuarioRepository;
    public MesaController(MesaRepository mesaRepository , ReservaRepository reservaRepository,RolRepository rolRepository,UsuarioRepository usuarioRepository  ){
        this.mesaRepository = mesaRepository;
        this.reservaRepository = reservaRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }
    @GetMapping("/verMesas")
    public String verMesas(Model model){
        model.addAttribute("listaMesas", mesaRepository.findAll());
        return "/Mesa/MesaLista";
    }

    @GetMapping("/createMesa")
    public String verCrearDisp(Model model , @ModelAttribute("mesa") Mesa mesa){
        return "/Mesa/EditMesa";
    }

    @GetMapping("/editarMesas")
    public String verEdicionCreado(Model model , @ModelAttribute("mesa") Mesa mesa , @RequestParam("id") int idMesa){
        System.out.println("Hola");
        Optional<Mesa> dispAux = mesaRepository.findById(idMesa);
        if(dispAux.isPresent()){
            mesa = dispAux.get();
            model.addAttribute("mesa", mesa);
            System.out.println(mesa.getNombre());
        }
        return "/Mesa/EditMesa";
    }
    @PostMapping("/createEditMesaCompleted")
    public String createEditMesaCompleted(Model model, @ModelAttribute("mesa") @Valid Mesa mesa ,
                                          BindingResult bindingResult, RedirectAttributes attr){
        if(!bindingResult.hasErrors()) {
            mesaRepository.save(mesa);
            return "redirect:/Mesas/verMesas";
        }else{
            System.out.println("HUBO ERRORES D:");
            return "/Mesa/EditMesa";
        }
    }

    @GetMapping("/borrarMesas")
    public String borrarMesa(@RequestParam("id") int id){
        Optional<Mesa> optional = mesaRepository.findById(id);
        if (optional.isPresent()) {
            mesaRepository.deleteById(id);
        }
        return "redirect:/Mesas/verMesas";
    }

}
