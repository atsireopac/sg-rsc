package br.gov.ife.sgrsc.shared.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}