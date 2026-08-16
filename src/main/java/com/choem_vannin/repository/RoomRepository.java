package com.choem_vannin.repository;

import com.choem_vannin.dto.responseDTO.RoomResponseDTO;
import com.choem_vannin.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Boolean existsByRoomNumber(String roomNumber);
}
