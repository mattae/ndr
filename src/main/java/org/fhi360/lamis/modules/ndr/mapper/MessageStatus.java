package org.fhi360.lamis.modules.ndr.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageStatus {
    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> getMessageStatus(long patientId, String identifier) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", "INITIAL");
        map.put("lastMessage", null);

        String query = "SELECT time_stamp FROM ndrmessagelog WHERE patient_id = ? AND identifier = ?";
        List<LocalDateTime> dates = jdbcTemplate.queryForList(query, LocalDateTime.class, patientId, identifier);
        if (!dates.isEmpty()) {
            if (dates.get(0) != null) {
                map.put("statusCode", "UPDATED");
                map.put("lastMessage", dates.get(0));
            }
        }
        return map;
    }

    public long getLastMessageId() {
        long messageId = 0L;
        String query = "SELECT MAX(message_id) AS message_id FROM ndrmessagelog";
        List<Long> ids = jdbcTemplate.queryForList(query, Long.class);
        if (!ids.isEmpty()) {
            if (ids.get(0) != null) {
                return ids.get(0);
            }
        }
        return messageId;
    }
}
