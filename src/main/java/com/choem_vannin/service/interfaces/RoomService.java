package com.choem_vannin.service.interfaces;

import com.choem_vannin.dto.requestDTO.RoomRequestDTO;
import com.choem_vannin.dto.responseDTO.RoomResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {

    RoomResponseDTO create(RoomRequestDTO requestDTO, MultipartFile photo);
    List<RoomResponseDTO> getRooms();
    RoomResponseDTO update(Long id, RoomRequestDTO requestDTO, MultipartFile photo);
    void delete(Long id);
}
