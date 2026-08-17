package com.choem_vannin.service.interfaces;

import com.choem_vannin.dto.requestDTO.RoomTypeRequestDTO;
import com.choem_vannin.dto.responseDTO.RoomTypeResponseDTO;

import java.util.List;

public interface RoomTypeService {
    // TODO: 8/12/2026: CRUD Room type
    RoomTypeResponseDTO create(RoomTypeRequestDTO requestDTO);
    List<RoomTypeResponseDTO> getAll();
    RoomTypeResponseDTO getById(Long id);
    RoomTypeResponseDTO update(Long id, RoomTypeRequestDTO requestDTO);
    void delete(Long id);
}
