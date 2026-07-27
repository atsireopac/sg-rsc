package br.gov.ife.sgrsc.shared.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String armazenar(MultipartFile arquivo);

}
