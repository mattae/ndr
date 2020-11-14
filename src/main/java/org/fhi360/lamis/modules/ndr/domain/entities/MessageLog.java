package org.fhi360.lamis.modules.ndr.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Table(name = "ndr_message_log")
public class MessageLog implements Serializable, Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    private String identifier;

    @NonNull
    private String file;

    @NonNull
    private LocalDateTime lastUpdated;

    public MessageLog() {
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
