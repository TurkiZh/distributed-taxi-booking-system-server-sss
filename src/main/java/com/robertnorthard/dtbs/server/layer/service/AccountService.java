package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.AccountAlreadyExistsException;
import com.robertnorthard.dtbs.server.common.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.common.exceptions.AccountInvalidException;
import com.robertnorthard.dtbs.server.common.exceptions.EntityNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.events.PasswordResetEvent;
import com.robertnorthard.dtbs.server.layer.persistence.AccountDao;
import com.robertnorthard.dtbs.server.layer.persistence.PasswordResetEventDao;
import com.robertnorthard.dtbs.server.layer.utils.AuthenticationUtils;
import com.robertnorthard.dtbs.server.layer.utils.mail.MailStrategy;
import com.robertnorthard.dtbs.server.layer.model.Account;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;

/**
 * Account Service interface implementation.
 *
 * @author robertnorthard
 */
public class AccountService implements AccountFacade {

    private final AccountDao accountDao;
    @Inject
    private PasswordResetEventDao passwordResetEventDao;
    @Inject
    private MailStrategy mailStrategy;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    /**
     * AccountService constructor.
     *
     * @param accountDao Account data access object.
     * @param passwordResetEventDao Password reset data access object.
     * @param mailStrategy Mail strategy object.
     */
    public AccountService(AccountDao accountDao, PasswordResetEventDao passwordResetEventDao, MailStrategy mailStrategy) {
        this.accountDao = accountDao;
        this.passwordResetEventDao = passwordResetEventDao;
        this.mailStrategy = mailStrategy;
    }

    /**
     * Register a new account. Checks if user exists and confirms validity of
     * email. If email is valid generate hash of user's password and send email
     * to user to confirm account creation.
     *
     * @param acct account to create.
     * @throws AccountAlreadyExistsException if account already exists.
     * @throws AccountInvalidException invalid email address
     */
    @Override
    public void registerAccount(final Account acct)
            throws AccountAlreadyExistsException, AccountInvalidException {

        // check if acount with username already exists
        if (this.accountDao.findEntityById(acct.getUsername()) == null) {

            //check if valid email
            if (!this.mailStrategy.isValidEmail(
                    acct.getEmail())) {
                throw new AccountInvalidException("Invalid email.");
            }

            if (!Account.isValidUsername(acct.getUsername())) {
                throw new AccountInvalidException(
                        "Username invalid must be at least 5 characters, start with a letter and only contain letters and numbers.");
            }

            // generate password hash
            String passwordHash = AuthenticationUtils.hashPassword(
                    acct.getPassword());

            // store password hash
            acct.setPassword(passwordHash);

            // activate account
            acct.setActive();

            // persist entity
            this.accountDao.persistEntity(acct);

            // email account registration confirmation.
            this.mailStrategy.sendMail("DTBS - Registration Confirmation",
                    "Your account, with username " + acct.getUsername()
                    + " has been activated.", acct.getEmail());
        } else {
            throw new AccountAlreadyExistsException(
                    String.format("Account with username - [%s] already exists.", acct.getUsername()));
        }
    }

    /**
     * Return account with corresponding username.
     *
     * @param username username.
     * @return an account with the corresponding username. If the account does
     * not exist return null.
     */
    @Override
    public Account findAccount(final String username) {
        return this.accountDao.findEntityById(username);
    }

    /**
     * Authenticate a user if password matches and account is active.
     *
     * @param username username of account
     * @param password password of account
     * @return account object if authentication successful else null.
     * @throws AccountAuthenticationFailed if authentication fails.
     */
    @Override
    public Account authenticate(String username, String password)
            throws AccountAuthenticationFailed {
        Account account = this.findAccount(username);

        if (account == null) {
            throw new AccountAuthenticationFailed();
        }

        if (!AuthenticationUtils.
                checkPassword(password, account.getPassword()) || !account.isActive()) {
            throw new AccountAuthenticationFailed();
        }

        return account;
    }

    /**
     * Reset account password via temporary code. 1 - Generate temporary code 2
     * - Create reset event. 3 - Send email to use with temporary access code.
     *
     * @param username username of account to reset.
     * @throws AccountInvalidException account not found.
     */
    @Override
    public void resetPassword(final String username)
            throws AccountInvalidException {

        Account account = this.findAccount(username);

        if (account == null) {
            throw new AccountInvalidException("Invalid account.");
        }

        // generate temporary reset code
        String resetCode = AuthenticationUtils.generateCode(4);

        // calculate reset expiry
        DateTime expireDate = new DateTime().plusDays(1);

        // create new password reset event with reset code, account username and expiry.
        PasswordResetEvent event = new PasswordResetEvent(
                username, resetCode, expireDate);

        // Set all current password resets for the user iactive.
        this.deactivePasswordResets(username);

        // save password reset event
        this.passwordResetEventDao.persistEntity(event);

        // send email with temporary code
        this.mailStrategy.sendMail("DTBS - Reset Password",
                "Your temporary code to reset your password is "
                + resetCode + " and expires on "
                + expireDate.toString("dd-MM-YYYY hh:mm:ss"),
                account.getEmail());
    }

    /**
     * Reset password for username using temporary access code.
     *
     * @param code temporary authentication code.
     * @param username username to authenticate with.
     * @param newPassword new password for user.
     * @throws AccountAuthenticationFailed if username and code do not match a
     * valid, active password reset event.
     * @throws EntityNotFoundException account not found but password resets
     * exist. Indicates data integrity issues.
     */
    @Override
    public void resetPassword(final String code, final String username, final String newPassword)
            throws AccountAuthenticationFailed, EntityNotFoundException {

        if (authenticateTemporaryCredentials(username, code)) {

            Account account = this.findAccount(username);

            if (account != null) {

                // create hash of new password
                String passwordHash = AuthenticationUtils.hashPassword(newPassword);

                // store password hash
                account.setPassword(passwordHash);

                //update account
                this.accountDao.update(account);

                // on success deactive all password resets for account
                this.deactivePasswordResets(username);
            } else {
                throw new EntityNotFoundException();
            }
        } else {
            throw new AccountAuthenticationFailed();
        }
    }

    private boolean authenticateTemporaryCredentials(final String username, final String code) {
        // authenticate code.
        List<PasswordResetEvent> events = this.passwordResetEventDao.findActivePasswordResetByUsername(username);

        for (PasswordResetEvent event : events) {
            if (event.validateCode(code)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Set all active password resets for the specified user inactive.
     *
     * @param username username to search by.
     */
    private void deactivePasswordResets(String username) {
        List<PasswordResetEvent> events = this.passwordResetEventDao.findActivePasswordResetByUsername(username);

        for (PasswordResetEvent e : events) {
            e.setInactive();
            this.passwordResetEventDao.update(e);
        }
    }

    /**
     * Authenticate user from base64 encoded message.
     *
     * @param base64HttpCredentials base64 encoded credentials
     * @return account object if authentication successful else null.
     * @throws AccountAuthenticationFailed if authentication fails.
     */
    @Override
    public Account authenticate(String base64HttpCredentials) throws AccountAuthenticationFailed {

        Account authAccount = null;
        String[] credentials = null;

        try {
            // Remove HTTP bearer e.g. Authorization: Basic base64EncodedUsernameAndPassword
            String filteredBase64HttpCredentials = base64HttpCredentials.replaceFirst("[Bb]asic ", "");

            String base64Decode = AuthenticationUtils.base64Decode(filteredBase64HttpCredentials);
            credentials = base64Decode.split(":");

            if (credentials.length != 2) {
                throw new IllegalArgumentException("Invalid Authorisation token.");
            }

            //username - credentials[0]
            // password - credentials[1]
            authAccount = this.authenticate(credentials[0], credentials[1]);

        } catch (IllegalArgumentException | AccountAuthenticationFailed ex) {
            throw ex;
        }

        return authAccount;
    }
}
