package br.com.mglu.orderapi.api.v1.upload;

import br.com.mglu.orderapi.updaload.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.UUID;

@RestController
@RequestMapping("/v1/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService service;

    @PostMapping
    public Mono<UUID> uploadfile(@RequestPart(name = "recordType") final String recordType,
                                 @RequestPart(name = "file") final Flux<ByteBuffer> file,
                                 ServerWebExchange exchange) {
        return service.uploadFile(recordType, file)
                .doOnSuccess(e -> exchange.getResponse().setStatusCode(HttpStatus.CREATED));
    }

}
