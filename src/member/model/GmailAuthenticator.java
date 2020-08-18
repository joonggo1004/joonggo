package member.model;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GmailAuthenticator extends Authenticator {
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("구글이메일아이디", "비번");
	}
}
