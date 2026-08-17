package com.choem_vannin.repository;

import com.choem_vannin.dto.responseDTO.RoomTypeResponseDTO;
import com.choem_vannin.model.Room;
import com.choem_vannin.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    Boolean existsByName(String name);
}
