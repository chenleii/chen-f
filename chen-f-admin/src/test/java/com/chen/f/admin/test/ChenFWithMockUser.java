package com.chen.f.admin.test;


import org.springframework.core.annotation.AliasFor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When used with {@link org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener} this annotation can be
 * added to a test method to emulate running with a mocked user. In order to work with
 * {@link MockMvc} The {@link SecurityContext} that is used will have the following
 * properties:
 *
 * <ul>
 * <li>The {@link SecurityContext} created with be that of
 * {@link SecurityContextHolder#createEmptyContext()}</li>
 * <li>It will be populated with an {@link UsernamePasswordAuthenticationToken} that uses
 * the username of either {@link #value()} or {@link #username()},
 * {@link GrantedAuthority} that are specified by {@link #roles()}, and a password
 * specified by {@link #password()}.
 * </ul>
 *
 * @see org.springframework.security.test.context.support.WithUserDetails
 *
 * @author Rob Winch
 * @since 4.0
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = ChenFWithMockUserSecurityContextFactory.class)
public @interface ChenFWithMockUser {
    /**
     * Convenience mechanism for specifying the username. The default is "user". If
     * {@link #username()} is specified it will be used instead of {@link #value()}
     * @return
     */
    String value() default "user";

    /**
     * The username to be used. Note that {@link #value()} is a synonym for
     * {@link #username()}, but if {@link #username()} is specified it will take
     * precedence.
     * @return
     */
    String username() default "";

    /**
     * <p>
     * The roles to use. The default is "USER". A {@link GrantedAuthority} will be created
     * for each value within roles. Each value in roles will automatically be prefixed
     * with "ROLE_". For example, the default will result in "ROLE_USER" being used.
     * </p>
     * <p>
     * If {@link #authorities()} is specified this property cannot be changed from the default.
     * </p>
     *
     * @return
     */
    String[] roles() default { "USER" };

    /**
     * <p>
     * The authorities to use. A {@link GrantedAuthority} will be created for each value.
     * </p>
     *
     * <p>
     * If this property is specified then {@link #roles()} is not used. This differs from
     * {@link #roles()} in that it does not prefix the values passed in automatically.
     * </p>
     *
     * @return
     */
    String[] authorities() default {};

    /**
     * The password to be used. The default is "password".
     * @return
     */
    String password() default "password";

    /**
     * Determines when the {@link org.springframework.security.core.context.SecurityContext} is setup. The default is before
     * {@link TestExecutionEvent#TEST_METHOD} which occurs during
     * {@link org.springframework.test.context.TestExecutionListener#beforeTestMethod(TestContext)}
     * @return the {@link TestExecutionEvent} to initialize before
     * @since 5.1
     */
    @AliasFor(annotation = WithSecurityContext.class)
    TestExecutionEvent setupBefore() default TestExecutionEvent.TEST_METHOD;
}
