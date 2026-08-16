package com.choem_vannin.controller;

import com.choem_vannin.dto.requestDTO.RoomTypeRequestDTO;
import com.choem_vannin.dto.responseDTO.RoomTypeResponseDTO;
import com.choem_vannin.service.interfaces.RoomTypeService;
import com.choem_vannin.utils.ApiResponse;
import com.choem_vannin.utils.ApiResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roomType")
@RequiredArgsConstructor
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponseHelper<RoomTypeResponseDTO>> create(@RequestBody RoomTypeRequestDTO requestDTO){
        RoomTypeResponseDTO responseDTO = roomTypeService.create(requestDTO);
        return ApiResponse.ok(responseDTO, "Created Room type successfully.");
    }

//    @PreAuthorize("isAuthenticated()") // we do not need this because we already config (every endpoint except auth must be authenticated
    @GetMapping
    public ResponseEntity<ApiResponseHelper<List<RoomTypeResponseDTO>>> getAll(){
        List<RoomTypeResponseDTO> responseDTOS = roomTypeService.getAll();
        return ApiResponse.ok(responseDTOS, "Get all room type success.");
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseHelper<RoomTypeResponseDTO>> update(
            @PathVariable Long id,
            @RequestBody RoomTypeRequestDTO requestDTO){

        RoomTypeResponseDTO responseDTO = roomTypeService.update(id, requestDTO);
        return ApiResponse.ok(responseDTO, "Updated room type success.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseHelper<Void>> delete(@PathVariable Long id){
        roomTypeService.delete(id);
        return ApiResponse.ok(null, "Delete Room type id"+id+"success");
    }

}
