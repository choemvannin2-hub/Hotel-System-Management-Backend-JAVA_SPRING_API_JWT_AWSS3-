package com.choem_vannin.model;

import com.choem_vannin.enums.Amenity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room_types")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_type", nullable = false, unique = true)
    private String name;

    // ONE RoomType -> MANY Amenities
    @ElementCollection(targetClass = Amenity.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "room_type_amenities",
            joinColumns = @JoinColumn(name = "room_type_id")
    )
    @Column(name = "amenity", nullable = false)
    @Builder.Default
    private Set<Amenity> amenities = new HashSet<>();

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ONE RoomType -> MANY Rooms
    @OneToMany(mappedBy = "roomType")
    private List<Room> rooms;
}