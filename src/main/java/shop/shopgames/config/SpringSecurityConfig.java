package shop.shopgames.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.csrf(config -> config.disable()); // jak nie chcesz postem tylko getem
        http.headers(config ->config.disable());


        var mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(

                        mvcMatcherBuilder.pattern("/game"),
                        mvcMatcherBuilder.pattern("/serach"),
                        mvcMatcherBuilder.pattern("/register"),
                        mvcMatcherBuilder.pattern("/registerUser"),
                        mvcMatcherBuilder.pattern("/url_error403"),
                        mvcMatcherBuilder.pattern("/"),
                        mvcMatcherBuilder.pattern("/script.js"),
                        mvcMatcherBuilder.pattern("/style.css"),
                        mvcMatcherBuilder.pattern("/external/**"),
                        mvcMatcherBuilder.pattern("/internal/**")
                ).permitAll()
                .requestMatchers(
                        mvcMatcherBuilder.pattern("/deleteKosz"),
                        mvcMatcherBuilder.pattern("/NewQuantity"),
                        mvcMatcherBuilder.pattern("/dodajDoKosza"),
                        mvcMatcherBuilder.pattern("/kosz"),
                        mvcMatcherBuilder.pattern("/myOrders"),
                        mvcMatcherBuilder.pattern("/addOpinion"),
                        mvcMatcherBuilder.pattern("/deleteOpinions"),
                        mvcMatcherBuilder.pattern("/zamowienie"),
                        mvcMatcherBuilder.pattern("/order"),

                        mvcMatcherBuilder.pattern("/myOrders")

                        ).hasRole("CLIENT")

                .requestMatchers(
                        mvcMatcherBuilder.pattern("/deleteGame"),
                        mvcMatcherBuilder.pattern("/ordersPanel"),
                        mvcMatcherBuilder.pattern("/addGameCategory"),
                        mvcMatcherBuilder.pattern("/editGameCategory"),
                        mvcMatcherBuilder.pattern("/saveCategory"),
                        mvcMatcherBuilder.pattern("/deleteGameCategory"),
                        mvcMatcherBuilder.pattern("/GameCategories"),
                        mvcMatcherBuilder.pattern("/gameSave"),
                        mvcMatcherBuilder.pattern("/addGame"),
                        mvcMatcherBuilder.pattern("/editGame"),
                        mvcMatcherBuilder.pattern("/editGamePlatform"),
                        mvcMatcherBuilder.pattern("/savePlatform"),
                        mvcMatcherBuilder.pattern("/GamePlatform"),
                        mvcMatcherBuilder.pattern("/deleteGamePlatform"),
                        mvcMatcherBuilder.pattern("/saveTag"),
                        mvcMatcherBuilder.pattern("/editGameTag"),
                        mvcMatcherBuilder.pattern("/deleteGameTag"),
                        mvcMatcherBuilder.pattern("/GameTags"),
                        mvcMatcherBuilder.pattern("/aktualizujStatus"),
                        mvcMatcherBuilder.pattern("/ordersPanel"),
                        mvcMatcherBuilder.pattern("/addGamePlatform"),
                        mvcMatcherBuilder.pattern("/addGameTag"),
                        mvcMatcherBuilder.pattern("/ordersPanel"),
                        mvcMatcherBuilder.pattern("/editGame"),
                        mvcMatcherBuilder.pattern("/addGame")
                ).hasRole("SELLER")

                .requestMatchers(
                        mvcMatcherBuilder.pattern("/pracownicy"),
                        mvcMatcherBuilder.pattern("/registerEmployee"),
                        mvcMatcherBuilder.pattern("/deleteEmployee"),
                        mvcMatcherBuilder.pattern("/h2-console/**")
                ).hasRole("BOSS")

                .anyRequest()
                .authenticated());

        http.formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
        );




        http.logout((logout) -> logout.
                permitAll()
                .logoutUrl("/logout"));

        http.exceptionHandling((config)-> config.accessDeniedPage("/url_error403"));//
        return http.build();
    }




}

