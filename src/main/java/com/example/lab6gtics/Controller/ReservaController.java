package com.example.lab6gtics.Controller;

import com.example.lab6gtics.Entity.Mesa;
import com.example.lab6gtics.Entity.Reserva;
import com.example.lab6gtics.Entity.Usuario;
import com.example.lab6gtics.Repository.MesaRepository;
import com.example.lab6gtics.Repository.ReservaRepository;
import com.example.lab6gtics.Repository.RolRepository;
import com.example.lab6gtics.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/Reservas")
public class ReservaController {
    final MesaRepository mesaRepository;
    final ReservaRepository reservaRepository;
    final RolRepository rolRepository;
    final UsuarioRepository usuarioRepository;
    public ReservaController(MesaRepository mesaRepository , ReservaRepository reservaRepository,RolRepository rolRepository,UsuarioRepository usuarioRepository  ){
        this.mesaRepository = mesaRepository;
        this.reservaRepository = reservaRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }
    @GetMapping("/verReservas")
    public String verReservas(Model model, HttpSession session){
        Usuario user = (Usuario) session.getAttribute("usuario");

        //Para cliente
        model.addAttribute("listaReservas", reservaRepository.getReservaPorIdCliente(user.getId()));
        return "/Reserva/ReservaLista";
    }
    @GetMapping("/verTodasReservas")
    public String verTodasReservas(Model model, HttpSession session){
        //para gerente
        Usuario user = (Usuario) session.getAttribute("usuario");
        model.addAttribute("listaReservas" ,reservaRepository.findAll());
        return "/Reserva/ReservaLista";
    }
    @GetMapping("/createReserva")
    public String verCreateReserva(Model model , @ModelAttribute("reserva") Reserva reserva){
        model.addAttribute("listaMesas" , mesaRepository.findAll());
        return "/Reserva/EditReserva";
    }

    @PostMapping("/createEditReservaCompleted")
    public String createEditReservaCompleted(Model model, @ModelAttribute("reserva") @Valid Reserva reserva ,
                                             BindingResult bindingResult, RedirectAttributes attr,  HttpSession session){
        reserva.setIdUsuario(((Usuario) session.getAttribute("usuario")));
        reservaRepository.save(reserva);
        return "redirect:/Reservas/verReservas";
        /*if(!bindingResult.hasErrors()) {
            reservaRepository.save(reserva);
            return "redirect:/Reservas/verReservas";
        }else{
            System.out.println("HUBO ERRORES D:");
            return "/Reserva/EditReserva";
        }
        */
    }
    @GetMapping("/deleteReserva")
    public String deleteReserva(Model model , @RequestParam("id") int id){
        Optional<Reserva> optional = reservaRepository.findById(id);
        if (optional.isPresent()) {
            reservaRepository.deleteById(id);
        }
        return "redirect:/Reservas/verReservas";
    }

}
