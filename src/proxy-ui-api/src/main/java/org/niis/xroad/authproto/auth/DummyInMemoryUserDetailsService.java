/**
 * The MIT License
 * Copyright (c) 2018 Estonian Information System Authority (RIA),
 * Nordic Institute for Interoperability Solutions (NIIS), Population Register Centre (VRK)
 * Copyright (c) 2015-2017 Estonian Information System Authority (RIA), Population Register Centre (VRK)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.niis.xroad.authproto.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Configures in-memory user details with simple user/password, admin/password
 * type of users, if PAM authentication is not activated
 */
@Configuration
public class DummyInMemoryUserDetailsService {

    @Value("${proto.pam}")
    private boolean pam;

    /**
     * Test userdetailsservice for non-pam implementation for development purposes
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        Collection<UserDetails> users = new ArrayList<>();
        if (!pam) {
            users.add(User.withDefaultPasswordEncoder()
                            .username("user")
                            .password("password")
                            .roles("USER")
                            .build());

            users.add(User.withDefaultPasswordEncoder()
                            .username("admin")
                            .password("password")
                            .roles("USER", "ADMIN")
                            .build());

            users.add(User.withDefaultPasswordEncoder()
                            .username("admindba")
                            .password("password")
                            .roles("USER", "ADMIN", "DBA")
                            .build());

            users.add(User.withDefaultPasswordEncoder()
                            .username("dba")
                            .password("password")
                            .roles("USER", "DBA")
                            .build());
        }
        return new InMemoryUserDetailsManager(users);
    }
}
