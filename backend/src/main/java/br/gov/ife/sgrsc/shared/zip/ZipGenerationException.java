package br.gov.ife.sgrsc.shared.zip;

public class ZipGenerationException extends RuntimeException {

    public ZipGenerationException(
            String message
    ) {
        super(message);
    }

    public ZipGenerationException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}