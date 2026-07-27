package br.gov.ife.sgrsc.shared.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class HashUtils {

    private HashUtils() {
    }

    public static String calcularSha256(MultipartFile arquivo) {
        try (
                InputStream inputStream = arquivo.getInputStream()
        ) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] buffer = new byte[8192];
            int bytesLidos;

            while ((bytesLidos = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesLidos);
            }

            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException(
                    "Algoritmo SHA-256 não está disponível.",
                    exception
            );
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível calcular o hash do arquivo.",
                    exception
            );
        }
    }
}
