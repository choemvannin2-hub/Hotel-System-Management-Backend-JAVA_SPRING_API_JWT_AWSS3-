package com.choem_vannin.service.impl;

import com.choem_vannin.dto.requestDTO.RoomRequestDTO;
import com.choem_vannin.dto.responseDTO.RoomResponseDTO;
import com.choem_vannin.execption.BadRequestException;
import com.choem_vannin.mapper.RoomMapper;
import com.choem_vannin.model.Room;
import com.choem_vannin.model.RoomType;
import com.choem_vannin.repository.RoomRepository;
import com.choem_vannin.repository.RoomTypeRepository;
import com.choem_vannin.service.AwsS3Service;
import com.choem_vannin.service.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final AwsS3Service awsS3Service;

    @Override
    public RoomResponseDTO create(RoomRequestDTO requestDTO, MultipartFile photo) {
        if (roomRepository.existsByRoomNumber(requestDTO.getRoomNumber())){
            throw new BadRequestException("This room number already exist.");
        }

        // upload photo to AWS S3 if provided
        String photoUrl = null;
        if (photo != null && !photo.isEmpty()){
            photoUrl = awsS3Service.saveImageToS3(photo); // return url
        }

        Room room = RoomMapper.toEntity(requestDTO);
        room.setPhotoUrl(photoUrl);
        Room savedRoom = roomRepository.save(room);

        return RoomMapper.toResponse(savedRoom);
    }


    @Override
    public List<RoomResponseDTO> getRooms() {
        return roomRepository.findAll().stream().map(RoomMapper::toResponse).toList();
    }

    @Override
    public RoomResponseDTO update(Long id, RoomRequestDTO requestDTO, MultipartFile photo) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("This room does not exist with id: " + id));

        // Check room number uniqueness safely
        if (requestDTO.getRoomNumber() != null
                && !requestDTO.getRoomNumber().equals(existingRoom.getRoomNumber())
                && roomRepository.existsByRoomNumber(requestDTO.getRoomNumber())) {
            throw new BadRequestException("This room number already exists.");
        }

        // Update fields directly
        existingRoom.setRoomNumber(requestDTO.getRoomNumber());
        existingRoom.setFloor(requestDTO.getFloor());
        existingRoom.setDescription(requestDTO.getDescription());
        existingRoom.setCapacity(requestDTO.getCapacity());
        existingRoom.setPricePerNight(requestDTO.getPricePerNight());

        // Set RoomType entity
        if (requestDTO.getRoomTypeId() != null) {
            RoomType roomType = roomTypeRepository.findById(requestDTO.getRoomTypeId())
                    .orElseThrow(() -> new BadRequestException("RoomType not found with id: " + requestDTO.getRoomTypeId()));
            existingRoom.setRoomType(roomType);
        }

        //FIXED: Update photo only if provided
        if (photo != null && !photo.isEmpty()) {
            String newPhotoUrl = awsS3Service.saveImageToS3(photo);
            existingRoom.setPhotoUrl(newPhotoUrl);
        }

        return RoomMapper.toResponse(roomRepository.save(existingRoom));
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }
}
