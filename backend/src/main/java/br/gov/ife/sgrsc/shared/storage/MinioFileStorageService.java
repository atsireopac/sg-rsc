package br.gov.ife.sgrsc.shared.storage;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class MinioFileStorageService implements FileStorageService {

    private final MinioClient minioClient;
    private final String bucket;

    public MinioFileStorageService(
            MinioClient minioClient,
            @Value("${minio.bucket}") String bucket
    ) {
        this.minioClient = minioClient;
        this.bucket = bucket;
    }

    @Override
    public String armazenar(MultipartFile arquivo) {
        try {
            garantirBucket();

            String nomeOriginal = arquivo.getOriginalFilename();
            String extensao = extrairExtensao(nomeOriginal);
            String nomeArmazenado = UUID.randomUUID() + extensao;

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(nomeArmazenado)
                            .stream(
                                    arquivo.getInputStream(),
                                    arquivo.getSize(),
                                    -1
                            )
                            .contentType(arquivo.getContentType())
                            .build()
            );

            return nomeArmazenado;
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível armazenar o arquivo no MinIO.",
                    exception
            );
        }
    }

    @Override
    public Resource recuperar(String nomeArmazenado) {
        try {
            return new InputStreamResource(
                    minioClient.getObject(
                            GetObjectArgs.builder()
                                    .bucket(bucket)
                                    .object(nomeArmazenado)
                                    .build()
                    )
            );
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível recuperar o arquivo do MinIO.",
                    exception
            );
        }
    }

    private void garantirBucket() throws Exception {
        boolean existe = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucket)
                        .build()
        );

        if (!existe) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucket)
                            .build()
            );
        }
    }

    private String extrairExtensao(String nomeOriginal) {
        if (nomeOriginal == null || !nomeOriginal.contains(".")) {
            return "";
        }

        return nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
    }
}