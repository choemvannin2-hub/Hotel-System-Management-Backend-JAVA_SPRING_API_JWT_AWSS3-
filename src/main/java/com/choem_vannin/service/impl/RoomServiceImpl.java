package com.choem_vannin.service.impl;

import com.choem_vannin.dto.requestDTO.RoomRequestDTO;
import com.choem_vannin.dto.responseDTO.RoomResponseDTO;
import com.choem_vannin.execption.BadRequestException;
import com.choem_vannin.mapper.RoomMapper;
import com.choem_vannin.model.Room;
import com.choem_vannin.repository.RoomRepository;
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
        if (id == null || !roomRepository.existsById(id)) {
            throw new BadRequestException("This room does not exist with id: " + id);
        }

        // upload photo to AWS S3 if provided
        String photoUrl = null;
        if (photo != null && !photo.isEmpty()){
            photoUrl = awsS3Service.saveImageToS3(photo); // return url
        }

        Room room = RoomMapper.toEntity(requestDTO);
        room.setId(id);
        room.setPhotoUrl(photoUrl);
        return RoomMapper.toResponse(roomRepository.save(room));
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }
}
