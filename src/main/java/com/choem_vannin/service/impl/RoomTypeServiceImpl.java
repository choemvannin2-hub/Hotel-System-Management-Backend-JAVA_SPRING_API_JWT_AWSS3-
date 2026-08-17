package com.choem_vannin.service.impl;

import com.choem_vannin.dto.requestDTO.RoomTypeRequestDTO;
import com.choem_vannin.dto.responseDTO.RoomTypeResponseDTO;
import com.choem_vannin.execption.BadRequestException;
import com.choem_vannin.execption.ResourceNotFoundException;
import com.choem_vannin.mapper.RoomTypeMapper;
import com.choem_vannin.model.RoomType;
import com.choem_vannin.repository.RoomTypeRepository;
import com.choem_vannin.service.interfaces.RoomTypeService;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    @Override
    public RoomTypeResponseDTO create(RoomTypeRequestDTO requestDTO) {
        // Validation Check
        if (roomTypeRepository.existsByName(requestDTO.getName())){
            throw new BadRequestException("Room type name cannot duplicate.");
        }
        if (requestDTO.getName().isEmpty()){
            throw new BadRequestException("roomType Name required.");
        }

        RoomType roomType = RoomTypeMapper.toEntity(requestDTO);
        RoomType saved = roomTypeRepository.save(roomType);
        return RoomTypeMapper.toResponse(saved);
    }

    @Override
    public List<RoomTypeResponseDTO> getAll() {
        return roomTypeRepository.findAll().stream().map(RoomTypeMapper::toResponse).toList();
    }

    @Override
    public RoomTypeResponseDTO getById(Long id) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("This Room Type not found.")
                );
        return RoomTypeMapper.toResponse(roomType);
    }

    @Override
    public RoomTypeResponseDTO update(Long id, RoomTypeRequestDTO requestDTO) {

        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("This user id not found!")
        );

        roomType.setName(requestDTO.getName());
        roomType.setAmenities(requestDTO.getAmenities());

        RoomType updated = roomTypeRepository.save(roomType);
        return RoomTypeMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        roomTypeRepository.deleteById(id);
    }
}
