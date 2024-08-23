package br.com.mglu.orderapi.upload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadService {

    private static final String BUCKET_NAME = "mglu-orders";
    private static final String NAME_PREFIX = "data_";

    private final S3Client s3Client;

    public Mono<UUID> uploadFile(String recordType,
                                 Flux<ByteBuffer> fluxBuffer) {
        return fluxBuffer
                .flatMap(file -> Mono.fromCallable(() -> {
                    final UUID uuidFile = UUID.randomUUID();
                    uploadToS3(uuidFile, recordType, file);
                    return uuidFile;
                }))
                .single();
    }

    private void uploadToS3(UUID uuidFile, String recordType, ByteBuffer file) {
        Map<String, String> metadata = Map.of("record-type", recordType);
        String key = NAME_PREFIX.concat(uuidFile.toString());
        PutObjectRequest request = PutObjectRequest.builder()
                .metadata(metadata)
                .bucket(BUCKET_NAME)
                .key(key)
                .contentType("text/plain")
                .contentLength(Long.valueOf(file.array().length))
                .build();

        PutObjectResponse response = s3Client.putObject(request, RequestBody.fromByteBuffer(file));
        log.info("response {} ", response);

    }


}
