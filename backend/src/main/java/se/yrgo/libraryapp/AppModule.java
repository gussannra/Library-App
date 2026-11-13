package se.yrgo.libraryapp;

import com.google.inject.AbstractModule;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AppModule extends AbstractModule {

    /**
     * This tells Guice that whenever it sees a dependency on a PasswordEncode, it should
     * satisfy the dependency using an Argon2PasswordEncode
     */
    @Override
    protected void configure() {
        bind(PasswordEncoder.class).to(Argon2PasswordEncoder.class);
    }
}
