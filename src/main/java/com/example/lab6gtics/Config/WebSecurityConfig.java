package com.example.lab6gtics.Config;

import com.example.lab6gtics.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig {
    final DataSource dataSource;
    final UsuarioRepository usuarioRepository;
    public WebSecurityConfig(DataSource dataSource ,UsuarioRepository usuarioRepository){
        this.dataSource = dataSource;
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsManager users(DataSource dataSource){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        String sql1 = "select correo, password, estado from usuario where correo = ?";
        String sql2 = "select u.correo, r.nombre from usuario u inner join rol r on (u.idRol = r.idrol) where u.correo = ?";
        users.setUsersByUsernameQuery(sql1);
        users.setAuthoritiesByUsernameQuery(sql2);
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/processLogin")
                .usernameParameter("email")
                .passwordParameter("contrasenia")
                .successHandler((request, response, authentication) -> {

                    DefaultSavedRequest defaultSavedRequest =
                            (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

                    HttpSession session = request.getSession();
                    session.setAttribute("usuario",usuarioRepository.findByEmail(authentication.getName()));

                    String rol = "";
                    for(GrantedAuthority role : authentication.getAuthorities()){
                        rol = role.getAuthority();
                        break;
                    }
                    switch(rol){
                        case "admin":
                            response.sendRedirect("/Dispositivo/verDispositivos");
                            break;
                        case "gerente":
                            response.sendRedirect("/Dispositivo/verDispositivos");
                            break;
                        case "cliente":
                            response.sendRedirect("/Dispositivo/verDispositivos");
                            break;
                    }

                });

        http.authorizeHttpRequests()
                .requestMatchers("/Dispositivo/**").hasAnyAuthority("admin", "profesor" , "alumno")
                .requestMatchers("/Reserva/**").hasAuthority( "alumno")
                .requestMatchers("/Prestamo/**").hasAnyAuthority( "profesor" , "alumno")
                .anyRequest().permitAll();


        http.logout()
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);


        return http.build();
    }



}
