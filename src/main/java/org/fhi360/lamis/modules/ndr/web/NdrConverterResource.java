package org.fhi360.lamis.modules.ndr.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fhi360.lamis.modules.ndr.service.NdrConverterService;
import org.fhi360.lamis.modules.ndr.service.Deduplicator;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NdrConverterResource {
    private final NdrConverterService ndrConverterService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final Deduplicator deduplicator;

    @GetMapping("/ndr/run")
    @Async
    public void run(@RequestParam List<Long> ids) {
        messagingTemplate.convertAndSend("/topic/ndr-status", "start");
        ids.forEach(ndrConverterService::buildMessage);
        messagingTemplate.convertAndSend("/topic/ndr-status", "end");
    }

    @GetMapping("/ndr/list-facilities")
    public List<Map<String, Object>> listFacilities() {
        return ndrConverterService.listFacilities();
    }

    @GetMapping("/ndr/download/{file}")
    public void downloadFile(@PathVariable String file, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream baos = ndrConverterService.downloadFile(file);
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file + ".zip");
        response.setHeader("Content-Length", Integer.valueOf(baos.size()).toString());
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(baos.toByteArray());
        outputStream.close();
        response.flushBuffer();
    }

    @GetMapping("/ndr/list-files")
    public Collection<String> listFiles() {
        return ndrConverterService.listFiles();
    }

    @GetMapping("/ndr/remove-duplicates")
    public void removeDuplicates() {
        deduplicator.deduplicate();
    }
}
