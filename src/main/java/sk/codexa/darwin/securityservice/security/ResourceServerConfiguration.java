package sk.codexa.darwin.securityservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //TODO add csrf protection
        http.csrf()/*.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()*/
                .disable()
                .authorizeRequests()
                .antMatchers("/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_TENANT_ADMIN')").anyRequest().authenticated()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated().and().logout();
    }
}
