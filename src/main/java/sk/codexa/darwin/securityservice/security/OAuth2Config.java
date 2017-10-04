package sk.codexa.darwin.securityservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import sk.codexa.darwin.securityservice.db.TempDatabaseInitializer;
import sk.codexa.darwin.securityservice.repositories.ClientRepository;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TempDatabaseInitializer initializer;
    private final ClientRepository clientRepository;

    @Autowired
    public OAuth2Config(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                        TempDatabaseInitializer initializer, PasswordEncoder passwordEncoder,
                        ClientRepository clientRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.initializer = initializer;
        this.clientRepository = clientRepository;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(inMemoryClientDetailsService()).build();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public ClientDetailsService inMemoryClientDetailsService() throws Exception {
        //TODO remove when switching to standalone db
        initializer.createClients();

        InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();

        clientRepository.findAll().forEach(client -> builder
                .withClient(client.getClientName()).secret(client.getSecret()).authorizedGrantTypes(client
                        .getGrantTypes().toArray(new String[client.getGrantTypes().size()])).scopes(client
                        .getScopes()
                        .toArray(new String[client.getScopes().size()])));

        return builder.build();
    }
}