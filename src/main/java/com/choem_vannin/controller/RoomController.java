package com.choem_vannin.controller;

import com.choem_vannin.dto.requestDTO.RoomRequestDTO;
import com.choem_vannin.dto.responseDTO.RoomResponseDTO;
import com.choem_vannin.service.interfaces.RoomService;
import com.choem_vannin.utils.ApiResponse;
import com.choem_vannin.utils.ApiResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseHelper<RoomResponseDTO>> create(
            @RequestPart("data")RoomRequestDTO requestDTO,
            @RequestPart(value = "file", required = false) MultipartFile file
            ){
        RoomResponseDTO responseDTO = roomService.create(requestDTO, file);
        return ApiResponse.ok(responseDTO, "Create Room success.");
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseHelper<List<RoomResponseDTO>>> findAll(){
        List<RoomResponseDTO> responseDTOS = roomService.getRooms();
        return ApiResponse.ok(responseDTOS, "Get All rooms success.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping(path = "/{id}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseHelper<RoomResponseDTO>> update(
            @PathVariable Long id,
            @RequestPart("data")RoomRequestDTO requestDTO,
            @RequestPart(value = "file", required = false) MultipartFile file){
        RoomResponseDTO responseDTO = roomService.update(id, requestDTO, file);
        return ApiResponse.ok(responseDTO, "Updated success with room id: "+id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseHelper<Void>> delete(@PathVariable Long id){
        roomService.delete(id);
        return ApiResponse.ok(null, "Delete room success.");
    }
}
