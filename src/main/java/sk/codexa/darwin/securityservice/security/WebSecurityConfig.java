package sk.codexa.darwin.securityservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import sk.codexa.darwin.securityservice.repositories.PersonRepository;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonRepository personRepository;

    @Autowired
    public WebSecurityConfig(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        personRepository.findAll().forEach(person -> manager.createUser(User.withUsername(person.getLogin()).password
                (person.getPassword()).roles(person.getRole().toString()).build()));

        return manager;
    }
}
