package sk.codexa.darwin.securityservice.security;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {

    /*@Autowired
    private PersonRepository personRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().authorizeRequests().antMatchers("/api/**").access("hasRole('Admin') and hasRole('Tenant Admin')")
                .anyRequest().authenticated()
        .and().authorizeRequests().antMatchers("/login","/").permitAll().anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        super.configure(web);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        personRepository.findAll().forEach(person -> manager.createUser(User.withUsername(person.getLogin()).password
                (person.getPassword()).roles(person.getRole().toString()).build()));

        return manager;
    }*/
}
