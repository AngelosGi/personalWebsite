import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password"; //put your preferred password
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println(encodedPassword); //copy password and insert it to the script bellow
    }
}

// INSERT INTO app_user (username, password, role) VALUES ('user', 'encoded password from print here', 'ROLE_USER');