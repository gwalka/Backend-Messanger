package basic.main.security.jwt;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtKeyProvider {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public JwtKeyProvider() throws Exception {
        String privatePath = System.getenv("JWT_PRIVATE_KEY_PATH");
        if (privatePath == null) privatePath = "./secrets/private_pkcs8.pem"; //TODO: NOT FOR PROD //TODO: NOT FOR PROD
        //TODO: NOT FOR PROD

        String publicPath = System.getenv("JWT_PUBLIC_KEY_PATH");
        if (publicPath == null) publicPath = "./secrets/public.pem"; //TODO: NOT FOR PROD //TODO: NOT FOR PROD
        //TODO: NOT FOR PROD

        if (!Files.exists(Paths.get(privatePath))) {
            throw new IllegalStateException("Файл приватного ключа не найден: " + privatePath);
        }
        if (!Files.exists(Paths.get(publicPath))) {
            throw new IllegalStateException("Файл публичного ключа не найден: " + publicPath);
        }

        this.privateKey = loadPrivateKey(privatePath);
        this.publicKey = loadPublicKey(publicPath);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    private PrivateKey loadPrivateKey(String path) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(path));
        String privateKeyPEM = new String(keyBytes)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private PublicKey loadPublicKey(String path) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(path));
        String publicKeyPEM = new String(keyBytes)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }
}
